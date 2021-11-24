package com.picpay.desafio.android.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.picpay.desafio.android.data.local.dao.UserDao
import com.picpay.desafio.android.data.model.entities.UserEntity

@Database(version = 1, entities = [UserEntity::class])
abstract class UserDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}