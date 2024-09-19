package com.example.bookshelf.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val name : String,
    val password : String,
    val country : String
)