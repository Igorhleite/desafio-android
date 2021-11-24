package com.picpay.desafio.android.data.remote.repository

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.RequestState
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUsers(): Flow<RequestState<List<User>>>
}