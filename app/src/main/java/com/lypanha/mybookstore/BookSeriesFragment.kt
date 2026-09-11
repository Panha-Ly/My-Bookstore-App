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

class BookSeriesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_series, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = ArrayList<Item>(Utils.allBooksBySeries)

        val bookSeriesRecView = view.findViewById<RecyclerView>(R.id.bookSeriesRecView)
        bookSeriesRecView.setAdapter(
            BookAdapter(
                items,
                requireContext(),
                BookAdapter.VERTICAL_VIEW,
                BookAdapter.BOOK_SERIES
            )
        )
        bookSeriesRecView.setLayoutManager(
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        )
    }
}