package igor.kuridza.dice.newsreader.model

import androidx.room.*
import com.google.gson.annotations.SerializedName
import igor.kuridza.dice.newsreader.common.NEWS_DATABASE_TABLE_NAME

@Entity(tableName = NEWS_DATABASE_TABLE_NAME)
data class SingleNews(
    @PrimaryKey
    val title: String,
    val description: String?,
    @SerializedName("urlToImage")
    val imagePath: String?,
)