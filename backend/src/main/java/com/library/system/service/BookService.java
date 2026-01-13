package com.library.system.service;

import com.library.system.model.Book;
import com.library.system.model.Category;
import com.library.system.repository.BookRepository;
import com.library.system.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> searchBooks(String query, String category) {
        return bookRepository.searchBooks(query, category);
    }

    public Book addBook(Book book, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        book.setCategory(category);
        return bookRepository.save(book);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Book updateBook(Long id, Book bookDetails) {
        Book book = getBookById(id);
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setIsbn(bookDetails.getIsbn());
        return bookRepository.save(book);
    }

    public List<Book> getTopBorrowedBooks() {
        return bookRepository.findTopBorrowedBooks(org.springframework.data.domain.PageRequest.of(0, 10));
    }
}
