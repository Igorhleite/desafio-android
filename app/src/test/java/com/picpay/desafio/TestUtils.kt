package com.picpay.desafio

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.model.dto.UserModel

fun UserModel.fromDomainToUser(): User {
    return User(
        id = this.id.toString(),
        name = this.name?: "",
        username = this.username?: "",
        img = this.img?: ""
    )
}

fun List<UserModel>.fromDomainListToUserList(): List<User> {
    return this.map { it.fromDomainToUser() }
}