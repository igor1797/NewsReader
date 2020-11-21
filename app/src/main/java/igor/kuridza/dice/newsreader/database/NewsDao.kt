package igor.kuridza.dice.newsreader.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import igor.kuridza.dice.newsreader.common.NEWS_DATABASE_TABLE_NAME
import igor.kuridza.dice.newsreader.model.SingleNews
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface NewsDao {

    @Query("Select * FROM $NEWS_DATABASE_TABLE_NAME")
    fun getAllNews(): Maybe<List<SingleNews>>

    @Query("Delete FROM $NEWS_DATABASE_TABLE_NAME")
    fun clearAllNews(): Completable

    @Insert(onConflict = IGNORE)
    fun insertNewsList(newsList: List<SingleNews>): Completable
}