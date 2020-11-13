package igor.kuridza.dice.newsreader.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import igor.kuridza.dice.newsreader.model.SingleNews
import igor.kuridza.dice.newsreader.repositories.NewsRepository

class ViewModelFactory(private val repository: NewsRepository): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsListViewModel(repository) as T
    }
}

class NewsListViewModel(newsRepository: NewsRepository): ViewModel(){

    private var mNewsList = MutableLiveData<List<SingleNews>>()
    val newsList: LiveData<List<SingleNews>>
            get() = mNewsList

    init {
        mNewsList = newsRepository.getNewsListFromApi() as MutableLiveData<List<SingleNews>>
    }
}