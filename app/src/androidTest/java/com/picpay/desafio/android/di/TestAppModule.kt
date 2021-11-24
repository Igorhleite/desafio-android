package com.picpay.desafio.android.di

import android.content.Context
import androidx.room.Room
import com.picpay.desafio.android.data.local.UserDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Named("test_user_db")
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, UserDataBase::class.java)
            .allowMainThreadQueries()
            .build()
}