package com.lypanha.mybookstore

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lypanha.mybookstore.adapter.BookAdapterItem
import com.lypanha.mybookstore.adapter.BookLargeAdapter
import com.lypanha.mybookstore.databinding.FragmentBookSeriesBinding

class BookSeriesFragment : Fragment() {

    private var _binding: FragmentBookSeriesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookSeriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items: List<BookAdapterItem> = Utils.allBooksBySeries

        binding.bookSeriesRecView.adapter = BookLargeAdapter { book ->
            val intent = Intent(requireContext(), BookActivity::class.java).apply {
                putExtra("bookId", book.id)
                putExtra("fragmentName", "Book Series")
            }
            requireContext().startActivity(intent)
        }.apply { submitList(items) }
        binding.bookSeriesRecView.setLayoutManager(
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}