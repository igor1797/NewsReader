package igor.kuridza.dice.newsreader.di

import android.content.Context
import androidx.room.Room
import igor.kuridza.dice.newsreader.common.NEWS_DATABASE_NAME
import igor.kuridza.dice.newsreader.database.NewsDao
import igor.kuridza.dice.newsreader.database.NewsDatabase
import igor.kuridza.dice.newsreader.database.NewsRemoteKeysDao
import igor.kuridza.dice.newsreader.persistence.SelectedRadioButtonOptionPrefs
import igor.kuridza.dice.newsreader.persistence.TimePrefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val PREFERENCES_DATA_STORE_NAME = "PreferencesDataStore"

val prefsModule = module {

    single {
        androidContext().getSharedPreferences(PREFERENCES_DATA_STORE_NAME, Context.MODE_PRIVATE)
    }

    single {
        TimePrefs(get())
    }

    single {
        SelectedRadioButtonOptionPrefs(get())
    }
}

val newsDatabaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            NewsDatabase::class.java, NEWS_DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideNewsDao(newsDatabase: NewsDatabase): NewsDao{
        return newsDatabase.newsDao()
    }

    fun provideNewsRemoteKeysDao(newsDatabase: NewsDatabase): NewsRemoteKeysDao{
        return newsDatabase.newsRemoteKeysDao()
    }

    single {
        provideNewsDao(get())
    }

    single {
        provideNewsRemoteKeysDao(get())
    }
}