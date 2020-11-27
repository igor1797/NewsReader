package igor.kuridza.dice.newsreader.networking

import igor.kuridza.dice.newsreader.model.NewsListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/top-headlines")
    fun getNews(
        @Query("country") source: String,
        @Query("pageSize") perPage: Int,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String
    ): Single<NewsListResponse>
}