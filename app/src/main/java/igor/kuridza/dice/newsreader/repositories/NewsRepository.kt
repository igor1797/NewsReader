package igor.kuridza.dice.newsreader.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import igor.kuridza.dice.newsreader.common.API_KEY
import igor.kuridza.dice.newsreader.common.BBC_NEWS_SOURCE
import igor.kuridza.dice.newsreader.common.SORT_BY_TOP
import igor.kuridza.dice.newsreader.database.NewsDao
import igor.kuridza.dice.newsreader.model.NewsListResponse
import igor.kuridza.dice.newsreader.model.SingleNews
import igor.kuridza.dice.newsreader.networking.NewsApiService
import igor.kuridza.dice.newsreader.persistence.TimeDataStorePrefs
import igor.kuridza.dice.newsreader.utils.TimeService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository(
    private val newsService: NewsApiService,
    private val newsDao: NewsDao,
    private val timeDataStorePrefs: TimeDataStorePrefs,
    private val timeService: TimeService
) {

    private fun clearDatabaseAndSaveNews(newsList: List<SingleNews>){
        newsDao.clearAllNews()
        newsList.forEach {
            newsDao.insertSingleNews(it)
        }
    }

    fun getNewsList(): LiveData<List<SingleNews>>{
        val storedTime = timeDataStorePrefs.getTime()
        return if (isStoredDataEmpty() || isStoredDataOlderThanFiveMinutes(storedTime)) {
            storeCurrentTime()
            fetchAndCacheTopBbcNewsListFromApi()
        } else {
            getNewsListFromCache()
        }
    }

    private fun isStoredDataEmpty(): Boolean{
        return getNewsListFromCache().value?.isEmpty() ?: true
    }

    private fun isStoredDataOlderThanFiveMinutes(storedTime: Long): Boolean{
        return (timeService.isStoredDataOlderThanFiveMinutes(storedTime) && storedTime != 0L)
    }

    private fun storeCurrentTime(){
        val currentTime = timeService.getCurrentTime()
        timeDataStorePrefs.storeTime(currentTime)
    }

    private fun fetchAndCacheTopBbcNewsListFromApi(): LiveData<List<SingleNews>> {
        val newsList = MutableLiveData<List<SingleNews>>()
        newsService.getNews(API_KEY, BBC_NEWS_SOURCE, SORT_BY_TOP).enqueue(object : Callback<NewsListResponse> {
            override fun onResponse(
                call: Call<NewsListResponse>,
                response: Response<NewsListResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.news?.let {
                        newsList.value = it
                        clearDatabaseAndSaveNews(it)
                    }
                }
            }

            override fun onFailure(call: Call<NewsListResponse>, t: Throwable) {
                Log.d("NewsRepository", "onFailure: ${t.message}")
            }
        })
        return newsList
    }

    private fun getNewsListFromCache(): LiveData<List<SingleNews>>{
        val newsList = MutableLiveData<List<SingleNews>>()
        newsList.value = newsDao.getAllNews()
        return newsList
    }
}