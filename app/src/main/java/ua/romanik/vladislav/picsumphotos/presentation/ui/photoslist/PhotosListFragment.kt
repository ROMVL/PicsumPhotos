package ua.romanik.vladislav.picsumphotos.presentation.ui.photoslist

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
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

    private val adapter by lazy { PhotosAdapter(this@PhotosListFragment) }

    override fun handleError() {
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(binding as FragmentPhotosListBinding) {
            viewModel = this@PhotosListFragment.viewModel
            rvPhotos.adapter = adapter
            rvPhotos.layoutManager = LinearLayoutManager(requireContext())
        }
        with(viewModel) {
            getPagedPhotos().observe(
                this@PhotosListFragment.viewLifecycleOwner,
                Observer {
                    adapter.submitList(it)
                }
            )
        }
    }

    override fun onClick(photo: Photo) {
        PhotosListFragmentDirections.actionPhotosListFragmentToPhotoDetailsFragment(photo.id).let {
            findNavController().navigate(it)
        }
    }

}
