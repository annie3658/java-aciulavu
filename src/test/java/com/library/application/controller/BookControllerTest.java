package com.library.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.application.dto.AuthorBooksDTO;
import com.library.application.dto.AuthorDTO;
import com.library.application.dto.BookDTO;
import com.library.application.entity.Author;
import com.library.application.entity.Book;
import com.library.application.service.BookService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BookControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    BookController bookController;

    @Mock
    BookService bookService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .build();
    }

    @Test
    public void getAllBooks()throws Exception{
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


        AuthorDTO authorTwoDTO = new AuthorDTO.Builder("2")
                .withFirstName("Test2")
                .withLastName("Collins")
                .withBio("bio")
                .withDateOfBirth(new GregorianCalendar(1980, 11, 21).getTime())
                .build();
        BookDTO bookTwoDTO = new BookDTO.Builder("2")
                .withTitle("Test2")
                .withRating(4.0d)
                .withDescription("desc")
                .withAuthor(authorTwoDTO)
                .withPublishedDate(new GregorianCalendar(2008, 11, 21).getTime())
                .build();
        List<BookDTO> booksDTO = new ArrayList<>();
        booksDTO.add(bookDTO);
        booksDTO.add(bookTwoDTO);

        when(bookService.getAll()).thenReturn(booksDTO);
        mockMvc.perform(MockMvcRequestBuilders.get("/books/all")).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void getBookByIdTest() throws Exception{
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

        when(bookService.findBookById("1")).thenReturn(bookDTO);
        mockMvc.perform(MockMvcRequestBuilders.get("/books/bookById/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn", Matchers.is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("desc")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rating", Matchers.is(4.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.id", Matchers.is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.firstName", Matchers.is("Suzanne")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.lastName", Matchers.is("Collins")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.dateOfBirth", Matchers.is("20-12-1980")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.bio", Matchers.is("bio")));
        verify(bookService).findBookById("1");
        verify(bookService, times(1)).findBookById("1");

    }

    @Test
    public void getBookByTitleTest() throws Exception{
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

        when(bookService.findByTitle("Test")).thenReturn(bookDTO);
        mockMvc.perform(MockMvcRequestBuilders.get("/books/book/Test")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn", Matchers.is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("desc")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rating", Matchers.is(4.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.id", Matchers.is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.firstName", Matchers.is("Suzanne")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.lastName", Matchers.is("Collins")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.dateOfBirth", Matchers.is("20-12-1980")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.bio", Matchers.is("bio")));
        verify(bookService).findByTitle("Test");
        verify(bookService, times(1)).findByTitle("Test");

    }

    @Test
    public void insertTest() throws Exception {
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

        when(bookService.insert(any(BookDTO.class))).thenReturn(bookDTO);
        String jsonRequest = objectMapper.writeValueAsString(bookDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/books")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.rating").exists());

        ArgumentCaptor<BookDTO> dtoCaptor = ArgumentCaptor.forClass(BookDTO.class);
        verify(bookService, times(1)).insert(dtoCaptor.capture());
        verifyNoMoreInteractions(bookService);

        BookDTO dtoArgument = dtoCaptor.getValue();
        assertThat(dtoArgument.getTitle(), is("Test"));
    }

    @Test
    public void insertBookWithoutTitleReturnValidationErrorTest() throws Exception {
        AuthorDTO authorDTO = new AuthorDTO.Builder("1")
                .withFirstName("Suzanne")
                .withLastName("Collins")
                .withBio("bio")
                .withDateOfBirth(new GregorianCalendar(1980, 11, 21).getTime())
                .build();
        BookDTO bookDTO = new BookDTO.Builder("1")
                .withRating(4.0d)
                .withDescription("desc")
                .withAuthor(authorDTO)
                .withPublishedDate(new GregorianCalendar(2008, 11, 21).getTime())
                .build();

        String jsonRequest = objectMapper.writeValueAsString(bookDTO);
        when(bookService.insert(any(BookDTO.class))).thenReturn(bookDTO);
        mockMvc.perform(MockMvcRequestBuilders.put("/books")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        verifyZeroInteractions(bookService);
    }

    @Test
    public void updateTest() throws Exception {
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

        String jsonRequest = objectMapper.writeValueAsString(bookDTO);
        when(bookService.update(any(BookDTO.class))).thenReturn(bookDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("1"));

        ArgumentCaptor<BookDTO> dtoCaptor = ArgumentCaptor.forClass(BookDTO.class);
        verify(bookService, times(1)).update(dtoCaptor.capture());
        verifyNoMoreInteractions(bookService);

        BookDTO dtoArgument = dtoCaptor.getValue();
        assertThat(dtoArgument.getTitle(), is("Test"));
    }

    @Test
    public void deleteTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/books/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getAllBooksByAuthor() throws Exception{
        Author author = new Author("1", "Suzanne", "Collins", new GregorianCalendar(1980, 11, 21).getTime(), "bio");

        List<Book> books = new ArrayList<>(
                Arrays.asList(
                        new Book("10", "The Hunger Games", "desc", new GregorianCalendar(2008, 10, 9).getTime(), 4.5d, author),
                        new Book("11", "Catching Fire", "desc", new GregorianCalendar(2009, 5, 25).getTime(), 4.2d, author)));

        AuthorBooksDTO authorBooksDTO = new AuthorBooksDTO();
        authorBooksDTO.setAuthor(author);
        authorBooksDTO.setBooks(books);

        when(bookService.getAllBooksByAuthor("Collins", "Suzanne")).thenReturn(authorBooksDTO);
        mockMvc.perform(MockMvcRequestBuilders.get("/books/byAuthor/Suzanne/Collins")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.id", Matchers.is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.firstName", Matchers.is("Suzanne")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.lastName", Matchers.is("Collins")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books",hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books[0].title", Matchers.is("The Hunger Games")));
        verify(bookService, times(1)).getAllBooksByAuthor("Collins", "Suzanne");
    }
}
