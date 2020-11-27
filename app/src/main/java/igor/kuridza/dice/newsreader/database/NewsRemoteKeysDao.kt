package igor.kuridza.dice.newsreader.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import igor.kuridza.dice.newsreader.common.NEWS_REMOTE_KEYS_DATABASE_TABLE_NAME
import igor.kuridza.dice.newsreader.model.NewsRemoteKeys

@Dao
interface NewsRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<NewsRemoteKeys>)

    @Query("SELECT * FROM $NEWS_REMOTE_KEYS_DATABASE_TABLE_NAME WHERE newsTitle = :newsTitle")
    fun newsRemoteKeysByNewsTitle(newsTitle: String): NewsRemoteKeys?

    @Query("DELETE FROM $NEWS_REMOTE_KEYS_DATABASE_TABLE_NAME")
    fun clearNewsRemoteKeys()
}