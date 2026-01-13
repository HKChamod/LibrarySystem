package com.library.system.controller;

import com.library.system.dto.request.BookRequest;
import com.library.system.model.Book;
import com.library.system.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping
    public List<Book> getAllBooks(@RequestParam(required = false) String query,
                                  @RequestParam(required = false) String category) {
        if (query != null || category != null) {
            return bookService.searchBooks(query, category);
        }
        return bookService.getAllBooks();
    }

    @GetMapping("/top")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Book> getTopBooks() {
        return bookService.getTopBorrowedBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<Book> createBook(@RequestBody BookRequest bookRequest) {
        Book book = new Book();
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setIsbn(bookRequest.getIsbn());
        return ResponseEntity.ok(bookService.addBook(book, bookRequest.getCategoryId()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody BookRequest bookRequest) {
        Book bookDetails = new Book();
        bookDetails.setTitle(bookRequest.getTitle());
        bookDetails.setAuthor(bookRequest.getAuthor());
        bookDetails.setIsbn(bookRequest.getIsbn());
        return ResponseEntity.ok(bookService.updateBook(id, bookDetails));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully");
    }
}
