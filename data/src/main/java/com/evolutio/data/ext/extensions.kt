package com.evolutio.data.ext

import com.evolutio.domain.shared.ResultWrapper
import retrofit2.HttpException
import retrofit2.Response

suspend fun <T : Any> safeApiCall(
    call: suspend () -> Response<T>
): ResultWrapper<Exception, T> {
    val result = try {
        call.invoke()
    } catch (e: Exception) {
        return if (e is HttpException) {
            ResultWrapper.build { throw Exception(e.message()) }
        } else {
            ResultWrapper.build { throw Exception(e.message) }
        }
    }

    return if (result.isSuccessful && result.body() != null) {
        // We could write a Kotlin Contract and infer to the compiler that
        // this result.body() can't be null here, but... im lazy right now :)
        return ResultWrapper.build { result.body()!! }
    } else
        ResultWrapper.build { throw Exception(result.message() ?: "Unknown error occurred") }

}
