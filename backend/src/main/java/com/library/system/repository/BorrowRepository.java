package com.library.system.repository;

import com.library.system.model.Borrow;
import com.library.system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    List<Borrow> findByUser(User user);
    List<Borrow> findByReturnDateIsNull();
    Optional<Borrow> findByBookIdAndReturnDateIsNull(Long bookId);
}
