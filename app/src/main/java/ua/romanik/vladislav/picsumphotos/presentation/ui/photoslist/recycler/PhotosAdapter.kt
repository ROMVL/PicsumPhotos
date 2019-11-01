package ua.romanik.vladislav.picsumphotos.presentation.ui.photoslist.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ua.romanik.vladislav.picsumphotos.databinding.ItemPhotoBinding
import ua.romanik.vladislav.picsumphotos.domain.model.Photo
import ua.romanik.vladislav.picsumphotos.presentation.base.recycler.DataBindingViewHolder

class PhotosAdapter(
    private val listener: OnClickPhotoListener
) : ListAdapter<Photo, PhotosAdapter.PhotoViewHolder>(PhotosItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPhotoBinding.inflate(layoutInflater, parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
    }

    inner class PhotoViewHolder(
        private val binding: ItemPhotoBinding
    ) : DataBindingViewHolder<Photo>(binding) {
        override fun onBind(photo: Photo) {
            binding.photo = photo
            binding.root.setOnClickListener { listener.onClick(photo) }
        }
    }

    interface OnClickPhotoListener {
        fun onClick(photo: Photo)
    }

}