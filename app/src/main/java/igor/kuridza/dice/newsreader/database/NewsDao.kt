package igor.kuridza.dice.newsreader.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import igor.kuridza.dice.newsreader.common.NEWS_DATABASE_TABLE_NAME
import igor.kuridza.dice.newsreader.model.SingleNews
import io.reactivex.Completable

@Dao
interface NewsDao {

    @Query("SELECT * FROM $NEWS_DATABASE_TABLE_NAME ORDER BY title ASC")
    fun getAllNews(): PagingSource<Int, SingleNews>

    @Query("DELETE FROM $NEWS_DATABASE_TABLE_NAME")
    fun clearAllNews(): Completable

    @Insert(onConflict = IGNORE)
    fun insertNewsList(newsList: List<SingleNews>): Completable
}