package com.example.bookshelf.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookshelf.repository.BookListRepository

class BookShelfViewModelFactory(private val context : Context, private val repository: BookListRepository) : ViewModelProvider
.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BookShelfViewModel(context, repository) as T
    }
}