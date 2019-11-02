package ua.romanik.vladislav.picsumphotos.presentation.ui.photoslist.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.ListAdapter
import ua.romanik.vladislav.picsumphotos.databinding.ItemPhotoBinding
import ua.romanik.vladislav.picsumphotos.domain.model.Photo
import ua.romanik.vladislav.picsumphotos.presentation.base.recycler.DataBindingViewHolder

class PhotosAdapter(
    private val listener: OnClickPhotoListener
) : PagedListAdapter<Photo, PhotosAdapter.PhotoViewHolder>(PhotosItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPhotoBinding.inflate(layoutInflater, parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    inner class PhotoViewHolder(
        private val binding: ItemPhotoBinding
    ) : DataBindingViewHolder<Photo>(binding) {
        override fun onBind(data: Photo) {
            binding.photo = data
            binding.root.setOnClickListener { listener.onClick(data) }
        }
    }

    interface OnClickPhotoListener {
        fun onClick(photo: Photo)
    }

}