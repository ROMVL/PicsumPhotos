package ua.romanik.vladislav.picsumphotos.presentation.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide


@BindingAdapter("app:loadImage")
fun ImageView.loadImage(url: String) {

    val circularProgressDrawable = CircularProgressDrawable(this.context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    Glide.with(this)
        .load(url)
        .override(600, 200)
        .placeholder(circularProgressDrawable)
        .into(this)
}