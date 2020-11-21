package igor.kuridza.dice.newsreader.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import igor.kuridza.dice.newsreader.common.API_KEY
import igor.kuridza.dice.newsreader.common.BBC_NEWS_SOURCE
import igor.kuridza.dice.newsreader.common.SORT_BY_TOP
import igor.kuridza.dice.newsreader.common.addTo
import igor.kuridza.dice.newsreader.database.NewsDao
import igor.kuridza.dice.newsreader.model.Resource
import igor.kuridza.dice.newsreader.model.SingleNews
import igor.kuridza.dice.newsreader.networking.NewsApiService
import igor.kuridza.dice.newsreader.persistence.TimeDataStorePrefs
import igor.kuridza.dice.newsreader.utils.TimeService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NewsRepository(
    private val newsService: NewsApiService,
    private val newsDao: NewsDao,
    private val timeDataStorePrefs: TimeDataStorePrefs,
    private val timeService: TimeService
) {
    private val disposable = CompositeDisposable()

    private fun clearDatabaseAndSaveNews(newsList: List<SingleNews>){
        newsDao.clearAllNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d(NewsRepository::class.java.name, "clearDatabaseAndSaveNews: successfully delete news")
                },
                {
                    Log.e(NewsRepository::class.java.name, "clearDatabaseAndSaveNews: unsuccessful deletion of news. ${it.message}")
                }
            ).addTo(disposable)

        newsDao.insertNewsList(newsList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d(NewsRepository::class.java.name, "insertNewsList: successfully saving news")
                },
                {
                    Log.e(NewsRepository::class.java.name, "insertNewsList: unsuccessful news saving. ${it.message}")
                }
            ).addTo(disposable)
    }

    fun getNewsList(): LiveData<Resource<List<SingleNews>>>{
        return if(isStoredDataOlderThanFiveMinutes() || isDatabaseEmpty()){
            fetchAndCacheTopBbcNewsListFromApi()
        }else {
            getNewsListFromCache()
        }
    }

    private fun isStoredDataOlderThanFiveMinutes(): Boolean{
        val storedTime = timeDataStorePrefs.getTime()
        return timeService.isStoredDataOlderThanFiveMinutes(storedTime)
    }

    private fun isDatabaseEmpty(): Boolean{
        val storedTime = timeDataStorePrefs.getTime()
        //If stored time == 0L than local database is empty, because 0L is default stored time
        return storedTime == 0L
    }

    private fun storeCurrentTime(){
        val currentTime = timeService.getCurrentTime()
        timeDataStorePrefs.storeTime(currentTime)
    }

    private fun fetchAndCacheTopBbcNewsListFromApi(): LiveData<Resource<List<SingleNews>>> {
        val newsList = MutableLiveData<Resource<List<SingleNews>>>()
        newsList.postValue(Resource.Loading())
        newsService.getNews(API_KEY, BBC_NEWS_SOURCE, SORT_BY_TOP)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    storeCurrentTime()
                    newsList.postValue(Resource.Success(response.news))
                    clearDatabaseAndSaveNews(response.news)
                },
                { throwable ->
                    newsList.postValue(Resource.Error(throwable.message))
                }
            ).addTo(disposable)

        return newsList
    }

    private fun getNewsListFromCache(): LiveData<Resource<List<SingleNews>>> {
        val newsList = MutableLiveData<Resource<List<SingleNews>>>()
        newsList.postValue(Resource.Loading())
        newsDao.getAllNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    newsList.postValue(Resource.Success(it))
                },
                {
                    newsList.postValue(Resource.Error(it.message))
                }
            ).addTo(disposable)

        return newsList
    }

    fun clearDisposable(){
        disposable.clear()
    }
}