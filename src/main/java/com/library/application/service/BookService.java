package com.library.application.service;

import com.library.application.dto.AuthorBooksDTO;
import com.library.application.dto.AuthorDTO;
import com.library.application.dto.BookDTO;
import com.library.application.entity.Book;
import com.library.application.repository.BookRepository;
import com.library.application.utils.DTOUtil;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {


    private final @NonNull BookRepository bookRepository;
    private final @NonNull AuthorService authorService;

    @Autowired
    public BookService(@NonNull BookRepository bookRepository, @NonNull AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    private DTOUtil dtoUtil = new DTOUtil();


    private Book findById(String id) {

        Optional<Book> foundBook = bookRepository.findById(id);
        if (foundBook.isPresent()) {
            return foundBook.get();
        } else {
            Book book = new Book();
            book.setIsbn(StringUtils.EMPTY);
            book.setDescription(StringUtils.EMPTY);
            book.setPublishedDate(null);
            book.setRating(NumberUtils.DOUBLE_ZERO);
            book.setAuthor(null);
            return book;
        }
    }

    public BookDTO findBookById(String id){
        return dtoUtil.bookToDTO(findById(id));
    }

    public BookDTO findByTitle(String title) {

        return dtoUtil.bookToDTO(bookRepository.findBookByTitle(title));
    }

    public List<BookDTO> getAll() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(book -> dtoUtil.bookToDTO(book))
                .collect(Collectors.toList());
    }

    public BookDTO insert(Book book) {
        authorService.insert(book.getAuthor());
        return dtoUtil.bookToDTO(bookRepository.insert(book));
    }

    public BookDTO update(Book book){
       return dtoUtil.bookToDTO(bookRepository.save(book));
    }

    public void delete(String id) {
        bookRepository.deleteById(id);
    }

    public AuthorBooksDTO getAllBooksByAuthor(String lastName, String firstName) {
        AuthorDTO author = authorService.findAuthorByFullName(lastName, firstName);
        List<Book> books = bookRepository.findByAuthor(lastName);

        AuthorBooksDTO dto = new AuthorBooksDTO();
        dto.setAuthor(dtoUtil.dtoToAuthor(author));
        dto.setBooks(books);

        return dto;
    }


}
