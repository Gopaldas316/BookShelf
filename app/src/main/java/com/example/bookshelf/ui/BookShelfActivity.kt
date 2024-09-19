package com.example.bookshelf.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.example.bookshelf.R
import com.example.bookshelf.db.UserDatabase
import com.example.bookshelf.models.BookEntity
import com.example.bookshelf.models.BookListResponseItem
import com.example.bookshelf.repository.BookListRepository
import com.example.bookshelf.viewmodel.BookShelfViewModel
import com.example.bookshelf.viewmodel.BookShelfViewModelFactory
import java.time.Instant
import java.time.ZoneId

fun newSuccessActivityIntent(context: Context?): Intent {
    return Intent(context, BookShelfActivity::class.java)
}

class BookShelfActivity : AppCompatActivity(), LikeButtonClickListener {
    private lateinit var rvYears: RecyclerView
    private lateinit var rvBookList: RecyclerView
    private lateinit var bookShelfViewModel: BookShelfViewModel
    private lateinit var booksRepo : BookListRepository

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_shelf)

        rvYears = findViewById(R.id.rvYears)
        rvBookList = findViewById(R.id.rvBookList)

        booksRepo = BookListRepository(UserDatabase.getDatabase(applicationContext))
        bookShelfViewModel = ViewModelProvider(this, BookShelfViewModelFactory(applicationContext, booksRepo))[BookShelfViewModel::class.java]

        initData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initData() {
        setRecyclerViewForBooks()
        setRecyclerViewForYears()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setRecyclerViewForBooks() {
        bookShelfViewModel.bookListFromDb.observe(this) {
            rvBookList.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = BooksAdapter(mapping(it), this@BookShelfActivity)
                stopScroll()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setRecyclerViewForYears() {
        bookShelfViewModel.bookListFromDb.observe(this) {
            rvYears.apply {
                layoutManager = LinearLayoutManager(applicationContext, HORIZONTAL, false)
                adapter = YearsAdapter(bookShelfViewModel.getYearsFromBookList(it))
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun mapping(entities: List<BookEntity>) : ArrayList<BookListResponseItem> {
        val mappedData = ArrayList<BookListResponseItem>()
        entities.forEach{
            val item =  BookListResponseItem(
                id = it.id,
                image = it.image,
                popularity = it.popularity,
                publishedChapterDate = Instant.ofEpochMilli(it.publishedChapterDate).atZone(ZoneId.systemDefault()).toLocalDate().year.toLong(),
                score = it.score,
                title = it.title,
                favourite = it.favourite
            )
            mappedData.add(item)
        }

        return mappedData
    }

    override fun toggleStatus(id: String, isFav: Boolean) {
        bookShelfViewModel.updateBook(id, !isFav)
    }

}