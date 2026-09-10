package com.lypanha.mybookstore.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.lypanha.mybookstore.BookActivity
import com.lypanha.mybookstore.R
import com.lypanha.mybookstore.model.Book
import com.lypanha.mybookstore.model.BookSeries
import com.lypanha.mybookstore.model.Item

class BookAdapter(
    private val items: ArrayList<Item>,
    private val context: Context,
    private val orientation: Int,
    private val fragmentName: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class HorizontalBookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val parent: ConstraintLayout = itemView.findViewById(R.id.parent)
        private val bookImage: ImageView = itemView.findViewById(R.id.imgBook)
        private val bookName: TextView = itemView.findViewById(R.id.txtBookName)

        fun bind(book: Book, onClick: View.OnClickListener) {
            parent.setOnClickListener(onClick)
            bookImage.setImageResource(book.imageId)
            bookName.text = book.name
        }
    }

    class VerticalBookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val parent: ConstraintLayout = itemView.findViewById(R.id.parent)
        private val bookImage: ImageView = itemView.findViewById(R.id.imgBook)
        private val bookName: TextView = itemView.findViewById(R.id.txtBookName)
        private val bookDescription: TextView = itemView.findViewById(R.id.txtBookDescription)

        fun bind(book: Book, onClick: View.OnClickListener) {
            parent.setOnClickListener(onClick)
            bookImage.setImageResource(book.imageId)
            bookDescription.text = book.description
            bookName.text = book.name
        }

    }

    class BookSeriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bookSeriesName: TextView = itemView.findViewById(R.id.txtBookSeriesName)

        fun bind(bookSeries: BookSeries) {
            bookSeriesName.text = bookSeries.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)

        // type1 for horizontal view, type2 for vertical view and type3 for vertical view with dynamic view type
        if (orientation == HORIZONTAL_VIEW) {
            return HorizontalBookViewHolder(
                inflater.inflate(
                    R.layout.item_book_row_type1,
                    parent,
                    false
                )
            )
        } else {
            if (viewType == Item.BOOK) {
                return VerticalBookViewHolder(
                    inflater.inflate(
                        R.layout.item_book_row_type2,
                        parent,
                        false
                    )
                )
            } else {
                return BookSeriesViewHolder(
                    inflater.inflate(
                        R.layout.item_book_row_type3,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (orientation == HORIZONTAL_VIEW) {
            val bookViewHolder = holder as HorizontalBookViewHolder
            val book = items[position].item as Book

            bookViewHolder.bind(book){ _: View? ->
                val intent = Intent(context, BookActivity::class.java)
                intent.putExtra("bookId", book.id)
                intent.putExtra("fragmentName", fragmentName)
                context.startActivity(intent)
            }

        } else if (orientation == VERTICAL_VIEW) {
            if (getItemViewType(position) == Item.BOOK) {
                val bookViewHolder = holder as VerticalBookViewHolder
                val book = items[position].item as Book

                bookViewHolder.bind(book){ _: View? ->
                    val intent = Intent(context, BookActivity::class.java)
                    intent.putExtra("bookId", book.id)
                    intent.putExtra("fragmentName", fragmentName)
                    context.startActivity(intent)
                }

            } else {
                val bookViewHolder = holder as BookSeriesViewHolder
                val bookSeries = items[position].item as BookSeries

                bookViewHolder.bind(bookSeries)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return if (items[position].itemTypeId == Item.BOOK) { 0 } else { 1 }
    }

    companion object {
        const val HOME: String = "Home"
        const val ALL_BOOKS: String = "All Books"
        const val BOOK_SERIES: String = "Book Series"
        const val CART: String = "Cart"

        const val HORIZONTAL_VIEW: Int = 0
        const val VERTICAL_VIEW: Int = 1
    }
}
