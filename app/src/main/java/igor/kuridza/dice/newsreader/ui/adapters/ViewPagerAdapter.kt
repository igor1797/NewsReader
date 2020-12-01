package igor.kuridza.dice.newsreader.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import igor.kuridza.dice.newsreader.R
import igor.kuridza.dice.newsreader.databinding.ItemSingleNewsDetailsBinding
import igor.kuridza.dice.newsreader.model.SingleNews

class ViewPagerAdapter: RecyclerView.Adapter<ViewPagerAdapter.ViewPagerHolder>(){

    private val newsList = arrayListOf<SingleNews>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemSingleNewsDetailsBinding>(inflater, R.layout.item_single_news_details, parent, false)
        return ViewPagerHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        val singleNews = newsList[position]
        holder.bindItem(singleNews)
    }

    override fun getItemCount(): Int = newsList.size

    fun setNewsList(newsList: List<SingleNews>){
        this.newsList.clear()
        this.newsList.addAll(newsList)
        notifyDataSetChanged()
    }

    fun getSingleNewsByPosition(position: Int): SingleNews{
        return newsList[position]
    }

    fun getSingleNewsPositionByTitle(title: String): Int{
        val singleNews = newsList.find { it.title == title }
        return newsList.indexOf(singleNews)
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