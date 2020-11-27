package igor.kuridza.dice.newsreader.common

import android.view.View
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import igor.kuridza.dice.newsreader.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun ImageView.loadImage(imageUrl: String?){
    val circularProgressDrawable = CircularProgressDrawable(this.context)
    circularProgressDrawable.apply{
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }

    Glide.with(this)
        .load(imageUrl)
        .error(R.drawable.ic_image_not_supported)
        .placeholder(circularProgressDrawable)
        .into(this)
}

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.gone(){
    visibility = View.GONE
}

fun Disposable.addTo(compositeDisposable: CompositeDisposable): Disposable
    = apply { compositeDisposable.add(this)
    }