package igor.kuridza.dice.newsreader.ui.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import igor.kuridza.dice.newsreader.common.loadImage
import igor.kuridza.dice.newsreader.model.SingleNews

object DataBindingAdapter {

    @BindingAdapter("app:imageUrl")
    @JvmStatic fun setImageUrl(imageView: ImageView, imageUrl: String?){
        imageView.loadImage(imageUrl)
    }

    @BindingAdapter("app:setAdapter")
    @JvmStatic fun setRecyclerViewAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>){
            recyclerView.adapter = adapter
    }

    @BindingAdapter("app:setCurrentItem")
    @JvmStatic fun setViewPagerCurrentItem(viewPager2: ViewPager2, position: Int){
        viewPager2.apply {
            post {
                this.setCurrentItem(position, true)
            }
        }
    }
}