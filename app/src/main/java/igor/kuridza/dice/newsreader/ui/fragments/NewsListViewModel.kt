package igor.kuridza.dice.newsreader.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import igor.kuridza.dice.newsreader.model.SingleNews
import igor.kuridza.dice.newsreader.repositories.NewsRepository

class NewsListViewModel(
    private val newsRepository: NewsRepository
): ViewModel() {

    private var mNewsList = MutableLiveData<List<SingleNews>>()
    val newsList: LiveData<List<SingleNews>>
        get() = mNewsList

    init {
        mNewsList = newsRepository.getNewsList() as MutableLiveData<List<SingleNews>>
    }
}