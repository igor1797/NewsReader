package igor.kuridza.dice.newsreader.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import igor.kuridza.dice.newsreader.common.NEWS_DATABASE_TABLE_NAME

@Entity(tableName = NEWS_DATABASE_TABLE_NAME)
data class SingleNews(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String?,
    val description: String?,
    val content: String?,
    @SerializedName("urlToImage")
    val imagePath: String?
)