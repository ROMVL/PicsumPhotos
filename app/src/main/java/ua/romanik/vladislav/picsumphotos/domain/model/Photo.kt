package ua.romanik.vladislav.picsumphotos.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(
    val id: Long,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    val downloadUrl: String
) : Parcelable