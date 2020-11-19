package igor.kuridza.dice.newsreader.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import igor.kuridza.dice.newsreader.R
import igor.kuridza.dice.newsreader.databinding.ItemNewsBinding
import igor.kuridza.dice.newsreader.model.SingleNews

class NewsAdapter(
    private val itemClickListener: SingleNewsClickListener
): RecyclerView.Adapter<NewsAdapter.NewsHolder>(){

    private var news = mutableListOf<SingleNews>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemNewsBinding>(inflater, R.layout.item_news, parent, false)
        return NewsHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.bindItem(news[position], itemClickListener, position)
    }

    override fun getItemCount(): Int = news.size

    fun setNews(singleNews: List<SingleNews>){
        this.news.clear()
        this.news.addAll(singleNews)
        notifyDataSetChanged()
    }

    inner class NewsHolder(private val binding: ItemNewsBinding): RecyclerView.ViewHolder(binding.root){
        fun bindItem(singleNews: SingleNews, itemClickListener: SingleNewsClickListener, position: Int){
            binding.apply {
                this.singleNews = singleNews
                singleNewsClickListener = itemClickListener
                positionOfSingleNewsInList = position
                executePendingBindings()
            }
        }
    }

    interface SingleNewsClickListener{
        fun onSingleNewsClicked(positionOfSingleNewsInList: Int)
    }
}