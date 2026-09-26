package com.lypanha.mybookstore

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lypanha.mybookstore.adapter.BookSmallAdapter
import com.lypanha.mybookstore.databinding.FragmentHomeBinding
import com.lypanha.mybookstore.model.Book

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
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
        val popBooksAdapter = BookSmallAdapter { book ->
            val intent = Intent(requireContext(), BookActivity::class.java).apply {
                putExtra("bookId", book.id)
                putExtra("fragmentName", "Home")
            }
            requireContext().startActivity(intent)
        }.apply { submitList(popItems) }
        binding.popularBooksRecView.adapter = popBooksAdapter
        binding.popularBooksRecView.setLayoutManager(
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )

        // New release books recycler view
        binding.newReleaseBooksRecView.adapter = BookSmallAdapter { book ->
            val intent = Intent(requireContext(), BookActivity::class.java).apply {
                putExtra("bookId", book.id)
                putExtra("fragmentName", "Home")
            }
            requireContext().startActivity(intent)
        }.apply { submitList(newItems) }
        binding.newReleaseBooksRecView.setLayoutManager(
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}