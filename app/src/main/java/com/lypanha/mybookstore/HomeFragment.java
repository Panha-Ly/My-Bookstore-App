package com.lypanha.mybookstore;

import static com.lypanha.mybookstore.adapter.BookAdapter.HORIZONTAL_VIEW;

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

public class HomeFragment extends Fragment {
    private RecyclerView popularBooksRecView;
    private RecyclerView newReleaseBooksRecView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // test data
        ArrayList<Item> popItems = new ArrayList<>();
        for (Book book :
                Utils.getInstance().getAllBooks()) {
            if (book.getId() == 21 || book.getId() == 19 || book.getId() == 1 || book.getId() == 13){
                popItems.add(new Item(Item.BOOK,book));
            }
        }

        ArrayList<Item> newItems = new ArrayList<>();
        for (Book book :
                Utils.getInstance().getAllBooks()) {
            if (book.getId() == 20 || book.getId() == 24 || book.getId() == 18){
                newItems.add(new Item(Item.BOOK,book));
            }

        }


//         Popular books recycler view
        popularBooksRecView = view.findViewById(R.id.popularBooksRecView);
        BookAdapter popBooksAdapter = new BookAdapter(popItems, view.getContext(), HORIZONTAL_VIEW, BookAdapter.HOME);
        popularBooksRecView.setAdapter(popBooksAdapter);
        popularBooksRecView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false));

        // New release books recycler view
        newReleaseBooksRecView = view.findViewById(R.id.newReleaseBooksRecView);
        newReleaseBooksRecView.setAdapter(new BookAdapter(newItems, view.getContext(), HORIZONTAL_VIEW, BookAdapter.HOME));
        newReleaseBooksRecView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false));

    }
}