package ua.romanik.vladislav.picsumphotos.presentation.ui.photoslist.recycler

import androidx.recyclerview.widget.DiffUtil
import ua.romanik.vladislav.picsumphotos.domain.model.Photo

class PhotosItemCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.author == newItem.author
                && oldItem.url == newItem.url
                && oldItem.downloadUrl == newItem.downloadUrl
                && oldItem.height == newItem.height
                && oldItem.width == newItem.width
    }
}