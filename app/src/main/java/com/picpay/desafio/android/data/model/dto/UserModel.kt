package com.picpay.desafio.android.data.model.dto

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("username")
    var username: String?,
    @SerializedName("img")
    var img: String?,
)