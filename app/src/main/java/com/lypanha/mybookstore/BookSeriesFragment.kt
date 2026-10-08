package com.lypanha.mybookstore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.lypanha.mybookstore.ui.features.bookseries.BookSeriesScreen
import com.lypanha.mybookstore.ui.theme.MyBookstoreTheme

class BookSeriesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MyBookstoreTheme {
                    Surface {
                        BookSeriesScreen()
                    }
                }
            }
        }
    }

}