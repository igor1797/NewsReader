package igor.kuridza.dice.newsreader.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import igor.kuridza.dice.newsreader.common.ITEMS_PER_PAGE
import igor.kuridza.dice.newsreader.common.PREFETCH_DISTANCE
import igor.kuridza.dice.newsreader.common.addTo
import igor.kuridza.dice.newsreader.database.NewsDao
import igor.kuridza.dice.newsreader.model.SingleNews
import igor.kuridza.dice.newsreader.paging.NewsRemoteMediator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NewsRepository(
    private val newsDao: NewsDao,
    private val newsRemoteMediator: NewsRemoteMediator
) {

    private val compositeDisposable = CompositeDisposable()

    fun getNewsPagingData(): LiveData<PagingData<SingleNews>>{
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

    fun clearDisposable(){
        compositeDisposable.clear()
    }
}