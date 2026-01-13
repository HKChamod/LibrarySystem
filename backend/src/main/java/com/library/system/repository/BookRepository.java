package com.library.system.repository;

import com.library.system.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingOrAuthorContaining(String title, String author);
    
    @Query("SELECT b FROM Book b WHERE " +
           "(:query IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(b.author) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
           "(:category IS NULL OR b.category.name = :category)")
    List<Book> searchBooks(@Param("query") String query, @Param("category") String category);

    @Query("SELECT b FROM Borrow br JOIN br.book b GROUP BY b ORDER BY COUNT(br) DESC")
    List<Book> findTopBorrowedBooks(org.springframework.data.domain.Pageable pageable);
}
