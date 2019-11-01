package ua.romanik.vladislav.picsumphotos.data.cloud.model

import com.google.gson.annotations.SerializedName

data class PicsumPhoto(
    val id: Long,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    @SerializedName("download_url") val downloadUrl: String
)