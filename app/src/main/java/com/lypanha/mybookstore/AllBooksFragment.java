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
import com.lypanha.mybookstore.model.Book;
import com.lypanha.mybookstore.model.Item;

import java.util.ArrayList;
import java.util.Collections;

public class AllBooksFragment extends Fragment {
    private RecyclerView allBooksRecView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_books, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ArrayList<Item> items = new ArrayList<>();
        // Add all books to the list
        for (Book book :
                Utils.getInstance().getAllBooks()) {
            items.add(new Item(Item.BOOK, book));

        }
        // Shuffle the books in the list
        Collections.shuffle(items);

        allBooksRecView = view.findViewById(R.id.allBooksRecView);
        allBooksRecView.setLayoutManager(new LinearLayoutManager(requireContext()));
        allBooksRecView.setAdapter(new BookAdapter(items,requireContext(),BookAdapter.VERTICAL_VIEW, BookAdapter.ALL_BOOKS));
    }
}