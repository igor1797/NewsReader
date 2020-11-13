package igor.kuridza.dice.newsreader.di

import igor.kuridza.dice.newsreader.networking.NewsApiService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://newsapi.org/"

val networkingModule = module {

    single {
        GsonConverterFactory.create() as Converter.Factory
    }

    single {
        OkHttpClient.Builder().build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BASE_URL)
            .addConverterFactory(get())
            .build()
    }

    single {
        get<Retrofit>().create(NewsApiService::class.java)
    }
}