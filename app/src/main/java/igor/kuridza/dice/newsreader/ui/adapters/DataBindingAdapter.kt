package igor.kuridza.dice.newsreader.ui.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import igor.kuridza.dice.newsreader.common.loadImage

object DataBindingAdapter {

    @BindingAdapter("app:imageUrl")
    @JvmStatic fun setImageUrl(imageView: ImageView, imageUrl: String?){
        imageView.loadImage(imageUrl)
    }

    @BindingAdapter("app:setAdapter")
    @JvmStatic fun setRecyclerViewAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>){
            recyclerView.adapter = adapter
    }
}