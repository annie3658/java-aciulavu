package com.library.application.service;

import com.library.application.dto.AuthorBooksDTO;
import com.library.application.dto.AuthorDTO;
import com.library.application.dto.BookDTO;
import com.library.application.entity.Author;
import com.library.application.entity.Book;
import com.library.application.exception.AuthorNotFoundException;
import com.library.application.exception.BookNotFoundException;
import com.library.application.repository.AuthorRepository;
import com.library.application.repository.BookRepository;
import com.library.application.utils.DTOUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {

    private BookService service;
    private DTOUtil dtoUtil = new DTOUtil();

    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    public void setService(BookService service) {
        this.service = service;
    }

    @Autowired
    public void setBookRepository(BookRepository repository) {
        this.bookRepository = repository;
    }

    @Autowired
    public void setAuthorRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Test
    public void getAllBooksByAuthorTest() {

        Author author = new Author("1", "Suzanne", "Collins", new GregorianCalendar(1980, 11, 21).getTime(), "bio");
        authorRepository.save(author);

        List<Book> books = new ArrayList<>(
                Arrays.asList(
                        new Book("10", "The Hunger Games", "desc", new GregorianCalendar(2008, 10, 9).getTime(), 4.5d, author),
                        new Book("11", "Catching Fire", "desc", new GregorianCalendar(2009, 5, 25).getTime(), 4.2d, author)));

        books.forEach(book -> bookRepository.save(book));
        when(bookRepository.findByAuthor("Collins")).thenReturn(books);
        when(authorRepository.findAuthorByLastNameAndAndFirstName("Collins", "Suzanne")).thenReturn(author);
        AuthorBooksDTO authorBooksDTO = new AuthorBooksDTO();
        authorBooksDTO.setAuthor(author);
        authorBooksDTO.setBooks(books);
        assertEquals(authorBooksDTO, service.getAllBooksByAuthor("Collins", "Suzanne"));

    }

    @Test(expected = AuthorNotFoundException.class)
    public void getAllBooksByAuthorThrowsExceptionTest() {

        Author author = new Author("1", "Suzanne", "Collins", new GregorianCalendar(1980, 11, 21).getTime(), "bio");
        authorRepository.save(author);

        List<Book> books = new ArrayList<>(
                Arrays.asList(
                        new Book("10", "The Hunger Games", "desc", new GregorianCalendar(2008, 10, 9).getTime(), 4.5d, author),
                        new Book("11", "Catching Fire", "desc", new GregorianCalendar(2009, 5, 25).getTime(), 4.2d, author)));

        books.forEach(book -> bookRepository.save(book));
        when(bookRepository.findByAuthor("Collins")).thenReturn(books);
        when(authorRepository.findAuthorByLastNameAndAndFirstName("Collins", "Suzanne")).thenReturn(author);
        AuthorBooksDTO authorBooksDTO = new AuthorBooksDTO();
        authorBooksDTO.setAuthor(author);
        authorBooksDTO.setBooks(books);
        assertEquals(authorBooksDTO, service.getAllBooksByAuthor("Test", "Test"));

    }

    @Test
    public void findById() {
        AuthorDTO authorDTO = new AuthorDTO.Builder("1")
                .withFirstName("Suzanne")
                .withLastName("Collins")
                .withBio("bio")
                .withDateOfBirth(new GregorianCalendar(1980, 11, 21).getTime())
                .build();
        BookDTO bookDTO = new BookDTO.Builder("1")
                .withTitle("Test")
                .withRating(4.0d)
                .withDescription("desc")
                .withAuthor(authorDTO)
                .withPublishedDate(new GregorianCalendar(2008, 11, 21).getTime())
                .build();

        Author author = new Author("1", "Suzanne", "Collins", new GregorianCalendar(1980, 11, 21).getTime(), "bio");
        Book book = new Book("1", "Test", "desc", new GregorianCalendar(2008, 11, 21).getTime(), 4.0d, author);

        when(bookRepository.findById("1")).thenReturn(Optional.of(book));
        assertEquals(bookDTO, service.findBookById("1"));
    }

    @Test(expected = BookNotFoundException.class)
    public void findByIdThrowsException() {
        AuthorDTO authorDTO = new AuthorDTO.Builder("1")
                .withFirstName("Suzanne")
                .withLastName("Collins")
                .withBio("bio")
                .withDateOfBirth(new GregorianCalendar(1980, 11, 21).getTime())
                .build();
        BookDTO bookDTO = new BookDTO.Builder("1")
                .withTitle("Test")
                .withRating(4.0d)
                .withDescription("desc")
                .withAuthor(authorDTO)
                .withPublishedDate(new GregorianCalendar(2008, 11, 21).getTime())
                .build();

        Author author = new Author("1", "Suzanne", "Collins", new GregorianCalendar(1980, 11, 21).getTime(), "bio");
        Book book = new Book("1", "Test", "desc", new GregorianCalendar(2008, 11, 21).getTime(), 4.0d, author);

        when(bookRepository.findById("1")).thenReturn(Optional.of(book));
        assertEquals(bookDTO, service.findBookById("test"));
    }

    @Test
    public void findByTitle() {
        AuthorDTO authorDTO = new AuthorDTO.Builder("1")
                .withFirstName("Suzanne")
                .withLastName("Collins")
                .withBio("bio")
                .withDateOfBirth(new GregorianCalendar(1980, 11, 21).getTime())
                .build();
        BookDTO bookDTO = new BookDTO.Builder("1")
                .withTitle("Test")
                .withRating(4.0d)
                .withDescription("desc")
                .withAuthor(authorDTO)
                .withPublishedDate(new GregorianCalendar(2008, 11, 21).getTime())
                .build();

        Author author = new Author("1", "Suzanne", "Collins", new GregorianCalendar(1980, 11, 21).getTime(), "bio");
        Book book = new Book("1", "Test", "desc", new GregorianCalendar(2008, 11, 21).getTime(), 4.0d, author);

        when(bookRepository.findBookByTitle("Test")).thenReturn((book));
        assertEquals(bookDTO, service.findByTitle("Test"));
    }

    @Test(expected = BookNotFoundException.class)
    public void findByTitleThrowsException() {
        AuthorDTO authorDTO = new AuthorDTO.Builder("1")
                .withFirstName("Suzanne")
                .withLastName("Collins")
                .withBio("bio")
                .withDateOfBirth(new GregorianCalendar(1980, 11, 21).getTime())
                .build();
        BookDTO bookDTO = new BookDTO.Builder("1")
                .withTitle("Test")
                .withRating(4.0d)
                .withDescription("desc")
                .withAuthor(authorDTO)
                .withPublishedDate(new GregorianCalendar(2008, 11, 21).getTime())
                .build();

        Author author = new Author("1", "Suzanne", "Collins", new GregorianCalendar(1980, 11, 21).getTime(), "bio");
        Book book = new Book("1", "Test", "desc", new GregorianCalendar(2008, 11, 21).getTime(), 4.0d, author);

        when(bookRepository.findBookByTitle("Test")).thenReturn((book));
        assertEquals(bookDTO, service.findByTitle("bookNotFound"));
    }

    @Test
    public void getAllTest() {
        List<Book> books = new ArrayList<>(
                Arrays.asList(
                        new Book("1", "Test", "desc", new GregorianCalendar(2008, 11, 21).getTime(), 4.0d, new Author("1", "Suzanne", "Collins", new GregorianCalendar(1980, 11, 21).getTime(), "bio")),
                        new Book("2", "Test2", "desc", new GregorianCalendar(2008, 11, 21).getTime(), 4.0d, new Author("2", "Ana", "Collins", new GregorianCalendar(1980, 11, 21).getTime(), "bio")),
                        new Book("3", "Test3", "desc", new GregorianCalendar(2008, 11, 21).getTime(), 4.0d, new Author("3", "Maria", "Collins", new GregorianCalendar(1980, 11, 21).getTime(), "bio")))

        );
        when(bookRepository.findAll()).thenReturn(books);
        List<BookDTO> bookDTOS = books.stream()
                .map(book -> dtoUtil.bookToDTO(book))
                .collect(Collectors.toList());
        assertEquals(bookDTOS, service.getAll());
    }

    @Test
    public void insertTest() {
        Book book = new Book("1", "Test", "desc", new GregorianCalendar(2008, 11, 21).getTime(), 4.0d, new Author("1", "Suzanne", "Collins", new GregorianCalendar(1980, 11, 21).getTime(), "bio"));
        when(bookRepository.insert(book)).thenReturn(book);
        when(authorRepository.insert(book.getAuthor())).thenReturn(new Author());
        assertEquals(dtoUtil.bookToDTO(book), service.insert(dtoUtil.bookToDTO(book)));

    }

    @Test
    public void updateTest() {
        Book book = new Book("1", "Test", "desc", new GregorianCalendar(2008, 11, 21).getTime(), 4.0d, new Author("1", "Suzanne", "Collins", new GregorianCalendar(1980, 11, 21).getTime(), "bio"));
        when(bookRepository.save(book)).thenReturn(book);
        assertEquals(dtoUtil.bookToDTO(book), service.update(dtoUtil.bookToDTO(book)));

    }

    @Test
    public void deleteTest(){
        service.delete("1");
        verify(bookRepository, times(1)).deleteById("1");
    }
}
