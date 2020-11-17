package igor.kuridza.dice.newsreader.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import igor.kuridza.dice.newsreader.common.NEWS_DATABASE_TABLE_NAME
import igor.kuridza.dice.newsreader.model.SingleNews

@Dao
interface NewsDao {

    @Query("Select * FROM $NEWS_DATABASE_TABLE_NAME")
    fun getAllNews(): List<SingleNews>

    @Query("Delete FROM $NEWS_DATABASE_TABLE_NAME")
    fun clearAllNews()

    @Insert(onConflict = IGNORE)
    fun insertSingleNews(singleNews: SingleNews)
}