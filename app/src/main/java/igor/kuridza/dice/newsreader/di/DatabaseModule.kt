package igor.kuridza.dice.newsreader.di



import android.content.Context
import androidx.room.Room
import igor.kuridza.dice.newsreader.common.NEWS_DATABASE_NAME
import igor.kuridza.dice.newsreader.database.NewsDao
import igor.kuridza.dice.newsreader.database.NewsDatabase
import igor.kuridza.dice.newsreader.persistence.TimeDataStorePrefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val PREFERENCES_DATA_STORE_NAME = "TimeDataStore"

val dataStoreModule = module {

    single {
        androidContext().getSharedPreferences(PREFERENCES_DATA_STORE_NAME, Context.MODE_PRIVATE)
    }

    single {
        TimeDataStorePrefs(get())
    }
}

val newsDatabaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            NewsDatabase::class.java, NEWS_DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    fun provideNewsDao(newsDatabase: NewsDatabase): NewsDao{
        return newsDatabase.newsDao()
    }

    single {
        provideNewsDao(get())
    }
}