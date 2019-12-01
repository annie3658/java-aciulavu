package com.library.application.service;

import ch.qos.logback.classic.Logger;
import com.library.application.config.CoversConfig;
import com.library.application.dto.AuthorBooksDTO;
import com.library.application.dto.AuthorDTO;
import com.library.application.dto.BookDTO;
import com.library.application.dto.CoverDTO;
import com.library.application.entity.Book;
import com.library.application.exception.BookNotFoundException;
import com.library.application.repository.BookRepository;
import com.library.application.utils.DTOUtil;
import lombok.NonNull;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(BookService.class);

    private final @NonNull BookRepository bookRepository;
    private final @NonNull AuthorService authorService;
    private final @NonNull CoverService coverService;
    private WebClient.Builder webClientBuilder;
    private CoversConfig config;

    @Autowired
    public BookService(@NonNull BookRepository bookRepository, @NonNull AuthorService authorService, @NonNull CoverService coverService, CoversConfig config, WebClient.Builder webClientBuilder) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.coverService = coverService;
        this.config = config;
        this.webClientBuilder = webClientBuilder;
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
        BookDTO book = dtoUtil.bookToDTO(findById(id));
        CoverDTO cover = webClientBuilder.build()
                .get()
                .uri(config.getCover() + book.getTitle())
                .retrieve()
                .bodyToMono(CoverDTO.class)
                .switchIfEmpty(Mono.empty())
                .block();
        book.setCover(cover);
        return book;
    }

    public BookDTO findByTitle(String title) {
        Book book = bookRepository.findBookByTitle(title);
        if(book == null){
            LOGGER.error("The Book with the title {} was not found", title);
            throw new BookNotFoundException(title);
        }
        CoverDTO cover = webClientBuilder.build()
                .get()
                .uri(config.getCover() + title)
                .retrieve()
                .bodyToMono(CoverDTO.class)
                .switchIfEmpty(Mono.empty())
                .block();
        BookDTO bookDTO = dtoUtil.bookToDTO(book);
        bookDTO.setCover(cover);
        return bookDTO;
    }

    public List<BookDTO> getAll() {
        List<Book> books = bookRepository.findAll();
        if(books.isEmpty()){
            LOGGER.warn("The books list is empty");
        }

        return books.stream()
                .map(book -> {
                    BookDTO bookDTO = dtoUtil.bookToDTO(book);
                    CoverDTO cover = webClientBuilder.build()
                            .get()
                            .uri(config.getCover() + book.getTitle())
                            .retrieve()
                            .bodyToMono(CoverDTO.class)
                            .switchIfEmpty(Mono.empty())
                            .block();
                    if(cover != null) bookDTO.setCover(cover);
                    return bookDTO;
                })
                .collect(Collectors.toList());
    }

    public BookDTO insert(BookDTO book) {
        authorService.insert(book.getAuthor());
        book.getCover().setBookTitle(book.getTitle());
        coverService.insert(book.getCover());
        LOGGER.info("Created new book: " + book.toString());
        return dtoUtil.bookToDTO(bookRepository.insert(dtoUtil.dtoToBook(book)));
    }

    public BookDTO update(BookDTO book){
        LOGGER.info("Updated book: " + book.toString());
        coverService.update(book.getCover());
       return dtoUtil.bookToDTO(bookRepository.save(dtoUtil.dtoToBook(book)));
    }

    public void delete(String id) {
        LOGGER.info("Deleting book with id: " + id);
        BookDTO book = findBookById(id);
        coverService.delete(book.getCover().getId());
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
