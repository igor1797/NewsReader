package igor.kuridza.dice.newsreader.model

import com.google.gson.annotations.SerializedName

data class NewsListResponse(
    val status: String,
    val totalResults: Int,
    @SerializedName("articles")
    val newsList: List<SingleNews>
)