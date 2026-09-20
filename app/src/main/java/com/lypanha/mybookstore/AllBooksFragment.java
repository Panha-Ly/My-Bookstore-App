package com.lypanha.mybookstore;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lypanha.mybookstore.adapter.BookLargeAdapter;
import com.lypanha.mybookstore.databinding.FragmentAllBooksBinding;
import com.lypanha.mybookstore.model.Book;
import com.lypanha.mybookstore.adapter.BookAdapterItem;

import java.util.ArrayList;
import java.util.Collections;

public class AllBooksFragment extends Fragment {

    private FragmentAllBooksBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentAllBooksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ArrayList<BookAdapterItem> items = new ArrayList<>();
        // Add all books to the list
        for (Book book : Utils.getInstance().getAllBooks()) {
            items.add(new BookAdapterItem.Content(book));
        }
        // Shuffle the books in the list
        Collections.shuffle(items);

        BookLargeAdapter adapter = new BookLargeAdapter(items, book -> {
            Intent intent = new Intent(requireContext(), BookActivity.class);
            intent.putExtra("bookId", book.getId());
            intent.putExtra("fragmentName", "All Books");
            startActivity(intent);
        });
        binding.allBooksRecView.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}