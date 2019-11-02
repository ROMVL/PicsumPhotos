package ua.romanik.vladislav.picsumphotos.presentation.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ua.romanik.vladislav.picsumphotos.presentation.base.glide.GlideModule


@BindingAdapter("app:loadImage")
fun ImageView.loadImage(url: String) {
    val circularProgressDrawable = GlideModule.GlideProgressBar.getCircularProgressDrawable(this.context)
    Glide.with(this)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(circularProgressDrawable)
        .into(this)
}