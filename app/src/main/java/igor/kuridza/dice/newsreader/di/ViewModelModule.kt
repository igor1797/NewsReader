package igor.kuridza.dice.newsreader.di

import igor.kuridza.dice.newsreader.ui.fragments.newslist.NewsListViewModel
import igor.kuridza.dice.newsreader.ui.fragments.singlenews.SingleNewsDetailsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{ NewsListViewModel(get()) }
    viewModel { SingleNewsDetailsViewModel(get()) }
}