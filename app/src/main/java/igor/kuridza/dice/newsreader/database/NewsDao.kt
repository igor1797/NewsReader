package igor.kuridza.dice.newsreader.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import igor.kuridza.dice.newsreader.common.NEWS_DATABASE_TABLE_NAME
import igor.kuridza.dice.newsreader.model.SingleNews
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface NewsDao {

    @Query("SELECT * FROM $NEWS_DATABASE_TABLE_NAME")
    fun getAllNewsPagingSource(): PagingSource<Int, SingleNews>

    @Query("DELETE FROM $NEWS_DATABASE_TABLE_NAME")
    fun clearAllNews()

    @Insert(onConflict = REPLACE)
    fun insertNewsList(newsList: List<SingleNews>)

    @Query("SELECT * FROM $NEWS_DATABASE_TABLE_NAME")
    fun getAllNews(): Single<List<SingleNews>>
}