package ua.romanik.vladislav.picsumphotos.domain.usecase.base

import kotlinx.coroutines.*
import ua.romanik.vladislav.picsumphotos.data.mapper.CloudErrorMapper
import ua.romanik.vladislav.picsumphotos.domain.model.error.ErrorModel

typealias CompletionBlock<T> = UseCase.Request<T>.() -> Unit

abstract class UseCase<T>(private val errorUtil: CloudErrorMapper) {

    protected abstract suspend fun executeOnBackground(): T

    suspend fun execute(block: CompletionBlock<T>) {
        val response = Request<T>().apply { block() }
        runCatching {
            withContext(Dispatchers.IO) {
                executeOnBackground()
            }
        }.onSuccess {
            response(it)
        }.onFailure { error ->
            if (error is CancellationException) {
                response(error)
            } else {
                response(errorUtil.mapToDomainErrorException(error))
            }
        }
    }

    class Request<T> {
        private var onComplete: ((T) -> Unit)? = null
        private var onError: ((ErrorModel) -> Unit)? = null
        private var onCancel: ((CancellationException) -> Unit)? = null

        fun onComplete(block: (T) -> Unit) {
            onComplete = block
        }

        fun onError(block: (ErrorModel) -> Unit) {
            onError = block
        }

        fun onCancel(block: (CancellationException) -> Unit) {
            onCancel = block
        }


        operator fun invoke(result: T) {
            onComplete?.invoke(result)
        }

        operator fun invoke(error: ErrorModel) {
            onError?.invoke(error)
        }

        operator fun invoke(error: CancellationException) {
            onCancel?.invoke(error)
        }
    }
}