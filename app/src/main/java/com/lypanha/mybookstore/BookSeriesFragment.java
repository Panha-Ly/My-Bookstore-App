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
import com.lypanha.mybookstore.databinding.FragmentBookSeriesBinding;
import com.lypanha.mybookstore.adapter.BookAdapterItem;

import java.util.ArrayList;


public class BookSeriesFragment extends Fragment {

    private FragmentBookSeriesBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentBookSeriesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<BookAdapterItem> items = Utils.getInstance().getAllBooksBySeries();

        BookLargeAdapter adapter = new BookLargeAdapter(items, book -> {
            Intent intent = new Intent(requireContext(), BookActivity.class);
            intent.putExtra("bookId", book.getId());
            intent.putExtra("fragmentName", "Book Series");
            startActivity(intent);
        });
        binding.bookSeriesRecView.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}