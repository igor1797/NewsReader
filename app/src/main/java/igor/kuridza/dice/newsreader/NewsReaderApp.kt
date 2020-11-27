package igor.kuridza.dice.newsreader

import android.app.Application
import igor.kuridza.dice.newsreader.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewsReaderApp: Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NewsReaderApp)
            modules(listOf(
                networkingModule,
                repositoryModule,
                viewModelModule,
                prefsModule,
                newsDatabaseModule,
                timeUtilModule,
                pagingModule
            ))
        }
    }
}