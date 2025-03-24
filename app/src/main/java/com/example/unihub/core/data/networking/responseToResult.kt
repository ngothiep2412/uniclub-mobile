package com.example.unihub.core.data.networking

import com.example.unihub.core.domain.util.NetworkError
import com.example.unihub.core.domain.util.Result
import com.example.unihub.uniclub.data.network.TokenExpirationHandler
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

object ResponseHandler : KoinComponent {
    val tokenExpirationHandler: TokenExpirationHandler by inject()

    suspend inline fun <reified T> handleTokenExpiration() {
        tokenExpirationHandler.onTokenExpired()
    }
}

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, NetworkError> {
    return when (response.status.value) {
        in 200..299 -> {
            try {
                Result.Success(data = response.body<T>())
            } catch (e: NoTransformationFoundException) {
                Result.Error(error = NetworkError.SERIALIZATION)
            }
        }
        401 -> {
            Timber.d("Unauthorized request (401): Token hết hạn hoặc không hợp lệ")
            // Handle token expiration
            ResponseHandler.handleTokenExpiration<T>()
            Result.Error(error = NetworkError.UNAUTHORIZED)
        }
        408 -> Result.Error(error = NetworkError.REQUEST_TIMEOUT)
        429 -> Result.Error(error = NetworkError.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(error = NetworkError.SERVER_ERROR)
        else -> Result.Error(error = NetworkError.UNKNOWN)
    }
}