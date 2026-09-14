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
import com.lypanha.mybookstore.adapter.BookSmallAdapter;
import com.lypanha.mybookstore.model.Book;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // test data
        ArrayList<Book> popItems = Utils
                .getInstance().getAllBooks().stream()
                .filter(book -> book.getId() == 21 || book.getId() == 19 || book.getId() == 1 || book.getId() == 13)
                .collect(Collectors.toCollection(ArrayList::new));

        Set<Integer> newItemIds = Set.of(20, 24, 18);
        ArrayList<Book> newItems = Utils
                .getInstance().getAllBooks().stream()
                .filter(book -> newItemIds.contains(book.getId()))
                .collect(Collectors.toCollection(ArrayList::new));

//         Popular books recycler view
        RecyclerView popularBooksRecView = view.findViewById(R.id.popularBooksRecView);
        BookSmallAdapter popularAdapter = new BookSmallAdapter(popItems, book -> {
            // All navigation logic stays at the top-level where it belongs!
            Intent intent = new Intent(requireContext(), BookActivity.class);
            intent.putExtra("bookId", book.getId());
            intent.putExtra("fragmentName", "Home");
            startActivity(intent);
        });
        popularBooksRecView.setAdapter(popularAdapter);
        popularBooksRecView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false));

        // New release books recycler view
        RecyclerView newReleaseBooksRecView = view.findViewById(R.id.newReleaseBooksRecView);
        BookSmallAdapter newReleaseAdapter = new BookSmallAdapter(newItems, book -> {
            Intent intent = new Intent(requireContext(), BookActivity.class);
            intent.putExtra("bookId", book.getId());
            intent.putExtra("fragmentName", "Home");
            startActivity(intent);
        });
        newReleaseBooksRecView.setAdapter(newReleaseAdapter);
        newReleaseBooksRecView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false));

    }
}