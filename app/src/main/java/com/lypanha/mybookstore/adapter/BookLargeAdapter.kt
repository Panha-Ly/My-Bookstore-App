package com.lypanha.mybookstore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lypanha.mybookstore.databinding.ItemBookLargeBinding
import com.lypanha.mybookstore.databinding.ItemBookSeriesBinding
import com.lypanha.mybookstore.model.Book
import com.lypanha.mybookstore.model.BookSeries

class BookLargeAdapter(
    private val onBookClickListener: (Book) -> Unit
) : ListAdapter<BookAdapterItem,RecyclerView.ViewHolder>(BookLargeDiffCallback()) {

    class ContentViewHolder(
        private val binding: ItemBookLargeBinding,
        private val getBookAt: (Int) -> Book?,
        private val onBookClickListener: (Book) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.parent.setOnClickListener {
                // 1. Get the current dynamic position of this ViewHolder
                val position = bindingAdapterPosition

                // 2. Ensure the position is valid (not NO_POSITION)
                if (position != RecyclerView.NO_POSITION) {
                    // 3. Fetch the book safely and fire the click listener
                    getBookAt(position)?.let { book ->
                        onBookClickListener(book)
                    }
                }
            }
        }

        fun bind(book: Book) {
            with(binding) {
                txtBookName.text = book.name
                txtBookDescription.text = book.description
                imgBook.setImageResource(book.imageId)
            }
        }
    }

    class HeaderViewHolder(
        private val binding: ItemBookSeriesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(bookSeries: BookSeries) {
            binding.txtBookSeriesName.text = bookSeries.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_CONTENT) {
            ContentViewHolder(
                ItemBookLargeBinding.inflate(inflater, parent, false),
                { position ->
                    // 1. Guard against IndexOutOfBoundsException during fast list updates
                    if (position >= 0 && position < currentList.size) {
                        // 2. Safe-cast in case the item type shifted during an animation
                        (getItem(position) as? BookAdapterItem.Content)?.book
                    } else {
                        null
                    }
                },
                onBookClickListener
            )
        } else {
            HeaderViewHolder(ItemBookSeriesBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ContentViewHolder -> {
                val content: BookAdapterItem.Content = getItem(position) as BookAdapterItem.Content
                holder.bind(content.book)
            }
            is HeaderViewHolder -> {
                val header: BookAdapterItem.Header = getItem(position) as BookAdapterItem.Header
                holder.bind(header.bookSeries)
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (getItem(position) is BookAdapterItem.Content) {
            TYPE_CONTENT
        } else {
            TYPE_HEADER
        }

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_CONTENT = 1
    }
}

/**
 * Interface for [BookLargeAdapter]'s item array.
 * @see Header
 *
 * @see Content
 */
sealed interface BookAdapterItem {
    data class Content(val book: Book) : BookAdapterItem

    data class Header(val bookSeries: BookSeries) : BookAdapterItem
}

class BookLargeDiffCallback: DiffUtil.ItemCallback<BookAdapterItem>() {
    override fun areItemsTheSame(
        oldItem: BookAdapterItem,
        newItem: BookAdapterItem
    ): Boolean {
        // 1. If they are different classes, they are definitely not the same item
        if (oldItem::class != newItem::class) return false

        // 2. Smart-cast and check their respective unique IDs
        return when (oldItem) {
            is BookAdapterItem.Content -> {
                // newItem is automatically smart-cast to Content here as well
                oldItem.book.id == (newItem as BookAdapterItem.Content).book.id
            }
            is BookAdapterItem.Header -> {
                // newItem is automatically smart-cast to Header here as well
                oldItem.bookSeries.seriesId == (newItem as BookAdapterItem.Header).bookSeries.seriesId
            }
        }
    }

    override fun areContentsTheSame(
        oldItem: BookAdapterItem,
        newItem: BookAdapterItem
    ): Boolean {
        // Since both are data classes, structural equality (==) works perfectly
        return oldItem == newItem
    }
}