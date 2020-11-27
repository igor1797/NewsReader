package igor.kuridza.dice.newsreader.di

import igor.kuridza.dice.newsreader.repositories.NewsRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { NewsRepository(get(),get()) }
}