package com.library.system.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "borrows")
@Data
@NoArgsConstructor
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate borrowDate;

    @Column(nullable = false)
    private LocalDate dueDate;

    private LocalDate returnDate;
    
    @OneToOne(mappedBy = "borrow", cascade = CascadeType.ALL)
    private Fine fine;
}
