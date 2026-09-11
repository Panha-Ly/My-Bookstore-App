package com.lypanha.mybookstore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lypanha.mybookstore.adapter.BookAdapter
import com.lypanha.mybookstore.model.Item

class CartFragment : Fragment() {
    private lateinit var adapter: BookAdapter
    private lateinit var items: ArrayList<Item>
    private lateinit var txtNoBooksInCart: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtNoBooksInCart = view.findViewById(R.id.noBooksInCart)
        items = arrayListOf<Item>().apply {
            addAll(Utils.booksInCart.map { Item(Item.BOOK, it) })
        }
        if (items.isEmpty()) txtNoBooksInCart.visibility = View.VISIBLE

        val cartRecView = view.findViewById<RecyclerView>(R.id.cartRecView)
        adapter = BookAdapter(items, requireContext(), BookAdapter.VERTICAL_VIEW, BookAdapter.CART)
        cartRecView.setAdapter(adapter)
        cartRecView.setLayoutManager(
            LinearLayoutManager(
                this.requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        )

    }

    override fun onResume() {
        super.onResume()
        with(items){
            clear()
            addAll(Utils.booksInCart.map { Item(Item.BOOK, it) })
        }
        if (items.isEmpty()) txtNoBooksInCart.visibility = View.VISIBLE
        adapter.notifyDataSetChanged()
    }
}