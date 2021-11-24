package com.picpay.desafio.android.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id: String,
    var name: String,
    var username: String,
    var img: String
) : Parcelable