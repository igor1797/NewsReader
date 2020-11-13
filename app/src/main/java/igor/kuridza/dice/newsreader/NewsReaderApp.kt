package igor.kuridza.dice.newsreader

import android.app.Application
import igor.kuridza.dice.newsreader.di.networkingModule
import igor.kuridza.dice.newsreader.di.repositoryModule
import igor.kuridza.dice.newsreader.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewsReaderApp: Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NewsReaderApp)
            modules(listOf(networkingModule, repositoryModule, viewModelModule))
        }
    }
}