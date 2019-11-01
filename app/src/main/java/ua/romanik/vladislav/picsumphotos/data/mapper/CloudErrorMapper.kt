package ua.romanik.vladislav.picsumphotos.data.mapper

import ua.romanik.vladislav.picsumphotos.domain.model.error.ErrorModel
import ua.romanik.vladislav.picsumphotos.domain.model.error.ErrorStatus
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class CloudErrorMapper {

    fun mapToDomainErrorException(throwable: Throwable?): ErrorModel {
        return when (throwable) {
            is SocketTimeoutException -> {
                ErrorModel(
                    "TIME OUT",
                    0,
                    ErrorStatus.TIMEOUT
                )
            }

            is IOException -> {
                ErrorModel(
                    "CHECK CONNECTION",
                    0,
                    ErrorStatus.NO_CONNECTION
                )
            }

            is UnknownHostException -> {
                ErrorModel(
                    "CHECK CONNECTION",
                    0,
                    ErrorStatus.NO_CONNECTION
                )
            }
            else -> ErrorModel(
                throwable?.message ?: "",
                0,
                ErrorStatus.NO_CONNECTION
            )
        }
    }
}