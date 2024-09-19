package com.example.bookshelf.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
data class BookEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val image: String,
    val popularity: Int,
    val score: Float,
    val title: String,
    val publishedChapterDate: Long,
    val favourite: Boolean
)
