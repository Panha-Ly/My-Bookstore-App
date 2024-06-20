package com.lypanha.mybookstore.adapter;

import static com.lypanha.mybookstore.model.Item.BOOK;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.lypanha.mybookstore.BookActivity;
import com.lypanha.mybookstore.R;
import com.lypanha.mybookstore.model.Book;
import com.lypanha.mybookstore.model.BookSeries;
import com.lypanha.mybookstore.model.Item;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String HOME = "Home";
    public static final String ALL_BOOKS = "All Books";
    public static final String BOOK_SERIES = "Book Series";
    public static final String CART = "Cart";

    public static final int HORIZONTAL_VIEW = 0;
    public static final int VERTICAL_VIEW = 1;

    private final ArrayList<Item> items;
    private final Context context;
    private final int orientation;
    private final String fragmentName;

    public BookAdapter(ArrayList<Item> items, Context context, int orientation, String fragmentName) {
        this.items = items;
        this.context = context;
        this.orientation = orientation;
        this.fragmentName = fragmentName;
    }

    public static class HorizontalBookViewHolder extends RecyclerView.ViewHolder{
        private final ConstraintLayout parent;
        private final ImageView bookImage;
        private final TextView bookName;

        public HorizontalBookViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            bookImage = itemView.findViewById(R.id.imgBook);
            bookName = itemView.findViewById(R.id.txtBookName);
        }
    }

    public static class VerticalBookViewHolder extends RecyclerView.ViewHolder{
        private final ConstraintLayout parent;
        private final ImageView bookImage;
        private final TextView bookName, bookDescription;
        public VerticalBookViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            bookImage = itemView.findViewById(R.id.imgBook);
            bookName = itemView.findViewById(R.id.txtBookName);
            bookDescription = itemView.findViewById(R.id.txtBookDescription);
        }
    }

    public static class BookSeriesViewHolder extends RecyclerView.ViewHolder{
        private final TextView bookSeriesName;

        public BookSeriesViewHolder(@NonNull View itemView) {
            super(itemView);
            bookSeriesName = itemView.findViewById(R.id.txtBookSeriesName);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        // type1 for horizontal view, type2 for vertical view and type3 for vertical view with dynamic view type

        if(orientation == HORIZONTAL_VIEW){
            return new HorizontalBookViewHolder(inflater.inflate(R.layout.item_book_row_type1, parent, false));
        } else {
            if(viewType == BOOK){
                return new VerticalBookViewHolder(inflater.inflate(R.layout.item_book_row_type2, parent, false));
            } else {
                return new BookSeriesViewHolder(inflater.inflate(R.layout.item_book_row_type3, parent, false));
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(orientation == HORIZONTAL_VIEW){
            HorizontalBookViewHolder bookViewHolder = (HorizontalBookViewHolder) holder;
            Book book = (Book) items.get(position).getObject();

            bookViewHolder.bookImage.setImageResource(book.getImageId());
            bookViewHolder.bookName.setText(book.getName());
            bookViewHolder.parent.setOnClickListener(v -> {

                Intent intent = new Intent(context, BookActivity.class);
                intent.putExtra("bookId", book.getId());
                intent.putExtra("fragmentName", fragmentName);
                context.startActivity(intent);
            });


        } else if (orientation == VERTICAL_VIEW) {
            if(getItemViewType(position) == BOOK){
                VerticalBookViewHolder bookViewHolder = (VerticalBookViewHolder) holder;
                Book book = (Book) items.get(position).getObject();

                bookViewHolder.bookImage.setImageResource(book.getImageId());
                bookViewHolder.bookName.setText(book.getName());
                bookViewHolder.bookDescription.setText(book.getDescription());
                bookViewHolder.parent.setOnClickListener(v -> {

                    Intent intent = new Intent(context, BookActivity.class);
                    intent.putExtra("bookId", book.getId());
                    intent.putExtra("fragmentName", fragmentName);
                    context.startActivity(intent);
                });
            } else {
                BookSeriesViewHolder bookViewHolder = (BookSeriesViewHolder) holder;
                BookSeries bookSeries = (BookSeries) items.get(position).getObject();

                bookViewHolder.bookSeriesName.setText(bookSeries.getName());
                bookViewHolder.bookSeriesName.setText(bookSeries.getName());

            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        Item item = items.get(position);
        if(item.getItemTypeId() == BOOK){
            return 0;
        } else {
            return 1;
        }
    }
}
