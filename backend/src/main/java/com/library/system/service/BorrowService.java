package com.library.system.service;

import com.library.system.model.Book;
import com.library.system.model.Borrow;
import com.library.system.model.Fine;
import com.library.system.model.User;
import com.library.system.repository.BookRepository;
import com.library.system.repository.BorrowRepository;
import com.library.system.repository.FineRepository;
import com.library.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BorrowService {
    @Autowired
    BorrowRepository borrowRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    FineRepository fineRepository;

    @Transactional
    public Borrow issueBook(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        
        if (!book.isAvailable()) {
            throw new RuntimeException("Book is not available");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Borrow borrow = new Borrow();
        borrow.setBook(book);
        borrow.setUser(user);
        borrow.setBorrowDate(LocalDate.now());
        borrow.setDueDate(LocalDate.now().plusWeeks(2)); // 2 weeks borrow period

        book.setAvailable(false);
        bookRepository.save(book);
        
        return borrowRepository.save(borrow);
    }

    @Transactional
    public Borrow returnBook(Long borrowId) {
        Borrow borrow = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));
        
        if (borrow.getReturnDate() != null) {
            throw new RuntimeException("Book already returned");
        }

        borrow.setReturnDate(LocalDate.now());
        
        Book book = borrow.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        // Calculate fine
        long daysLate = ChronoUnit.DAYS.between(borrow.getDueDate(), LocalDate.now());
        if (daysLate > 0) {
            Fine fine = new Fine();
            fine.setBorrow(borrow);
            fine.setAmount(daysLate * 5.0); // $5 per day
            fineRepository.save(fine);
            borrow.setFine(fine);
        }

        return borrowRepository.save(borrow);
    }

    public List<Borrow> getUserHistory(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return borrowRepository.findByUser(user);
    }

    public List<Borrow> getAllBorrows() {
        return borrowRepository.findAll();
    }
}
