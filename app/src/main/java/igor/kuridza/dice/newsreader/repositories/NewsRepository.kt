package igor.kuridza.dice.newsreader.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import igor.kuridza.dice.newsreader.common.API_KEY
import igor.kuridza.dice.newsreader.common.SORT_BY
import igor.kuridza.dice.newsreader.common.SOURCE
import igor.kuridza.dice.newsreader.model.NewsListResponse
import igor.kuridza.dice.newsreader.model.SingleNews
import igor.kuridza.dice.newsreader.networking.NewsApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository(private val newsService: NewsApiService) {

    fun getNewsListFromApi(): LiveData<List<SingleNews>>{
        val newsList = MutableLiveData<List<SingleNews>>()
        newsService.getNews(API_KEY, SOURCE, SORT_BY).enqueue(object : Callback<NewsListResponse> {
            override fun onResponse(
                call: Call<NewsListResponse>,
                response: Response<NewsListResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.news?.let {
                        newsList.value = it
                    }
                }
            }

            override fun onFailure(call: Call<NewsListResponse>, t: Throwable) {
                Log.d("NewsRepository", "onFailure: ${t.message}")
            }
        })
        return newsList
    }
}