package com.lypanha.mybookstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.lypanha.mybookstore.databinding.ActivityBookBinding;
import com.lypanha.mybookstore.model.Book;

public class BookActivity extends AppCompatActivity {

    private ActivityBookBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        // Action Bar
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
            getSupportActionBar().show();
        }
        // Status bar and Navigation bar
        Window window = getWindow();
        WindowCompat.getInsetsController(window, window.getDecorView()).setAppearanceLightStatusBars(false);
        WindowCompat.getInsetsController(window, window.getDecorView()).setAppearanceLightNavigationBars(false);

        Intent intent = getIntent(); // Never null here
        String fragmentName = intent.getStringExtra("fragmentName");
        int bookId = intent.getIntExtra("bookId", -1);
        Book currentBook = Utils.getInstance().getBookById(bookId);

        // Book data
        if (currentBook != null) {
            binding.imgBook.setImageResource(currentBook.getImageId());
            binding.txtBookName.setText(currentBook.getName());
            binding.txtAuthor.setText(currentBook.getAuthor());
            binding.txtPrice.setText(currentBook.getPrice());
            binding.txtBookDescription.setText(currentBook.getDescription());
            binding.txtPublicationDate.setText(currentBook.getPublicationDate());
        }

        // Cart Button
        if ("Cart".equals(fragmentName)) {
            binding.btnCart.setText(R.string.remove_from_cart);
            binding.btnCart.setOnClickListener(v -> {
                String toastText = Utils.getInstance().removeFromCart(bookId) ? "Removed from Cart" : "Already removed from Cart";
                Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
            });
        } else {
            // This handles both regular "Add to Cart" fragments AND cases where fragmentName is null
            binding.btnCart.setText(R.string.add_to_cart);
            binding.btnCart.setOnClickListener(v -> {
                String toastText = Utils.getInstance().addToCart(bookId) ? "Added to Cart" : "Already added to Cart";
                Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
            });
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}