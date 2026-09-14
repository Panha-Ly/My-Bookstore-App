package com.lypanha.mybookstore.adapter;

import com.lypanha.mybookstore.model.Book;
import com.lypanha.mybookstore.model.BookSeries;

/**
 *  Interface for {@link BookLargeAdapter}'s item array.
 *  @see Header
 *  @see Content
 */
public interface BookAdapterItem {

    class Content implements BookAdapterItem {
        private final Book book;

        public Content(Book book) {
            this.book = book;
        }

        public Book getBook() {
            return book;
        }
    }

    class Header implements BookAdapterItem {
        private final BookSeries bookSeries;

        public Header(BookSeries bookSeries) {
            this.bookSeries = bookSeries;
        }

        public BookSeries getBookSeries() {
            return bookSeries;
        }
    }

}
