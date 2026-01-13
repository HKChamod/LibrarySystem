package com.library.system.controller;

import com.library.system.model.Borrow;
import com.library.system.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/borrows")
public class BorrowController {
    @Autowired
    BorrowService borrowService;

    @PostMapping("/issue")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<?> issueBook(@RequestParam Long bookId, @RequestParam Long userId) {
        return ResponseEntity.ok(borrowService.issueBook(bookId, userId));
    }

    @PostMapping("/return/{borrowId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<?> returnBook(@PathVariable Long borrowId) {
        return ResponseEntity.ok(borrowService.returnBook(borrowId));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN') or #userId == authentication.principal.id") 
    // Note: #userId == authentication.principal.id requires complex SpEL or custom SecurityExpression.
    // Simplifying to allow Admin/Librarian or User effectively via service check or just simplistic role check for now.
    // Best practice is to check current user ID match in code if strict privacy needed.
    public ResponseEntity<List<Borrow>> getUserHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(borrowService.getUserHistory(userId));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public List<Borrow> getAllBorrows() {
        return borrowService.getAllBorrows();
    }
}
