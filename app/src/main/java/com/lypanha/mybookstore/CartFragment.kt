package com.lypanha.mybookstore

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lypanha.mybookstore.adapter.BookAdapterItem
import com.lypanha.mybookstore.adapter.BookLargeAdapter

class CartFragment : Fragment() {
    private lateinit var adapter: BookLargeAdapter
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
        val items: List<BookAdapterItem> = Utils.booksInCart
            .map { BookAdapterItem.Content(it) }
            .apply { txtNoBooksInCart.visibility = if (isEmpty()) View.VISIBLE else View.GONE }

        val cartRecView = view.findViewById<RecyclerView>(R.id.cartRecView)
        adapter = BookLargeAdapter { book ->
            val intent = Intent(requireContext(), BookActivity::class.java).apply {
                putExtra("bookId", book.id)
                putExtra("fragmentName", "Cart")
            }
            requireContext().startActivity(intent)
        }.apply { submitList(items) }
        cartRecView.adapter = this.adapter
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

        Utils.booksInCart
            .map { BookAdapterItem.Content(it) }
            .apply { txtNoBooksInCart.visibility = if (isEmpty()) View.VISIBLE else View.GONE }
            .also { freshItems: List<BookAdapterItem> ->
                adapter.submitList(freshItems)
            }

    }
}