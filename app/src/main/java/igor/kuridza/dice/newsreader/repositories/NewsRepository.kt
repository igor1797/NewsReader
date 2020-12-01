package igor.kuridza.dice.newsreader.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import igor.kuridza.dice.newsreader.common.DEFAULT_RADIO_BUTTON_OPTION
import igor.kuridza.dice.newsreader.common.ITEMS_PER_PAGE
import igor.kuridza.dice.newsreader.common.PREFETCH_DISTANCE
import igor.kuridza.dice.newsreader.common.addTo
import igor.kuridza.dice.newsreader.database.NewsDao
import igor.kuridza.dice.newsreader.model.SingleNews
import igor.kuridza.dice.newsreader.paging.NewsRemoteMediator
import igor.kuridza.dice.newsreader.persistence.SelectedRadioButtonOptionPrefs
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NewsRepository(
    private val newsDao: NewsDao,
    private val newsRemoteMediator: NewsRemoteMediator,
    private val selectedRadioButtonOptionPrefs: SelectedRadioButtonOptionPrefs
) {

    private val compositeDisposable = CompositeDisposable()

    fun getNewsPagingData(source: String): LiveData<PagingData<SingleNews>>{
        newsRemoteMediator.setSource(source)
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                prefetchDistance = PREFETCH_DISTANCE,
                enablePlaceholders = true,
                initialLoadSize = 30
            ),
            remoteMediator = newsRemoteMediator
        ){
            newsDao.getAllNewsPagingSource()
        }.liveData
    }

    fun getNewsList(): LiveData<List<SingleNews>>{
        val news = MutableLiveData<List<SingleNews>>()
        newsDao.getAllNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    news.value = it
                },
                {

                }
            ).addTo(compositeDisposable)
        return news
    }

    fun getSourceRadioButtonOption(): LiveData<Int>{
        val selectedRadioButtonOption = MutableLiveData<Int>()
        val oldOption = selectedRadioButtonOptionPrefs.getSelectedRadioButtonOption()
        selectedRadioButtonOption.value = if(oldOption == -1) DEFAULT_RADIO_BUTTON_OPTION else oldOption
        return selectedRadioButtonOption
    }

    fun storeSourceRadioButtonOption(newSelectedRadioButtonOption: Int){
        selectedRadioButtonOptionPrefs.storeSelectedRadioButtonOption(newSelectedRadioButtonOption)
    }

    fun clearDisposable(){
        compositeDisposable.clear()
    }
}