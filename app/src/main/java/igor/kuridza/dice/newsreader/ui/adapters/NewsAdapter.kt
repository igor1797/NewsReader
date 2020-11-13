package igor.kuridza.dice.newsreader.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import igor.kuridza.dice.newsreader.R
import igor.kuridza.dice.newsreader.model.SingleNews

class NewsAdapter() : RecyclerView.Adapter<NewsHolder>(){

    private var news = mutableListOf<SingleNews>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsHolder(view)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.bindItem(news[position])
    }

    override fun getItemCount(): Int = news.size

    fun setNews(singleNews: List<SingleNews>){
        this.news.clear()
        this.news.addAll(singleNews)
        notifyDataSetChanged()
    }
}