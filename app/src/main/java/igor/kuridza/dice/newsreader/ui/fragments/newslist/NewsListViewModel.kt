package igor.kuridza.dice.newsreader.ui.fragments.newslist

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.android.material.bottomsheet.BottomSheetBehavior
import igor.kuridza.dice.newsreader.common.BBC_NEWS_SOURCE
import igor.kuridza.dice.newsreader.model.SingleNews
import igor.kuridza.dice.newsreader.repositories.NewsRepository

class NewsListViewModel(
    private val newsRepository: NewsRepository
): ViewModel() {

    private val source = MutableLiveData(DEFAULT_SOURCE)

    private val mBottomSheetState = MutableLiveData(BottomSheetBehavior.STATE_HIDDEN)
    val bottomSheetState: LiveData<Int>
        get() = mBottomSheetState

    private var mSelectedRadioButtonOption = MutableLiveData<Int>()
    val selectedRadioButtonOption: LiveData<Int>
        get() = mSelectedRadioButtonOption

    init {
        mSelectedRadioButtonOption = newsRepository.getSourceRadioButtonOption() as MutableLiveData<Int>
    }

    val newsList: LiveData<PagingData<SingleNews>> = source.switchMap { source ->
        getNewsBySource(source)
    }

    fun setBottomSheetState(newState: Int){
        mBottomSheetState.value = newState
    }

    fun setRadioButtonOption(option: Int){
        mSelectedRadioButtonOption.value = option
        newsRepository.storeSourceRadioButtonOption(option)
    }

    fun setSource(newSource: String){
        source.value = newSource
    }

    private fun getNewsBySource(source: String): LiveData<PagingData<SingleNews>>{
        return newsRepository.getNewsPagingData(source)
            .cachedIn(viewModelScope)
    }

    companion object{
        private const val DEFAULT_SOURCE = BBC_NEWS_SOURCE
    }
}