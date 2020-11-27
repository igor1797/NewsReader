package igor.kuridza.dice.newsreader.ui.fragments.newslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import igor.kuridza.dice.newsreader.model.SingleNews
import igor.kuridza.dice.newsreader.repositories.NewsRepository

class NewsListViewModel(
    private val newsRepository: NewsRepository
): ViewModel() {

    val newsList: LiveData<PagingData<SingleNews>>
       get() =  newsRepository.getNewsPagingData()
                    .cachedIn(viewModelScope)
}