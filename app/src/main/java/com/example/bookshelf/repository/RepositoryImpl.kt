package com.example.bookshelf.repository

import androidx.lifecycle.LiveData
import com.example.bookshelf.db.UserDatabase
import com.example.bookshelf.models.UserEntity

class RepositoryImpl(private val database : UserDatabase) : Repository {
    override suspend fun insertUser(user: UserEntity) {
        database.userDao().insertUser(user)
    }

    override suspend fun isAnExistingUser(user: UserEntity) : LiveData<Boolean> {
        return database.userDao().isAnExistingUser(userName = user.name, userPassword = user.password)
    }
}