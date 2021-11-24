package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.local.UserDataBase
import com.picpay.desafio.android.data.remote.PicPayService
import com.picpay.desafio.android.data.remote.repository.UserRepository
import com.picpay.desafio.android.data.remote.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideRemoteUserRepository(
        picPayService: PicPayService,
        userDataBase: UserDataBase
    ): UserRepository {
        return UserRepositoryImpl(picPayService, userDataBase)
    }
}