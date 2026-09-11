package com.lypanha.mybookstore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lypanha.mybookstore.adapter.BookAdapter
import com.lypanha.mybookstore.model.Item

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
        val popItems = arrayListOf<Item>()
        val popIds = listOf(21,19,1,13)
        popItems.addAll(
            Utils.allBooks
                .filter { book -> popIds.any { book.id == it } }
                .map { Item(Item.BOOK, it) }
        )

        val newItems = ArrayList<Item>()
        for (book in Utils.allBooks) {
            if (book.id == 20 || book.id == 24 || book.id == 18) {
                newItems.add(Item(Item.BOOK, book))
            }
        }

        // Popular books recycler view
        val popularBooksRecView = view.findViewById<RecyclerView>(R.id.popularBooksRecView)
        val popBooksAdapter = BookAdapter(popItems, requireContext(), BookAdapter.HORIZONTAL_VIEW, BookAdapter.HOME)
        popularBooksRecView.setAdapter(popBooksAdapter)
        popularBooksRecView.setLayoutManager(
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )

        // New release books recycler view
        val newReleaseBooksRecView = view.findViewById<RecyclerView>(R.id.newReleaseBooksRecView)
        newReleaseBooksRecView.setAdapter(
            BookAdapter(
                newItems,
                requireContext(),
                BookAdapter.HORIZONTAL_VIEW,
                BookAdapter.HOME
            )
        )
        newReleaseBooksRecView.setLayoutManager(
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
    }
}