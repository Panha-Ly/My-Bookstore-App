package com.lypanha.mybookstore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lypanha.mybookstore.databinding.ItemBookSmallBinding
import com.lypanha.mybookstore.model.Book

class BookSmallAdapter(
    private val onBookClickListener: (Book) -> Unit
) : ListAdapter<Book,BookSmallAdapter.BookSmallViewHolder>(BookSmallDiffCallback()) {

    class BookSmallViewHolder(
        private val binding: ItemBookSmallBinding,
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
                        onBookClickListener(book) // Trigger the Fragment's logic
                    }
                }
            }
        }

        fun bind(book: Book) {
            with(binding){
                txtBookName.text = book.name
                imgBook.setImageResource(book.imageId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookSmallViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BookSmallViewHolder(
            ItemBookSmallBinding.inflate(inflater, parent, false),
            { position ->
                // Guard against IndexOutOfBoundsException during fast list updates
                if (position >= 0 && position < currentList.size) { getItem(position) } else { null }
            },
            onBookClickListener
        )
    }

    override fun onBindViewHolder(holder: BookSmallViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}


class BookSmallDiffCallback: DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(
        oldItem: Book,
        newItem: Book
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Book,
        newItem: Book
    ): Boolean {
        // Since both are data classes, structural equality (==) works perfectly
        return oldItem == newItem
    }
}