package com.lypanha.mybookstore

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lypanha.mybookstore.adapter.BookAdapterItem
import com.lypanha.mybookstore.adapter.BookLargeAdapter
import kotlin.random.Random

class AllBooksFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_books, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Add all books to the list
        val items: List<BookAdapterItem> = Utils.allBooks
            .map { BookAdapterItem.Content(it) }
            .shuffled(Random(69)) // Shuffle the books in the list

        val allBooksRecView = view.findViewById<RecyclerView>(R.id.allBooksRecView)
        allBooksRecView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        allBooksRecView.adapter = BookLargeAdapter { book ->
            val intent = Intent(requireContext(), BookActivity::class.java).apply {
                putExtra("bookId", book.id)
                putExtra("fragmentName", "All Books")
            }
            requireContext().startActivity(intent)
        }.apply { submitList(items) }
    }
}