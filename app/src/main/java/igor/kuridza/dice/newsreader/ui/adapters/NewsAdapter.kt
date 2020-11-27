package igor.kuridza.dice.newsreader.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import igor.kuridza.dice.newsreader.R
import igor.kuridza.dice.newsreader.databinding.ItemNewsBinding
import igor.kuridza.dice.newsreader.model.SingleNews

class NewsAdapter(
    private val itemClickListener: SingleNewsClickListener
): PagingDataAdapter<SingleNews,NewsAdapter.NewsHolder>(COMPARATOR){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemNewsBinding>(inflater, R.layout.item_news, parent, false)
        return NewsHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val singleNews = getItem(position)
        if(singleNews != null) {
            holder.bindItem(singleNews, itemClickListener)
        }
    }

    inner class NewsHolder(private val binding: ItemNewsBinding): RecyclerView.ViewHolder(binding.root){
        fun bindItem(singleNews: SingleNews, itemClickListener: SingleNewsClickListener){
            binding.apply {
                this.singleNews = singleNews
                singleNewsClickListener = itemClickListener
                executePendingBindings()
            }
        }
    }

    interface SingleNewsClickListener{
        fun onSingleNewsClicked(titleOfSingleNewsInList: String)
    }

    companion object{
        val COMPARATOR = object : DiffUtil.ItemCallback<SingleNews>() {
            override fun areItemsTheSame(oldItem: SingleNews, newItem: SingleNews): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: SingleNews, newItem: SingleNews): Boolean {
                return oldItem == newItem
            }
        }
    }
}