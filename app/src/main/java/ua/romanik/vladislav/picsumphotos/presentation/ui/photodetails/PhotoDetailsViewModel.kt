package ua.romanik.vladislav.picsumphotos.presentation.ui.photodetails

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ua.romanik.vladislav.picsumphotos.PicsumPhotosApp
import ua.romanik.vladislav.picsumphotos.R
import ua.romanik.vladislav.picsumphotos.domain.model.Photo
import ua.romanik.vladislav.picsumphotos.domain.model.error.ErrorModel
import ua.romanik.vladislav.picsumphotos.domain.model.error.ErrorStatus
import ua.romanik.vladislav.picsumphotos.domain.usecase.FetchPhotoDetailsUseCase
import ua.romanik.vladislav.picsumphotos.presentation.base.livedata.Event
import ua.romanik.vladislav.picsumphotos.presentation.base.viewmodel.BaseAndroidViewModel
import java.io.File
import java.io.FileOutputStream


class PhotoDetailsViewModel(
    private val fetchPhotoDetailsUseCase: FetchPhotoDetailsUseCase,
    private val photoId: Long,
    application: Application
) : BaseAndroidViewModel(application) {

    enum class PhotoDetailsUserEvent {
        BACK_PRESSED, SAVE_PRESSED, PHOTO_PRESSED, SHARE_PRESSED
    }

    val photo by lazy { MutableLiveData<Photo>() }
    val sharePhotoEvent by lazy { MutableLiveData<Event<String>>() }

    init {
        loadPhoto()
    }

    private fun loadPhoto() {
        viewModelScope.launch {
            fetchPhotoDetailsUseCase.photoId = photoId
            fetchPhotoDetailsUseCase.execute {
                onComplete {
                    photo.value = it
                }
                onError {
                    error.value = it
                }
            }
        }
    }


    fun backButtonPressed() {
        userEvent.value = Event(PhotoDetailsUserEvent.BACK_PRESSED)
    }

    fun shareButtonPressed() {
        userEvent.value = Event(PhotoDetailsUserEvent.SHARE_PRESSED)
    }

    fun saveButtonPressed() {
        userEvent.value = Event(PhotoDetailsUserEvent.SAVE_PRESSED)
    }

    fun photoImagePressed() {
        userEvent.value = Event(PhotoDetailsUserEvent.PHOTO_PRESSED)
    }

    fun saveImage(image: Bitmap) {
        viewModelScope.launch {
            val filename = "${photo.value?.author}_${image.width}x${image.height}.jpg"
            runCatching {
                withContext(Dispatchers.IO) {
                    val path = File("${Environment.getExternalStorageDirectory().path}/PicsumApp/Photos")
                    path.mkdirs()
                    val sdFile = File(path, filename)
                    val stream = FileOutputStream(sdFile)
                    image.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    stream.close()
                    sdFile.exists()
                }
            }.onSuccess {
                showNotification(filename)
            }.onFailure {
                error.value = ErrorModel(
                    it.message,
                    0,
                    ErrorStatus.NO_CONNECTION
                )
            }
        }
    }

    fun shareImage(image: Bitmap) {
        viewModelScope.launch {
            val filename = "${photo.value?.author}_${image.width}x${image.height}.jpg"
            runCatching {
                withContext(Dispatchers.IO) {
                    val path = File("${Environment.getExternalStorageDirectory().path}/PicsumApp/SharedPhotos")
                    path.mkdirs()
                    val sdFile = File(path, filename)
                    val stream = FileOutputStream(sdFile)
                    image.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    stream.close()
                    sdFile.exists()
                    sdFile.path
                }
            }.onSuccess {
                sharePhotoEvent.postValue(Event(it))
            }.onFailure {
                error.value = ErrorModel(
                    it.message,
                    0,
                    ErrorStatus.NO_CONNECTION
                )
            }
        }
    }

    private fun showNotification(fileName: String) {
        val builder = NotificationCompat.Builder(
            getApplication(),
            fileName
        )
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(getApplication<PicsumPhotosApp>().resources.getString(R.string.photo_was_saved))
            .setContentText(fileName)
        val notification = builder.build()
        val notificationManager: NotificationManager = getApplication<PicsumPhotosApp>().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = fileName
            val descriptionText = fileName
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(name, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(1, notification)
    }

}
