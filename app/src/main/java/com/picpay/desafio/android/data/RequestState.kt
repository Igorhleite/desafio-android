package com.picpay.desafio.android.data

import com.picpay.desafio.android.data.model.error.ResponseError
import okhttp3.ResponseBody

sealed class RequestState<out T> {
    data class ResponseSuccess<out R>(val data: R) : RequestState<R>()
    data class ResponseFailure<out R>(val data: R, val error: ResponseError?) :
        RequestState<R>()
    data class ResponseException<out R>(val data: R, val exception: Exception) : RequestState<R>()
    data class Loading(val status: Boolean) : RequestState<Nothing>()
}