package com.lypanha.mybookstore;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lypanha.mybookstore.adapter.BookLargeAdapter;
import com.lypanha.mybookstore.adapter.BookAdapterItem;

import java.util.ArrayList;


public class BookSeriesFragment extends Fragment {

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_series, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<BookAdapterItem> items = Utils.getInstance().getAllBooksBySeries();

        RecyclerView bookSeriesRecView = view.findViewById(R.id.bookSeriesRecView);
        BookLargeAdapter adapter = new BookLargeAdapter(items, book -> {
            Intent intent = new Intent(requireContext(), BookActivity.class);
            intent.putExtra("bookId", book.getId());
            intent.putExtra("fragmentName", "Book Series");
            startActivity(intent);
        });
        bookSeriesRecView.setAdapter(adapter);
        bookSeriesRecView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

    }
}