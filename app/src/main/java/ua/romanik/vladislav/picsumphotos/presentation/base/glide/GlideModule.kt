package ua.romanik.vladislav.picsumphotos.presentation.base.glide

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class GlideModule : AppGlideModule() {

    object GlideProgressBar {

        fun getCircularProgressDrawable(context: Context) = CircularProgressDrawable(context).apply {
            strokeWidth = 5f
            centerRadius = 30f
            start()
        }

    }

}