package ua.romanik.vladislav.picsumphotos.domain.datasource

import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ua.romanik.vladislav.picsumphotos.domain.model.Photo
import ua.romanik.vladislav.picsumphotos.domain.repository.IPhotosRepository

class PhotoDataSource(
    private val scope: CoroutineScope,
    private val photoRepository: IPhotosRepository
) : PageKeyedDataSource<Long, Photo>() {

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, Photo>
    ) {
        scope.launch {
            val photos = photoRepository.fetchPhotos(1, params.requestedLoadSize)
            callback.onResult(photos, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Photo>) {
        scope.launch {
            val photos = photoRepository.fetchPhotos(params.key, params.requestedLoadSize)
            callback.onResult(photos, params.key + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Photo>) { }
}