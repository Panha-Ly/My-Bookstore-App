package com.lypanha.mybookstore

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lypanha.mybookstore.adapter.BookSmallAdapter
import com.lypanha.mybookstore.model.Book

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // test data
        val popIds = setOf(21, 19, 1, 13)
        val popItems: List<Book> = Utils.allBooks.filter { book -> popIds.any { book.id == it } }

        val newItems: List<Book> = mutableListOf<Book>().apply {
            for (book in Utils.allBooks) {
                if (book.id == 20 || book.id == 24 || book.id == 18) {
                    add(book)
                }
            }
        }.toList()

        // Popular books recycler view
        val popularBooksRecView = view.findViewById<RecyclerView>(R.id.popularBooksRecView)
        val popBooksAdapter = BookSmallAdapter { book ->
            val intent = Intent(requireContext(), BookActivity::class.java).apply {
                putExtra("bookId", book.id)
                putExtra("fragmentName", "Home")
            }
            requireContext().startActivity(intent)
        }.apply { submitList(popItems) }
        popularBooksRecView.adapter = popBooksAdapter
        popularBooksRecView.setLayoutManager(
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )

        // New release books recycler view
        val newReleaseBooksRecView = view.findViewById<RecyclerView>(R.id.newReleaseBooksRecView)
        newReleaseBooksRecView.adapter = BookSmallAdapter { book ->
            val intent = Intent(requireContext(), BookActivity::class.java).apply {
                putExtra("bookId", book.id)
                putExtra("fragmentName", "Home")
            }
            requireContext().startActivity(intent)
        }.apply { submitList(newItems) }
        newReleaseBooksRecView.setLayoutManager(
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
    }
}