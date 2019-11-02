package ua.romanik.vladislav.picsumphotos.domain.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import kotlinx.coroutines.CoroutineScope
import ua.romanik.vladislav.picsumphotos.domain.model.Photo
import ua.romanik.vladislav.picsumphotos.domain.repository.IPhotosRepository

class PhotoDataSourceFactory(
    private val scope: CoroutineScope,
    private val photoRepository: IPhotosRepository
) : DataSource.Factory<Long, Photo>() {

    private val photoMutableLiveDataSource by lazy { MutableLiveData<PhotoDataSource>() }

    override fun create(): DataSource<Long, Photo> {
        val photoDataSource = PhotoDataSource(scope, photoRepository)
        photoMutableLiveDataSource.postValue(photoDataSource)
        return photoDataSource
    }

    fun getPhotoDataSource() = photoMutableLiveDataSource
}