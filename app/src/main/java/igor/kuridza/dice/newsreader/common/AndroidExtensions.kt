package igor.kuridza.dice.newsreader.common

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun ImageView.loadImage(imageUrl: String){
    Glide.with(this).load(imageUrl).into(this)
}

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.gone(){
    visibility = View.GONE
}

fun Disposable.addTo(compositeDisposable: CompositeDisposable): Disposable
    = apply { compositeDisposable.add(this) }