package com.picpay.desafio.android.utils

import com.picpay.desafio.android.data.RequestState
import com.picpay.desafio.android.data.model.error.ResponseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline getDataFromLocalDataBase: () -> Flow<ResultType>,
    crossinline getDataFromApi: suspend () -> Response<RequestType>,
    crossinline insertDataInLocalDataBase: suspend (RequestType) -> Unit
) = flow {
    val data = try {
        with(getDataFromApi()) {
            if (isSuccessful) {
                body()?.let { responseBody ->
                    insertDataInLocalDataBase(responseBody)
                }
                getDataFromLocalDataBase().map { savedData ->
                    RequestState.ResponseSuccess(savedData)
                }
            } else {
                getDataFromLocalDataBase().map { savedData ->
                    RequestState.ResponseFailure(
                        data = savedData,
                        error = parseResponseError()
                    )
                }
            }
        }
    } catch (e: Exception) {
        getDataFromLocalDataBase().map { savedData ->
            RequestState.ResponseException(savedData, e)
        }
    }
    emitAll(data)
}

fun <T> Response<T>.parseResponseError(): ResponseError {
    return ResponseError(code = this.code().toString(), message = this.errorBody()?.string() ?: "")
}