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
import com.lypanha.mybookstore.model.Book;
import com.lypanha.mybookstore.adapter.BookAdapterItem;

import java.util.ArrayList;
import java.util.Collections;

public class AllBooksFragment extends Fragment {


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_books, container, false);
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

        RecyclerView allBooksRecView = view.findViewById(R.id.allBooksRecView);
        BookLargeAdapter adapter = new BookLargeAdapter(items, book -> {
            Intent intent = new Intent(requireContext(), BookActivity.class);
            intent.putExtra("bookId", book.getId());
            intent.putExtra("fragmentName", "All Books");
            startActivity(intent);
        });
        allBooksRecView.setAdapter(adapter);
        allBooksRecView.setLayoutManager(new LinearLayoutManager(requireContext()));

    }
}