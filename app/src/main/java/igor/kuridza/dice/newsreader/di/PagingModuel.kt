package igor.kuridza.dice.newsreader.di

import igor.kuridza.dice.newsreader.paging.NewsRemotePagingSource
import org.koin.dsl.module

val pagingModule = module {
    single {
        NewsRemotePagingSource(get())
    }
}