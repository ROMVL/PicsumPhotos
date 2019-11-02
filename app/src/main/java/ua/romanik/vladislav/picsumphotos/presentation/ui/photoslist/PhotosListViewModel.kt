package ua.romanik.vladislav.picsumphotos.presentation.ui.photoslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import ua.romanik.vladislav.picsumphotos.domain.datasource.PhotoDataSourceFactory
import ua.romanik.vladislav.picsumphotos.domain.model.Photo
import ua.romanik.vladislav.picsumphotos.domain.repository.IPhotosRepository
import ua.romanik.vladislav.picsumphotos.presentation.base.viewmodel.BaseViewModel

class PhotosListViewModel(
    private val photosRepository: IPhotosRepository
) : BaseViewModel() {

    private val Tag = PhotosListViewModel::class.java.name

    private lateinit var photos: LiveData<PagedList<Photo>>

    init {
        initPagedConfig()
    }

    private fun initPagedConfig() {
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setInitialLoadSizeHint(5)
            .setEnablePlaceholders(false)
            .build()
        photos = LivePagedListBuilder(
            PhotoDataSourceFactory(
                viewModelScope,
                photosRepository
            ),
            config
        ).build()
    }

    fun getPagedPhotos(): LiveData<PagedList<Photo>> = photos

}
