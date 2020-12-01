package igor.kuridza.dice.newsreader.ui.fragments.singlenews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import igor.kuridza.dice.newsreader.model.SingleNews
import igor.kuridza.dice.newsreader.repositories.NewsRepository

class SingleNewsDetailsViewModel(
    private val newsRepository: NewsRepository
): ViewModel() {

    val newsList: LiveData<List<SingleNews>>
        get() =  newsRepository.getNewsList()

    private var mSelectedSingleNews = MutableLiveData<SingleNews>()
    val selectedSingleNews: LiveData<SingleNews>
        get() = mSelectedSingleNews

    private var mPositionOfSelectedSingleNews = MutableLiveData<Int>()
    val positionOfSelectedSingleNews: LiveData<Int>
        get() = mPositionOfSelectedSingleNews

    fun setSelectedSingleNews(singleNews: SingleNews?){
        singleNews?.let {
            mSelectedSingleNews.value = singleNews
        }
    }

    fun setPositionOfSelectedSingleNews(position: Int){
        mPositionOfSelectedSingleNews.value = position
    }

    override fun onCleared() {
        newsRepository.clearDisposable()
        super.onCleared()
    }
}