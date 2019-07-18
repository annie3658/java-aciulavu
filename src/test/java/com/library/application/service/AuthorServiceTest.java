package com.library.application.service;

import com.library.application.dto.AuthorDTO;
import com.library.application.entity.Author;
import com.library.application.exception.AuthorNotFoundException;
import com.library.application.repository.AuthorRepository;
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
public class AuthorServiceTest {

    private AuthorService service;
    private DTOUtil dtoUtil = new DTOUtil();

    @Autowired
    public void setService(AuthorService service) {
        this.service = service;
    }
    @Autowired
    public void setRepository(AuthorRepository repository) {
        this.repository = repository;
    }

    @MockBean
    private AuthorRepository repository;

    @Test
    public void findByIdTest(){
        AuthorDTO authorDTO = new AuthorDTO.Builder("1")
                .withFirstName("Suzanne")
                .withLastName("Collins")
                .withBio("bio")
                .withDateOfBirth(new GregorianCalendar(1980, 11, 21).getTime())
                .build();
        Author author = new Author("1","Suzanne","Collins", new GregorianCalendar(1980, 11, 21).getTime(), "bio");
        doReturn(Optional.of(author)).when(repository).findById("1");
        assertEquals(authorDTO, service.findAuthorById("1"));
    }

    @Test(expected = AuthorNotFoundException.class)
    public void findByIdThrowsExceptionTest(){
        AuthorDTO authorDTO = new AuthorDTO.Builder("1")
                .withFirstName("Suzanne")
                .withLastName("Collins")
                .withBio("bio")
                .withDateOfBirth(new GregorianCalendar(1980, 11, 21).getTime())
                .build();
        Author author = new Author("1","Suzanne","Collins", new GregorianCalendar(1980, 11, 21).getTime(), "bio");
        doReturn(Optional.of(author)).when(repository).findById("1");
        assertEquals(authorDTO, service.findAuthorById("444"));
    }

    @Test
    public void findAuthorByFullNameTest(){
        AuthorDTO authorDTO = new AuthorDTO.Builder("1")
                .withFirstName("Suzanne")
                .withLastName("Collins")
                .withBio("bio")
                .withDateOfBirth(new GregorianCalendar(1980, 11, 21).getTime())
                .build();
        Author author = new Author("1","Suzanne","Collins", new GregorianCalendar(1980, 11, 21).getTime(), "bio");
        doReturn(author).when(repository).findAuthorByLastNameAndAndFirstName("Collins", "Suzanne");
        assertEquals(authorDTO, service.findAuthorByFullName("Collins", "Suzanne"));
    }

    @Test(expected = AuthorNotFoundException.class)
    public void findAuthorByFullNameThrowsExceptionTest(){
        AuthorDTO authorDTO = new AuthorDTO.Builder("1")
                .withFirstName("Suzanne")
                .withLastName("Collins")
                .withBio("bio")
                .withDateOfBirth(new GregorianCalendar(1980, 11, 21).getTime())
                .build();
        Author author = new Author("1","Suzanne","Collins", new GregorianCalendar(1980, 11, 21).getTime(), "bio");
        doReturn(author).when(repository).findAuthorByLastNameAndAndFirstName("Collins", "Suzanne");
        assertEquals(authorDTO, service.findAuthorByFullName("Test", "Test"));
    }

    @Test
    public void getAllTest(){
        List<Author> authors = new ArrayList<>(
                Arrays.asList(
                        new Author("1","Suzanne","Collins", new GregorianCalendar(1980, 11, 21).getTime(), "bio"),
                        new Author("2","Sarah","Myer", new GregorianCalendar(1991, 05, 12).getTime(), "bio"),
                        new Author("3","Dana","Smith", new GregorianCalendar(1997, 02, 03).getTime(), "bio")));
        when(repository.findAll()).thenReturn(authors);
        List<AuthorDTO> authorDTOS = authors.stream()
                .map(author -> dtoUtil.authorToDTO(author))
                .collect(Collectors.toList());
        assertEquals(authorDTOS , service.getAll());
    }

    @Test
    public void insertTest(){
        Author author = new Author("1","Suzanne","Collins", new GregorianCalendar(1980, 11, 21).getTime(), "bio");
        when(repository.insert(author)).thenReturn(author);
        assertEquals(dtoUtil.authorToDTO(author), service.insert(dtoUtil.authorToDTO(author)));
    }

    @Test
    public void updateTest(){
        Author author = new Author("1","Suzanne","Collins", new GregorianCalendar(1980, 11, 21).getTime(), "bio");
        when(repository.save(author)).thenReturn(author);
        assertEquals(dtoUtil.authorToDTO(author), service.update(dtoUtil.authorToDTO(author)));
    }

    @Test
    public void deleteTest(){
        service.delete("1");
        verify(repository, times(1)).deleteById("1");
    }
}
