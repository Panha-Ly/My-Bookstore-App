package com.lypanha.mybookstore.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.lypanha.mybookstore.databinding.ItemBookLargeBinding;
import com.lypanha.mybookstore.databinding.ItemBookSeriesBinding;
import com.lypanha.mybookstore.model.Book;
import com.lypanha.mybookstore.model.BookSeries;
import java.util.ArrayList;
import java.util.function.Consumer;

public class BookLargeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_CONTENT = 1;
    private final ArrayList<BookAdapterItem> items;
    private final Consumer<Book> onBookClickListener;

    public BookLargeAdapter(@NonNull ArrayList<BookAdapterItem> items, @NonNull Consumer<Book> onBookClickListener) {
        this.items = items;
        this.onBookClickListener = onBookClickListener;
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        private final ItemBookLargeBinding binding;
        private Book currentBook;
        public ContentViewHolder(
                @NonNull ItemBookLargeBinding binding,
                Consumer<Book> onBookClickListener
        ) {
            super(binding.getRoot());
            this.binding = binding;

            binding.parent.setOnClickListener(view -> {
                if (currentBook != null && onBookClickListener != null) {
                    onBookClickListener.accept(currentBook);
                }
            });
        }
        public void bind(@NonNull Book book){
            this.currentBook = book;

            binding.txtBookName.setText(book.getName());
            binding.txtBookDescription.setText(book.getDescription());
            binding.imgBook.setImageResource(book.getImageId());
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final ItemBookSeriesBinding binding;
        public HeaderViewHolder(@NonNull ItemBookSeriesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(@NonNull BookSeries bookSeries){
            binding.txtBookSeriesName.setText(bookSeries.getName());
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(viewType == TYPE_CONTENT){
            return new ContentViewHolder(
                    ItemBookLargeBinding.inflate(inflater, parent, false),
                    onBookClickListener
            );
        } else {
            return new HeaderViewHolder(ItemBookSeriesBinding.inflate(inflater, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ContentViewHolder){
            ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
            BookAdapterItem.Content content = (BookAdapterItem.Content) items.get(position);
            contentViewHolder.bind(content.getBook());
        } else {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            BookAdapterItem.Header header = (BookAdapterItem.Header) items.get(position);
            headerViewHolder.bind(header.getBookSeries());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        int itemType = TYPE_HEADER;
        if(items.get(position) instanceof BookAdapterItem.Content){
            itemType = TYPE_CONTENT;
        }
        return itemType;
    }
}




