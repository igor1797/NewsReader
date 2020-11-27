package igor.kuridza.dice.newsreader.di

import igor.kuridza.dice.newsreader.paging.NewsRemoteMediator
import org.koin.dsl.module

val pagingModule = module {
    single {
        NewsRemoteMediator(get(),get(),get(),get(),get())
    }
}