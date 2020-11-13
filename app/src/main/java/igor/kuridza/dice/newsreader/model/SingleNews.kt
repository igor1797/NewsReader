package igor.kuridza.dice.newsreader.model

import com.google.gson.annotations.SerializedName

data class SingleNews(
    val title: String,
    val description: String,
    val content: String,
    @SerializedName("urlToImage")
    val imagePath: String
)