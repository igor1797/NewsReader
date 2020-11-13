package igor.kuridza.dice.newsreader.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import igor.kuridza.dice.newsreader.common.loadImage
import igor.kuridza.dice.newsreader.model.SingleNews
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_news.*

class NewsHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer{

    fun bindItem(singleNews: SingleNews){
        newsTitle.text = singleNews.title
        newsDescription.text = singleNews.description
        newsImage.loadImage(singleNews.imagePath)
    }
}