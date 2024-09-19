package com.example.bookshelf.models

data class BookListResponseItem(
    val id: String,
    val image: String,
    val popularity: Int,
    val publishedChapterDate: Long,
    val score: Float,
    val title: String,
    val favourite: Boolean = false
)