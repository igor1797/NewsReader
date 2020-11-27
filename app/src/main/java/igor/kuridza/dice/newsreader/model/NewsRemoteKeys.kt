package igor.kuridza.dice.newsreader.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import igor.kuridza.dice.newsreader.common.NEWS_REMOTE_KEYS_DATABASE_TABLE_NAME

@Entity(tableName = NEWS_REMOTE_KEYS_DATABASE_TABLE_NAME)
data class NewsRemoteKeys(
    @PrimaryKey
    val newsTitle: String,
    val previousKey: Int?,
    val nextKey: Int?
)
