package com.library.application.dto;

import com.library.application.entity.Author;
import com.library.application.entity.Book;
import lombok.Data;

import java.util.List;

@Data
public class AuthorBooksDTO {
    private Author author;
    private List<Book> books;
}
