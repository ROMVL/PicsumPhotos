package ua.romanik.vladislav.picsumphotos.presentation.ui.photoslist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ua.romanik.vladislav.picsumphotos.domain.model.Photo
import ua.romanik.vladislav.picsumphotos.domain.usecase.FetchPhotosUseCase
import ua.romanik.vladislav.picsumphotos.presentation.base.livedata.Event
import ua.romanik.vladislav.picsumphotos.presentation.base.viewmodel.BaseViewModel

class PhotosListViewModel(
    private val fetchPhotosUseCase: FetchPhotosUseCase
) : BaseViewModel() {

    private val Tag = PhotosListViewModel::class.java.name

    val photos by lazy { MutableLiveData<List<Photo>>() }

    init {
        loadPhotos()
    }

    private fun loadPhotos() {
        viewModelScope.launch {
            loading.value = Event(true)
            fetchPhotosUseCase.execute {
                onComplete { photosList ->
                    Log.d(Tag, photosList.toString())
                    photos.value = photosList
                    loading.value = Event(false)
                }
                onError { errorModel ->
                    error.value = errorModel
                    loading.value = Event(false)
                }
            }
        }
    }

}
