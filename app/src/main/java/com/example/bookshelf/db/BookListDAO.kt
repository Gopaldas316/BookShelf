package com.example.bookshelf.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bookshelf.models.BookEntity
import com.example.bookshelf.models.BookListResponseItem

@Dao
interface BookListDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(bookList : List<BookEntity>)

    @Query("select * from book")
    fun getAllBooks() : LiveData<List<BookEntity>>

    @Query("update book set favourite = :isFav where id = :id")
    suspend fun updateBook(id: String, isFav: Boolean)
}