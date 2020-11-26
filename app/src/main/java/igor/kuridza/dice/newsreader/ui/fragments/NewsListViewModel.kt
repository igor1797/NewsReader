package igor.kuridza.dice.newsreader.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import igor.kuridza.dice.newsreader.model.Resource
import igor.kuridza.dice.newsreader.model.SingleNews
import igor.kuridza.dice.newsreader.repositories.NewsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class NewsListViewModel(
    newsRepository: NewsRepository
): ViewModel() {

    private val mNewsList = MutableLiveData<Resource<PagingData<SingleNews>>>()
    val newsList: LiveData<Resource<PagingData<SingleNews>>>
        get() = mNewsList

    private val disposable: Disposable = newsRepository.getNews()
        .cachedIn(viewModelScope)
        .doOnSubscribe{ mNewsList.postValue(Resource.Loading()) }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            {
                mNewsList.postValue(Resource.Success(it))
            },
            {
                mNewsList.postValue(Resource.Error(it.message))
            }
        )

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}