package igor.kuridza.dice.newsreader.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import igor.kuridza.dice.newsreader.R
import igor.kuridza.dice.newsreader.databinding.ItemSingleNewsDetailsBinding
import igor.kuridza.dice.newsreader.model.SingleNews

class ViewPagerAdapter: RecyclerView.Adapter<ViewPagerAdapter.ViewPagerHolder>(){

    private val newsList: ArrayList<SingleNews> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemSingleNewsDetailsBinding>(inflater, R.layout.item_single_news_details, parent, false)
        return ViewPagerHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        holder.bindItem(newsList[position])
    }

    override fun getItemCount(): Int = newsList.size

    fun setNews(newsList: List<SingleNews>){
        this.newsList.clear()
        this.newsList.addAll(newsList)
        notifyDataSetChanged()
    }

    fun getSingleNewsTitleByPosition(position: Int): String?{
        return newsList[position].title
    }

    inner class ViewPagerHolder(private val bindingItem: ItemSingleNewsDetailsBinding): RecyclerView.ViewHolder(bindingItem.root){

        fun bindItem(singleNews: SingleNews){
            bindingItem.apply {
                this.singleNews = singleNews
                executePendingBindings()
            }
        }
    }
}