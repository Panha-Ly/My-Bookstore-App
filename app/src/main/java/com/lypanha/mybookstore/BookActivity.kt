package com.lypanha.mybookstore

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import com.google.android.material.snackbar.Snackbar
import com.lypanha.mybookstore.Utils.addToCart
import com.lypanha.mybookstore.Utils.removeFromCart
import com.lypanha.mybookstore.adapter.BookAdapter

class BookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_book)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
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

        val imgBook = findViewById<ImageView>(R.id.imgBook)
        val txtName = findViewById<TextView>(R.id.txtBookName)
        val txtAuthor = findViewById<TextView>(R.id.txtAuthor)
        val txtPrice = findViewById<TextView>(R.id.txtPrice)
        val txtDescription = findViewById<TextView>(R.id.txtBookDescription)
        val txtPublicationDate = findViewById<TextView>(R.id.txtPublicationDate)
        val btnCart = findViewById<Button>(R.id.btnCart)

        // Book details
        val bookId = intent.getIntExtra("bookId", -1)
        Utils.getBookById(bookId)?.let { book ->
            imgBook.setImageResource(book.imageId)
            txtName.text = book.name
            txtAuthor.text = book.author
            txtPrice.text = book.price
            txtDescription.text = book.description
            txtPublicationDate.text = book.publicationDate
        }

        // Cart button
        intent.getStringExtra("fragmentName")?.let { name ->
            btnCart.text = resources.getText(
                if (name == BookAdapter.CART) R.string.remove_from_cart else R.string.add_to_cart
            )
            btnCart.setOnClickListener {
                val toastTxt = if (name == BookAdapter.CART) {
                    if (removeFromCart(bookId)) "Removed from Cart" else "Already removed from Cart"
                } else {
                    if (addToCart(bookId)) "Added to Cart" else "Already added to Cart"
                }
//                val snackBar = Snackbar.make(findViewById<ConstraintLayout>(R.id.main), toastTxt, Snackbar.LENGTH_SHORT)
//                snackBar.view.updateLayoutParams<ViewGroup.MarginLayoutParams>{
//                    bottomMargin += (48 * resources.displayMetrics.density).toInt()
//                }
//                snackBar.show()
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