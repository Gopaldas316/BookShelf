package com.example.bookshelf.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bookshelf.models.BookEntity
import com.example.bookshelf.models.UserEntity

@Database(entities = [UserEntity::class, BookEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao() : UserDAO

    abstract fun bookListDao() : BookListDAO

    companion object {
        @Volatile
        private var INSTANCE : UserDatabase? = null

        fun getDatabase(context : Context) : UserDatabase {
            if(INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, UserDatabase::class.java, "userDB"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}