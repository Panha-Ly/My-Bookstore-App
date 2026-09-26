package com.lypanha.mybookstore

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.ViewCompat
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
        ViewCompat.setOnApplyWindowInsetsListener(
            binding.main
        ) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

        // Full screen on all API
        if (Build.VERSION.SDK_INT <= 29) {
            window.statusBarColor = Color.TRANSPARENT
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        } else {
            window.statusBarColor = Color.TRANSPARENT
//            WindowCompat
//                .getInsetsController(window, window.decorView)
//                .isAppearanceLightStatusBars = false // false = white icons

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
            binding.btnCart.text = resources.getText(
                if (name == "Cart") R.string.remove_from_cart else R.string.add_to_cart
            )
            binding.btnCart.setOnClickListener {
                val toastTxt = if (name == "Cart") {
                    if (removeFromCart(bookId)) "Removed from Cart" else "Already removed from Cart"
                } else {
                    if (addToCart(bookId)) "Added to Cart" else "Already added to Cart"
                }
                Toast.makeText(this, toastTxt, Toast.LENGTH_SHORT).show()
            }
        }

        // Action Bar
        supportActionBar?.let { actionBar ->
            actionBar.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            actionBar.displayOptions = ActionBar.DISPLAY_HOME_AS_UP
            actionBar.show()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        if(item.itemId == android.R.id.home){
            finish()
            true
        } else super.onOptionsItemSelected(item)

}