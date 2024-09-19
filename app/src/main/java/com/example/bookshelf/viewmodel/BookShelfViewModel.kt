package com.example.bookshelf.viewmodel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookshelf.models.BookEntity
import com.example.bookshelf.models.BookListResponse
import com.example.bookshelf.models.BookListResponseItem
import com.example.bookshelf.repository.BookListRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId

class BookShelfViewModel(private val context: Context, private val repository: BookListRepository) : ViewModel() {

    val bookListFromDb : LiveData<List<BookEntity>> = repository.allBooksList
    init{
        insertBookList(getListOFBooksFromJson())
    }

    private fun insertBookList(bookList: BookListResponse) {
        viewModelScope.launch {
            repository.insertBooks(bookList)
        }
    }

    fun updateBook(id: String, isFav: Boolean) {
        viewModelScope.launch {
            repository.updateBook(id, isFav)
        }
    }

    private fun getListOFBooksFromJson() : BookListResponse {
        val inputStream = context.assets.open("book_list.json")
        val size : Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        val json = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        return gson.fromJson(json, BookListResponse::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getYearsFromBookList(bookList: List<BookEntity>) : List<String> {
        return bookList.map {
            val date = Instant.ofEpochMilli(it.publishedChapterDate).atZone(ZoneId.systemDefault()).toLocalDate()
            date.year.toString()
        }.toSet().toList()
    }

}