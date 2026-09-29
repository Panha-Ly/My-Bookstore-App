package com.lypanha.mybookstore

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.lypanha.mybookstore.Utils.addToCart
import com.lypanha.mybookstore.Utils.removeFromCart
import com.lypanha.mybookstore.databinding.ActivityBookBinding

class BookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // Toolbar
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Status bar and Navigation bar
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = false // false = white icons
            isAppearanceLightNavigationBars = false
            Log.d("", "Hi " + Build.VERSION.SDK_INT)
        }

        // Book details
        val bookId = intent.getIntExtra("bookId", -1)
        Utils.getBookById(bookId)?.let { book ->
            binding.imgBook.setImageResource(book.imageId)
            binding.txtBookName.text = book.name
            binding.txtAuthor.text = book.author
            binding.txtPrice.text = book.price
            binding.txtBookDescription.text = book.description
            binding.txtPublicationDate.text = book.publicationDate
        }

        // Cart button
        intent.getStringExtra("fragmentName")?.let { name ->
            val isCart = name == "Cart"
            binding.btnCart.apply {
                // 1. Set text dynamically
                text = getText(if (isCart) R.string.remove_from_cart else R.string.add_to_cart)
                // 2. Set the click listener cleanly
                setOnClickListener {
                    val success = if (isCart) removeFromCart(bookId) else addToCart(bookId)
                    val toastTxt = when {
                        isCart && success -> "Removed from Cart"
                        isCart && !success -> "Already removed from Cart"
                        !isCart && success -> "Added to Cart"
                        else -> "Already added to Cart"
                    }
                    Toast.makeText(this@BookActivity, toastTxt, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}