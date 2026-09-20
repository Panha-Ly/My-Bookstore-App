package com.lypanha.mybookstore;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lypanha.mybookstore.adapter.BookSmallAdapter;
import com.lypanha.mybookstore.databinding.FragmentHomeBinding;
import com.lypanha.mybookstore.model.Book;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
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
        BookSmallAdapter popularAdapter = new BookSmallAdapter(popItems, book -> {
            // All navigation logic stays at the top-level where it belongs!
            Intent intent = new Intent(requireContext(), BookActivity.class);
            intent.putExtra("bookId", book.getId());
            intent.putExtra("fragmentName", "Home");
            startActivity(intent);
        });
        binding.popularBooksRecView.setAdapter(popularAdapter);

        // New release books recycler view
        BookSmallAdapter newReleaseAdapter = new BookSmallAdapter(newItems, book -> {
            Intent intent = new Intent(requireContext(), BookActivity.class);
            intent.putExtra("bookId", book.getId());
            intent.putExtra("fragmentName", "Home");
            startActivity(intent);
        });
        binding.newReleaseBooksRecView.setAdapter(newReleaseAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}