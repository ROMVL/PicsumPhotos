package ua.romanik.vladislav.picsumphotos.presentation.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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
        .placeholder(circularProgressDrawable)
        .into(this)
}

@BindingAdapter("app:submitList")
fun RecyclerView.submitList(data: List<Any>?) {
    (this.adapter as? ListAdapter<Any, *>)?.submitList(data ?: emptyList())
}