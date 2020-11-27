package igor.kuridza.dice.newsreader.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import igor.kuridza.dice.newsreader.common.API_KEY
import igor.kuridza.dice.newsreader.common.BBC_NEWS_SOURCE
import igor.kuridza.dice.newsreader.common.ITEMS_PER_PAGE
import igor.kuridza.dice.newsreader.database.NewsDao
import igor.kuridza.dice.newsreader.database.NewsRemoteKeysDao
import igor.kuridza.dice.newsreader.model.NewsListResponse
import igor.kuridza.dice.newsreader.model.NewsRemoteKeys
import igor.kuridza.dice.newsreader.model.SingleNews
import igor.kuridza.dice.newsreader.networking.NewsApiService
import igor.kuridza.dice.newsreader.persistence.TimePrefs
import igor.kuridza.dice.newsreader.utils.TimeService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val newsDao: NewsDao,
    private val newsRemoteKeysDao: NewsRemoteKeysDao,
    private val newsApiService: NewsApiService,
    private val timeService: TimeService,
    private val timePrefs: TimePrefs
): RxRemoteMediator<Int, SingleNews>() {

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, SingleNews>
    ): Single<MediatorResult> {
        return Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .map {
                when (loadType) {
                    LoadType.REFRESH -> {
                        val storedTime = timePrefs.getTime()
                        if (timeService.isStoredDataOlderThanFiveMinutes(storedTime) || storedTime == 0L) {
                            val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                            remoteKeys?.nextKey?.minus(1) ?: 1
                        } else {
                            INVALID_PAGE
                        }
                    }
                    LoadType.PREPEND -> {
                        val remoteKeys = getRemoteKeyForFirstItem(state)
                            ?: throw InvalidObjectException("Result is empty")
                        remoteKeys.previousKey ?: INVALID_PAGE
                    }
                    LoadType.APPEND -> {
                        val remoteKeys = getRemoteKeyForLastItem(state)
                            ?: throw InvalidObjectException("Result is empty")
                        remoteKeys.nextKey ?: INVALID_PAGE
                    }
                }
            }
            .flatMap { page ->
                if (page == INVALID_PAGE) {
                    Single.just(MediatorResult.Success(true))
                } else {
                    newsApiService.getNews(
                        source = BBC_NEWS_SOURCE,
                        perPage = ITEMS_PER_PAGE,
                        page = page,
                        apiKey = API_KEY
                    ).subscribeOn(Schedulers.io())
                        .map {
                            insertToDb(page, loadType, it)
                        }
                        .map<MediatorResult>{
                            MediatorResult.Success(endOfPaginationReached = (it.totalResults <= (page * ITEMS_PER_PAGE)))
                        }
                        .onErrorReturn {
                            MediatorResult.Error(it)
                        }
                }
            }
            .onErrorReturn { MediatorResult.Error(it) }
    }

    private fun insertToDb(
        page: Int,
        loadType: LoadType,
        data: NewsListResponse
    ): NewsListResponse {
        if (loadType == LoadType.REFRESH) {
            timePrefs.storeTime(timeService.getCurrentTime())
            newsRemoteKeysDao.clearNewsRemoteKeys()
            newsDao.clearAllNews()
        }

        val prevKey = if (page == 1) null else page - 1
        val nextKey = if (data.totalResults <= (page * ITEMS_PER_PAGE)) null else page + 1

        val keys = data.newsList.map {
            NewsRemoteKeys(newsTitle = it.title, previousKey = prevKey, nextKey = nextKey)
        }

        newsRemoteKeysDao.insertAll(keys)
        newsDao.insertNewsList(data.newsList)
        return data
    }


    private fun getRemoteKeyForLastItem(state: PagingState<Int, SingleNews>): NewsRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { singleNews ->
                newsRemoteKeysDao.newsRemoteKeysByNewsTitle(singleNews.title)
            }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, SingleNews>): NewsRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { singleNews ->
                newsRemoteKeysDao.newsRemoteKeysByNewsTitle(singleNews.title)
            }
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, SingleNews>): NewsRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.title?.let { title ->
                newsRemoteKeysDao.newsRemoteKeysByNewsTitle(title)
            }
        }
    }

    companion object {
        const val INVALID_PAGE = -1
    }
}