package com.picpay.desafio.android.data.remote.repository

import androidx.room.withTransaction
import com.picpay.desafio.android.data.RequestState
import com.picpay.desafio.android.data.local.UserDataBase
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.model.dto.UserModel
import com.picpay.desafio.android.data.model.entities.UserEntity
import com.picpay.desafio.android.data.remote.PicPayService
import com.picpay.desafio.android.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val picPayApi: PicPayService,
    private val userDataBase: UserDataBase
) : UserRepository {
    override suspend fun getUsers(
    ): Flow<RequestState<List<User>>> {
        val userDao = userDataBase.userDao()
        return networkBoundResource(
            getDataFromLocalDataBase = {
                userDao.getAllUsers().map {
                    it.fromEntityListToUserList()
                }
            },
            getDataFromApi = {
                picPayApi.getUsers()
            },
            insertDataInLocalDataBase = { users ->
                userDataBase.withTransaction {
                    userDao.deleteALlUsers()
                    userDao.insertAllUsers(users.fromDomainListToEntityList())
                }
            }
        )
    }
}

fun List<UserModel>.fromDomainListToEntityList(): List<UserEntity> {
    return this.map { it.fromDomainToEntity() }
}

fun UserModel?.fromDomainToEntity(): UserEntity {
    return UserEntity(
        id = this?.id.toString()?: "",
        name = this?.name?: "",
        username = this?.username?: "",
        img = this?.img?: "url"
    )
}

fun UserEntity.fromEntityToUser(): User {
    return User(
        id = this.id,
        name = this.name,
        username = this.username,
        img = this.img
    )
}

fun List<UserEntity>.fromEntityListToUserList(): List<User> {
    return this.map { it.fromEntityToUser() }
}