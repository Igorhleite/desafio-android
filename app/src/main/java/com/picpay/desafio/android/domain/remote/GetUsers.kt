package com.picpay.desafio.android.domain.remote

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.remote.repository.UserRepository
import com.picpay.desafio.android.data.RequestState
import com.picpay.desafio.android.domain.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsers @Inject constructor(
    private val userRepository: UserRepository
) : UseCase.Empty<Flow<RequestState<List<User>>>> {
    override suspend fun invoke(): Flow<RequestState<List<User>>> {
        return userRepository.getUsers()
    }
}