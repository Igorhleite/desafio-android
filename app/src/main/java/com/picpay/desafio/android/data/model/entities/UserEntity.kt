package com.picpay.desafio.android.data.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "username")
    var username: String,
    @ColumnInfo(name = "img")
    var img: String,
)