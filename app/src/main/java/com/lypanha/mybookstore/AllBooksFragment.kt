package com.lypanha.mybookstore

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lypanha.mybookstore.adapter.BookAdapterItem
import com.lypanha.mybookstore.adapter.BookLargeAdapter
import com.lypanha.mybookstore.databinding.FragmentAllBooksBinding
import kotlin.random.Random

class AllBooksFragment : Fragment() {

    private var _binding: FragmentAllBooksBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllBooksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // All books list
        val items: List<BookAdapterItem> = Utils.allBooks
            .map { BookAdapterItem.Content(it) }
            .shuffled(Random(69)) // Shuffle the books in the list

        binding.allBooksRecView.adapter = BookLargeAdapter { book ->
            val intent = Intent(requireContext(), BookActivity::class.java).apply {
                putExtra("bookId", book.id)
                putExtra("fragmentName", "All Books")
            }
            requireContext().startActivity(intent)
        }.apply { submitList(items) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}