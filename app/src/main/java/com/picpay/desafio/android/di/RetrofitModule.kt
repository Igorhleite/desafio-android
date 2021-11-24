package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.remote.PicPayService
import com.picpay.desafio.android.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun provideUserApi(
        retrofit: Retrofit.Builder
    ): PicPayService {
        return retrofit
            .build()
            .create(PicPayService::class.java)
    }
}