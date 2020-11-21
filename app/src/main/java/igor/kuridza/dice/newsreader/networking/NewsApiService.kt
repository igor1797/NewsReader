package igor.kuridza.dice.newsreader.networking

import igor.kuridza.dice.newsreader.model.NewsListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v1/articles")
    fun getNews(
        @Query("apiKey") apiKey: String,
        @Query("source") source: String,
        @Query("sortBy") sortBy: String
    ): Single<NewsListResponse>
}