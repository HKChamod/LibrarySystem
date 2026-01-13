package com.library.system.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotBlank
    private String isbn;
    
    private Long categoryId;
}
