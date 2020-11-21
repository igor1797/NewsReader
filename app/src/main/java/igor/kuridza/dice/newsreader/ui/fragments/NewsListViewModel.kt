package igor.kuridza.dice.newsreader.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import igor.kuridza.dice.newsreader.model.Resource
import igor.kuridza.dice.newsreader.model.SingleNews
import igor.kuridza.dice.newsreader.repositories.NewsRepository

class NewsListViewModel(
    private val newsRepository: NewsRepository
): ViewModel() {

    val newsList: LiveData<Resource<List<SingleNews>>>
        by lazy {
            newsRepository.getNewsList()
        }

    override fun onCleared() {
        super.onCleared()
        newsRepository.clearDisposable()
    }
}