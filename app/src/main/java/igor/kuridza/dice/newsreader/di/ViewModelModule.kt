package igor.kuridza.dice.newsreader.di

import igor.kuridza.dice.newsreader.ui.fragments.NewsListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{ NewsListViewModel(get()) }
}