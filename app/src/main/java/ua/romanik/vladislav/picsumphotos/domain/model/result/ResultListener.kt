package ua.romanik.vladislav.picsumphotos.domain.model.result

import ua.romanik.vladislav.picsumphotos.domain.model.error.ErrorModel

sealed class ResultListener<out T : Any> {
    data class Success<out T : Any>(val result: T) : ResultListener<T>()
    data class Error(val errorModel: ErrorModel) : ResultListener<ErrorModel>()
    data class Loading(val isLoading: Boolean) : ResultListener<Nothing>()
}