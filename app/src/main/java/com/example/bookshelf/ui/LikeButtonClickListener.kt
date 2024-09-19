package com.example.bookshelf.ui

interface LikeButtonClickListener {
    fun toggleStatus(id : String, isFav: Boolean)
}