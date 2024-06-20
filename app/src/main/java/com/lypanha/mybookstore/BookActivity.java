package com.lypanha.mybookstore;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.lypanha.mybookstore.adapter.BookAdapter;
import com.lypanha.mybookstore.model.Book;

public class BookActivity extends AppCompatActivity {
    private ImageView imgBook;
    private TextView txtName, txtAuthor, txtPrice, txtDescription, txtPublicationDate;
    private Button btnCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });



        // Full screen on all API
        if (Build.VERSION.SDK_INT <= 29) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        } else {
            Window window = getWindow();
            window.setStatusBarColor(Color.TRANSPARENT);
            // Making status bar overlaps with the activity
            WindowCompat.setDecorFitsSystemWindows(window, false);

        }

        imgBook = findViewById(R.id.imgBook);
        txtName = findViewById(R.id.txtBookName);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtPrice = findViewById(R.id.txtPrice);
        txtDescription = findViewById(R.id.txtBookDescription);
        txtPublicationDate = findViewById(R.id.txtPublicationDate);
        btnCart = findViewById(R.id.btnCart);

        Intent intent = getIntent();
        if (intent != null) {
            String fragmentName = intent.getStringExtra("fragmentName");
            int bookId = intent.getIntExtra("bookId", -1);

            if (bookId != -1) {
                Book currentBook = Utils.getInstance().getBookById(bookId);
                if (currentBook != null) {
                    imgBook.setImageResource(currentBook.getImageId());
                    txtName.setText(currentBook.getName());
                    txtAuthor.setText(currentBook.getAuthor());
                    txtPrice.setText(currentBook.getPrice());
                    txtDescription.setText(currentBook.getDescription());
                    txtPublicationDate.setText(currentBook.getPublicationDate());
                }
                if (fragmentName != null) {
                    if (fragmentName.equals(BookAdapter.CART)) {
                        btnCart.setText(getText(R.string.remove_from_cart));
                        btnCart.setOnClickListener(v -> {
                            if(Utils.getInstance().removeFromCart(bookId)){
                                Toast.makeText(this, "Removed from Cart", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(this, "Already removed from Cart", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        btnCart.setText(getText(R.string.add_to_cart));
                        btnCart.setOnClickListener(v -> {
                            if(Utils.getInstance().addToCart(bookId)){
                                Toast.makeText(this, "Added to Cart", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Already added to Cart", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }


        }

        // Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
            getSupportActionBar().show();
        }
        // Back Button Implementation
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}