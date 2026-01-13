package com.library.system.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fines")
@Data
@NoArgsConstructor
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrow_id", nullable = false)
    private Borrow borrow;

    private Double amount;

    private boolean isPaid = false;
}
