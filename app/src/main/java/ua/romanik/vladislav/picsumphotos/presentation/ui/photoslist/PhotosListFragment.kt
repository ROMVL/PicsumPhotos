package ua.romanik.vladislav.picsumphotos.presentation.ui.photoslist

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.viewmodel.ext.android.viewModel
import ua.romanik.vladislav.picsumphotos.R
import ua.romanik.vladislav.picsumphotos.databinding.FragmentPhotosListBinding
import ua.romanik.vladislav.picsumphotos.domain.model.Photo
import ua.romanik.vladislav.picsumphotos.presentation.base.fragment.BaseFragment
import ua.romanik.vladislav.picsumphotos.presentation.ui.photoslist.recycler.PhotosAdapter

class PhotosListFragment : BaseFragment(), PhotosAdapter.OnClickPhotoListener {

    override val layoutId: Int = R.layout.fragment_photos_list

    override val viewModel: PhotosListViewModel by viewModel()

    override fun handleError() {
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(binding as FragmentPhotosListBinding) {
            viewModel = this@PhotosListFragment.viewModel
            rvPhotos.adapter = PhotosAdapter(this@PhotosListFragment)
            rvPhotos.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onClick(photo: Photo) {

    }

}
