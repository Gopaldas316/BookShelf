package com.example.bookshelf.repository

import androidx.lifecycle.LiveData
import com.example.bookshelf.models.UserEntity

interface Repository {
    suspend fun insertUser(user : UserEntity)

    suspend fun isAnExistingUser(user : UserEntity) : LiveData<Boolean>
}