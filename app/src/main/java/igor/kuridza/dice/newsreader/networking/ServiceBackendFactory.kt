package igor.kuridza.dice.newsreader.networking

import igor.kuridza.dice.newsreader.common.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBackendFactory {

    private var mRetrofit: Retrofit? = null

    private val httpClient: OkHttpClient by lazy { OkHttpClient.Builder().build() }

    private val retrofit: Retrofit? = if(mRetrofit == null) createRetrofit() else mRetrofit

    private fun createRetrofit(): Retrofit{
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService(): NewsApiService = retrofit!!.create(NewsApiService::class.java)
}