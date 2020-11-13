package igor.kuridza.dice.newsreader.model

import com.google.gson.annotations.SerializedName

data class NewsListResponse(
    @SerializedName("articles")
    val news: List<SingleNews>
)