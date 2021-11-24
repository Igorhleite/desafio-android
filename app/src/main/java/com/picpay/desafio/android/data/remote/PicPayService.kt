package com.picpay.desafio.android.data.remote

import com.picpay.desafio.android.data.model.dto.UserModel
import retrofit2.Response
import retrofit2.http.GET

interface PicPayService {
    @GET("users")
    suspend fun getUsers(): Response<List<UserModel>>
}