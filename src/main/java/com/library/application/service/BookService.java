package com.library.application.service;
import ch.qos.logback.classic.Logger;
import com.library.application.dto.AuthorBooksDTO;
import com.library.application.dto.AuthorDTO;
import com.library.application.dto.BookDTO;
import com.library.application.entity.Book;
import com.library.application.exception.BookNotFoundException;
import com.library.application.repository.BookRepository;
import com.library.application.utils.DTOUtil;
import lombok.NonNull;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(BookService.class);

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
            LOGGER.error("The Book with id {} was not found", id);
            throw new BookNotFoundException(id);
        }
    }

    public BookDTO findBookById(String id){
        return dtoUtil.bookToDTO(findById(id));
    }

    public BookDTO findByTitle(String title) {
        Book book = bookRepository.findBookByTitle(title);
        if(book == null){
            LOGGER.error("The Book with the title {} was not found", title);
            throw new BookNotFoundException(title);
        }
        return dtoUtil.bookToDTO(book);
    }

    public List<BookDTO> getAll() {
        List<Book> books = bookRepository.findAll();
        if(books.isEmpty()){
            LOGGER.warn("The books list is empty");
        }
        return books.stream()
                .map(book -> dtoUtil.bookToDTO(book))
                .collect(Collectors.toList());
    }

    public BookDTO insert(BookDTO book) {
        authorService.insert(book.getAuthor());
        LOGGER.info("Created new book: " + book.toString());
        return dtoUtil.bookToDTO(bookRepository.insert(dtoUtil.dtoToBook(book)));
    }

    public BookDTO update(BookDTO book){
        LOGGER.info("Updated book: " + book.toString());
       return dtoUtil.bookToDTO(bookRepository.save(dtoUtil.dtoToBook(book)));
    }

    public void delete(String id) {
        LOGGER.info("Deleting book with id: " + id);
        bookRepository.deleteById(id);
    }

    public AuthorBooksDTO getAllBooksByAuthor(String lastName, String firstName) {
        AuthorDTO author = authorService.findAuthorByFullName(lastName, firstName);
        List<Book> books = bookRepository.findByAuthor(lastName);

        if(books.isEmpty()){
            LOGGER.warn("getAllBooksByAuthor : the books list is empty");
        }

        AuthorBooksDTO dto = new AuthorBooksDTO();
        dto.setAuthor(dtoUtil.dtoToAuthor(author));
        dto.setBooks(books);

        return dto;
    }


}
