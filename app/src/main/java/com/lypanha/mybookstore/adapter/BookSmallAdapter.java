package com.lypanha.mybookstore.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lypanha.mybookstore.databinding.ItemBookSmallBinding;
import com.lypanha.mybookstore.model.Book;
import java.util.ArrayList;
import java.util.function.Consumer;

public class BookSmallAdapter extends RecyclerView.Adapter<BookSmallAdapter.BookSmallViewHolder> {

    private final ArrayList<Book> items;
    private final Consumer<Book> onBookClickListener;

    public BookSmallAdapter(@NonNull ArrayList<Book> items, @NonNull Consumer<Book> onBookClickListener) {
        this.items = items;
        this.onBookClickListener = onBookClickListener;
    }

    public static class BookSmallViewHolder extends RecyclerView.ViewHolder {

        private final ItemBookSmallBinding binding;
        private Book currentBook; // 1. Keep a reference to the bound book

        public BookSmallViewHolder(
                @NonNull ItemBookSmallBinding binding,
                Consumer<Book> onBookClickListener
        ) {
            super(binding.getRoot());
            this.binding = binding;

            // 2. Set the listener ONCE here in the constructor
            binding.parent.setOnClickListener(view -> {
                if (currentBook != null && onBookClickListener != null) {
                    onBookClickListener.accept(currentBook); // Trigger the Fragment's logic
                }
            });
        }

        public void bind(@NonNull Book book){
            this.currentBook = book; // 3. Update the book reference on bind

            binding.txtBookName.setText(book.getName());
            binding.imgBook.setImageResource(book.getImageId());
        }
    }

    @NonNull
    @Override
    public BookSmallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new BookSmallViewHolder(
                ItemBookSmallBinding.inflate(inflater, parent, false),
                onBookClickListener
        );
    }

    @Override
    public void onBindViewHolder(@NonNull BookSmallViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
