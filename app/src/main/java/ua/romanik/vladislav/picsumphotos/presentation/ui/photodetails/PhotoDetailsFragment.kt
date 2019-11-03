package ua.romanik.vladislav.picsumphotos.presentation.ui.photodetails

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import org.koin.android.ext.android.getKoin
import org.koin.android.viewmodel.ext.android.getViewModel
import ua.romanik.vladislav.picsumphotos.R
import ua.romanik.vladislav.picsumphotos.databinding.FragmentPhotoDetailsBinding
import ua.romanik.vladislav.picsumphotos.di.KoinProperty
import ua.romanik.vladislav.picsumphotos.presentation.base.fragment.BaseFragment
import java.io.File


class PhotoDetailsFragment : BaseFragment() {

    override val viewModel: PhotoDetailsViewModel by lazy { getViewModel<PhotoDetailsViewModel>() }

    override val layoutId: Int = R.layout.fragment_photo_details

    private val args: PhotoDetailsFragmentArgs by navArgs()

    override fun handleError() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args.photoId.let { photoId ->
            getKoin().setProperty(KoinProperty.photoId, photoId)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(binding as FragmentPhotoDetailsBinding) {
            viewModel = this@PhotoDetailsFragment.viewModel
        }
        with(viewModel) {
            userEvent.observe(
                this@PhotoDetailsFragment.viewLifecycleOwner,
                Observer {
                    when (it.content as PhotoDetailsViewModel.PhotoDetailsUserEvent) {
                        PhotoDetailsViewModel.PhotoDetailsUserEvent.BACK_PRESSED -> popBackStack()
                        PhotoDetailsViewModel.PhotoDetailsUserEvent.PHOTO_PRESSED -> changeStateTopAndBottomBar()
                        PhotoDetailsViewModel.PhotoDetailsUserEvent.SAVE_PRESSED -> showSavePhotoDialog()
                        PhotoDetailsViewModel.PhotoDetailsUserEvent.SHARE_PRESSED -> showSharePhotoDialog()
                    }
                }
            )
            sharePhotoEvent.observe(
                this@PhotoDetailsFragment.viewLifecycleOwner,
                Observer {
                    this@PhotoDetailsFragment.shareImage(it.content)
                }
            )
        }
    }

    private fun popBackStack() {
        findNavController().popBackStack()
    }

    private fun changeStateTopAndBottomBar() {
        with(binding as FragmentPhotoDetailsBinding) {
            groupTopBar.visibility =
                if (groupTopBar.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            groupBottomBar.visibility =
                if (groupBottomBar.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }
    }

    private fun showSavePhotoDialog() {
        requireContext().resources.let {
            showAlertWithThreeButton(
                it.getString(R.string.save_photo),
                it.getString(R.string.save_with_resolution),
                it.getString(R.string.yes),
                it.getString(R.string.no),
                it.getString(R.string.cancel),
                {
                    viewModel.photo.value?.let { photo ->
                        loadOriginalPhoto(photo.getOriginalPhotoUrl()) { bitmap ->
                            viewModel.saveImage(bitmap)
                        }
                    }
                },
                {
                    (binding as FragmentPhotoDetailsBinding).ivPhoto.drawable.toBitmap()
                        .let { bitmap ->
                            viewModel.saveImage(bitmap)
                        }
                }
            )
        }
    }

    private fun showSharePhotoDialog() {
        requireContext().resources.let {
            showAlertWithThreeButton(
                it.getString(R.string.share_photo),
                it.getString(R.string.share_with_resolution),
                it.getString(R.string.yes),
                it.getString(R.string.no),
                it.getString(R.string.cancel),
                {
                    viewModel.photo.value?.let { photo ->
                        loadOriginalPhoto(photo.getOriginalPhotoUrl()) { bitmap ->
                            viewModel.shareImage(bitmap)
                        }
                    }
                },
                {
                    (binding as FragmentPhotoDetailsBinding).ivPhoto.drawable.toBitmap()
                        .let { bitmap ->
                            viewModel.shareImage(bitmap)
                        }
                }
            )
        }
    }

    private fun shareImage(path: String) {
        val photoFile = File(path)
        val imageUri = FileProvider.getUriForFile(
            requireContext(),
            "ua.romanik.vladislav.picsumphotos.provider",
            photoFile
        )
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, imageUri)
            type = "image/jpeg"
        }
        startActivity(Intent.createChooser(shareIntent, resources.getText(R.string.share_photo)))
    }

    private fun loadOriginalPhoto(url: String, onResourceReady: (Bitmap) -> Unit) {
        Glide.with(requireContext()).asDrawable().load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean = false

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    onResourceReady(resource.toBitmap())
                    return true
                }
            }).submit()
    }

    private fun showAlertWithThreeButton(
        title: String,
        message: String,
        positiveButtonText: String,
        negativeButtonText: String,
        cancelText: String,
        originalPhotoListener: () -> Unit,
        phoneResolutionPhoto: () -> Unit
    ) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(positiveButtonText) { _, _ ->
                originalPhotoListener()
            }
            setNegativeButton(negativeButtonText) { _, _ ->
                phoneResolutionPhoto()
            }
            setNeutralButton(cancelText) { _, _ -> }
        }.show()
    }

}
