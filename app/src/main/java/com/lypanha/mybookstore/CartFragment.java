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
import android.widget.TextView;
import com.lypanha.mybookstore.adapter.BookLargeAdapter;
import com.lypanha.mybookstore.model.Book;
import com.lypanha.mybookstore.adapter.BookAdapterItem;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    private BookLargeAdapter adapter;
    private ArrayList<BookAdapterItem> items;
    private TextView noBooksInCart;


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noBooksInCart = view.findViewById(R.id.noBooksInCart);
        items = new ArrayList<>();
        for (Book book :
                Utils.getInstance().getBooksInCart()) {
            items.add(new BookAdapterItem.Content(book));
        }
        if (items.isEmpty()) {
            noBooksInCart.setVisibility(View.VISIBLE);
        }


        RecyclerView cartRecView = view.findViewById(R.id.cartRecView);
        adapter = new BookLargeAdapter(items, book -> {
            Intent intent = new Intent(requireContext(), BookActivity.class);
            intent.putExtra("bookId", book.getId());
            intent.putExtra("fragmentName", "Cart");
            startActivity(intent);
        });
        cartRecView.setAdapter(adapter);
        cartRecView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public void onResume() {
        super.onResume();
        items.clear();
        for (Book book : Utils.getInstance().getBooksInCart()) {
            items.add(new BookAdapterItem.Content(book));
        }
        if (items.isEmpty()) {
            noBooksInCart.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
    }
}