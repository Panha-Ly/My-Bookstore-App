package com.lypanha.mybookstore.model

data class Item(
    val itemTypeId: Int,
    val item: Any
) {
    companion object {
        const val BOOK: Int = 0
        const val BOOK_SERIES: Int = 1
    }
}
