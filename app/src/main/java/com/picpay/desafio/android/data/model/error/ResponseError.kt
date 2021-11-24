package com.picpay.desafio.android.data.model.error

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize
import okhttp3.ResponseBody
import retrofit2.Response

@Parcelize
data class ResponseError(
    var code: String = "",
    var message: String = ""
) : Parcelable