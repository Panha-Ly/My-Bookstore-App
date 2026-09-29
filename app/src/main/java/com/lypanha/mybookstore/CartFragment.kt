package com.lypanha.mybookstore

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lypanha.mybookstore.adapter.BookAdapterItem
import com.lypanha.mybookstore.adapter.BookLargeAdapter
import com.lypanha.mybookstore.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: BookLargeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items: List<BookAdapterItem> = Utils.booksInCart.map { BookAdapterItem.Content(it) }
        binding.noBooksInCart.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE

        BookLargeAdapter { book ->
            val intent = Intent(requireContext(), BookActivity::class.java).apply {
                putExtra("bookId", book.id)
                putExtra("fragmentName", "Cart")
            }
            requireContext().startActivity(intent)
        }.apply {
            submitList(items)
        }.also {
            binding.cartRecView.adapter = it
            adapter = it
        }

    }

    override fun onResume() {
        super.onResume()
        val freshItems: List<BookAdapterItem> = Utils.booksInCart.map { BookAdapterItem.Content(it) }
        binding.noBooksInCart.visibility = if (freshItems.isEmpty()) View.VISIBLE else View.GONE
        adapter.submitList(freshItems)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}