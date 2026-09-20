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
import com.lypanha.mybookstore.databinding.FragmentCartBinding;
import com.lypanha.mybookstore.model.Book;
import com.lypanha.mybookstore.adapter.BookAdapterItem;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    private FragmentCartBinding binding;
    private BookLargeAdapter adapter;
    private ArrayList<BookAdapterItem> items;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        items = new ArrayList<>();
        for (Book book : Utils.getInstance().getBooksInCart()) {
            items.add(new BookAdapterItem.Content(book));
        }
        if (items.isEmpty()) binding.noBooksInCart.setVisibility(View.VISIBLE);
        else binding.noBooksInCart.setVisibility(View.GONE);


        adapter = new BookLargeAdapter(items, book -> {
            Intent intent = new Intent(requireContext(), BookActivity.class);
            intent.putExtra("bookId", book.getId());
            intent.putExtra("fragmentName", "Cart");
            startActivity(intent);
        });
        binding.cartRecView.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        items.clear();
        for (Book book : Utils.getInstance().getBooksInCart()) {
            items.add(new BookAdapterItem.Content(book));
        }
        if (items.isEmpty()) {
            binding.noBooksInCart.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}