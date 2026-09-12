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
import java.util.Collections

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
        val items: ArrayList<Item> = Utils.allBooks
            .map { Item(Item.BOOK, it) }
            .toCollection(arrayListOf())

        // Shuffle the books in the list
//        Collections.shuffle(items)
        items.shuffle()

        val allBooksRecView = view.findViewById<RecyclerView>(R.id.allBooksRecView)
        allBooksRecView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        allBooksRecView.adapter = BookAdapter(
            items,
            requireContext(),
            BookAdapter.VERTICAL_VIEW,
            BookAdapter.ALL_BOOKS
        )
    }
}