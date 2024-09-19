package com.example.bookshelf.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookshelf.R
import com.example.bookshelf.models.BookListResponseItem

class BooksAdapter(private val bookList: ArrayList<BookListResponseItem>? = arrayListOf(), private val context: Context) : RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {

    private var mListener: LikeButtonClickListener = context as LikeButtonClickListener
    private var activeYear = 0

    inner class BooksViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val image: ImageView = view.findViewById(R.id.ivImage)
        private val title: TextView = view.findViewById(R.id.tvTitle)
        private val score: TextView = view.findViewById(R.id.tvScore)
        private val published: TextView = view.findViewById(R.id.tvPublished)
        private val favourite : ImageView = view.findViewById(R.id.ivFavourite)

        fun bind(item: BookListResponseItem) {
            Glide.with(context).load(item.image).into(image)
            title.text = item.title
            score.text = item.score.toString()
            published.text = "Published in " + item.publishedChapterDate.toString()
            favourite.setOnClickListener{
                mListener.toggleStatus(item.id, item.favourite)
            }
            if(item.favourite)
                favourite.setImageResource(R.drawable.liked)
            else favourite.setImageResource(R.drawable.unlike)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item_book, parent, false)
        return BooksViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bookList?.size ?: 0
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        holder.bind(bookList!![position])
    }

}