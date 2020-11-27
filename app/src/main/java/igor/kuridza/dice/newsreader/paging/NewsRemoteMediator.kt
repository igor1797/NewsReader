package igor.kuridza.dice.newsreader.paging

import androidx.paging.rxjava2.RxPagingSource
import igor.kuridza.dice.newsreader.common.API_KEY
import igor.kuridza.dice.newsreader.common.BBC_NEWS_SOURCE
import igor.kuridza.dice.newsreader.common.ITEMS_PER_PAGE
import igor.kuridza.dice.newsreader.common.RESPONSE_OK
import igor.kuridza.dice.newsreader.model.NewsListResponse
import igor.kuridza.dice.newsreader.model.SingleNews
import igor.kuridza.dice.newsreader.networking.NewsApiService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class NewsRemotePagingSource(
    private val newsApiService: NewsApiService
): RxPagingSource<Int, SingleNews>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, SingleNews>> {
        val position = params.key ?: 1

        return newsApiService.getNews(
            source = BBC_NEWS_SOURCE,
            perPage = ITEMS_PER_PAGE,
            page = position,
            apiKey = API_KEY
        ).subscribeOn(Schedulers.io())
            .map {
                if(it.status == RESPONSE_OK)
                    toLoadResult(it, position)
                else
                    LoadResult.Error(Throwable())
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }

    private fun toLoadResult(news: NewsListResponse, position: Int): LoadResult<Int, SingleNews>{
        return LoadResult.Page(
            data = news.news,
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (news.totalResults <= (position*ITEMS_PER_PAGE)) null else position + 1
        )
    }
}