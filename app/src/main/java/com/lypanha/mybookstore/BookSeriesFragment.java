package com.lypanha.mybookstore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lypanha.mybookstore.adapter.BookAdapter;
import com.lypanha.mybookstore.model.Item;

import java.util.ArrayList;


public class BookSeriesFragment extends Fragment {
    private RecyclerView bookSeriesRecView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_series, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Item> items = new ArrayList<>(Utils.getInstance().getAllBooksBySeries());

        bookSeriesRecView = (RecyclerView) view.findViewById(R.id.bookSeriesRecView);
        bookSeriesRecView.setAdapter(new BookAdapter(items, requireContext(), BookAdapter.VERTICAL_VIEW, BookAdapter.BOOK_SERIES));
        bookSeriesRecView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

    }
}