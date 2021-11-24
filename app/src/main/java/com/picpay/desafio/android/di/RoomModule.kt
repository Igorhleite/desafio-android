package com.picpay.desafio.android.di

import android.content.Context
import androidx.room.Room
import com.picpay.desafio.android.data.local.UserDataBase
import com.picpay.desafio.android.data.local.dao.UserDao
import com.picpay.desafio.android.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideUserDataBase(
        @ApplicationContext context: Context
    ): UserDataBase {
        return Room.databaseBuilder(
            context, UserDataBase::class.java,
            Constants.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }
}