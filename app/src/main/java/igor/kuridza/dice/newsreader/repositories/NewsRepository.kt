package igor.kuridza.dice.newsreader.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import igor.kuridza.dice.newsreader.common.ITEMS_PER_PAGE
import igor.kuridza.dice.newsreader.common.PREFETCH_DISTANCE
import igor.kuridza.dice.newsreader.model.SingleNews
import igor.kuridza.dice.newsreader.paging.NewsRemotePagingSource
import io.reactivex.Flowable

class NewsRepository(
    private val newsRemotePagingSource: NewsRemotePagingSource
) {

    fun getNews(): Flowable<PagingData<SingleNews>>{
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                prefetchDistance = PREFETCH_DISTANCE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { newsRemotePagingSource }
        ).flowable
    }
}