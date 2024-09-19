package com.example.bookshelf.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.bookshelf.db.UserDatabase
import com.example.bookshelf.models.BookEntity
import com.example.bookshelf.models.BookListResponse
import com.example.bookshelf.models.BookListResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookListRepository(private val database: UserDatabase) {

    val allBooksList : LiveData<List<BookEntity>> = database.bookListDao().getAllBooks()

    suspend fun insertBooks(bookList: BookListResponse) {
        withContext(Dispatchers.IO) {
            val listOfBook = mutableListOf<BookEntity>()
            bookList.forEach {
                val item = BookEntity(
                    id = it.id,
                    image = it.image,
                    popularity = it.popularity,
                    score = it.score,
                    title = it.title,
                    publishedChapterDate = it.publishedChapterDate.toLong(),
                    favourite = false
                )
                listOfBook.add(item)
            }
            database.bookListDao().insertAll(bookList = listOfBook)
        }
    }

    suspend fun updateBook(id : String, isFav: Boolean) {
        withContext(Dispatchers.IO) {
            database.bookListDao().updateBook(id, isFav)
        }
    }

}