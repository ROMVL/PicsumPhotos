package ua.romanik.vladislav.picsumphotos.presentation.base.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ua.romanik.vladislav.picsumphotos.domain.model.error.ErrorModel
import ua.romanik.vladislav.picsumphotos.presentation.base.livedata.Event
import ua.romanik.vladislav.picsumphotos.presentation.base.livedata.SingleLiveEvent

abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {

    val loading: SingleLiveEvent<Event<Boolean>> by lazy { SingleLiveEvent<Event<Boolean>>() }
    val error: MutableLiveData<ErrorModel> by lazy { MutableLiveData<ErrorModel>() }
    val userEvent: SingleLiveEvent<Event<*>> by lazy { SingleLiveEvent<Event<*>>() }

}