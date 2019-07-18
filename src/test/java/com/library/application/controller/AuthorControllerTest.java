package com.library.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.application.dto.AuthorDTO;
import com.library.application.service.AuthorService;
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
import java.util.GregorianCalendar;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AuthorControllerTest {

    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    AuthorController authorController;

    @Mock
    AuthorService authorService;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(authorController)
                .build();
    }

    @Test
    public void getAllAuthors() throws Exception{
        AuthorDTO authorDTO = new AuthorDTO.Builder("1")
                .withFirstName("Suzanne")
                .withLastName("Collins")
                .withBio("bio")
                .withDateOfBirth(new GregorianCalendar(1980, 11, 21).getTime())
                .build();

        AuthorDTO authorTwoDTO = new AuthorDTO.Builder("2")
                .withFirstName("Test2")
                .withLastName("Collins")
                .withBio("bio")
                .withDateOfBirth(new GregorianCalendar(1980, 11, 21).getTime())
                .build();

        List<AuthorDTO> authorDTOS = new ArrayList<>();
        authorDTOS.add(authorDTO);
        authorDTOS.add(authorTwoDTO);

        when(authorService.getAll()).thenReturn(authorDTOS);
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/all")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getAuthorById() throws Exception{
        AuthorDTO authorDTO = new AuthorDTO.Builder("1")
                .withFirstName("Suzanne")
                .withLastName("Collins")
                .withBio("bio")
                .withDateOfBirth(new GregorianCalendar(1980, 11, 21).getTime())
                .build();

        when(authorService.findAuthorById("1")).thenReturn(authorDTO);
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Suzanne")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("Collins")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth", Matchers.is("20-12-1980")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bio", Matchers.is("bio")));
        verify(authorService).findAuthorById("1");
        verify(authorService, times(1)).findAuthorById("1");
    }

    @Test
    public void getAuthorByNameTest() throws Exception{
        AuthorDTO authorDTO = new AuthorDTO.Builder("1")
                .withFirstName("Suzanne")
                .withLastName("Collins")
                .withBio("bio")
                .withDateOfBirth(new GregorianCalendar(1980, 11, 21).getTime())
                .build();

        when(authorService.findAuthorByFullName("Collins", "Suzanne")).thenReturn(authorDTO);
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/author/Suzanne/Collins")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Suzanne")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("Collins")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth", Matchers.is("20-12-1980")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bio", Matchers.is("bio")));
        verify(authorService).findAuthorByFullName("Collins", "Suzanne");
        verify(authorService, times(1)).findAuthorByFullName("Collins", "Suzanne");
    }

    @Test
    public void insertTest() throws Exception{
        AuthorDTO authorDTO = new AuthorDTO.Builder("1")
                .withFirstName("Suzanne")
                .withLastName("Collins")
                .withBio("bio")
                .withDateOfBirth(new GregorianCalendar(1980, 11, 21).getTime())
                .build();
        when(authorService.insert(any(AuthorDTO.class))).thenReturn(authorDTO);
        String jsonRequest = objectMapper.writeValueAsString(authorDTO);
        mockMvc.perform(MockMvcRequestBuilders.put("/authors")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is("1")));

        ArgumentCaptor<AuthorDTO> dtoCaptor = ArgumentCaptor.forClass(AuthorDTO.class);
        verify(authorService, times(1)).insert(dtoCaptor.capture());
        verifyNoMoreInteractions(authorService);

        AuthorDTO dtoArgument = dtoCaptor.getValue();
        assertThat(dtoArgument.getFirstName(), is("Suzanne"));
    }

    @Test
    public void insertAuthorWithoutFirstNameReturnValidationErrorTest() throws Exception {
        AuthorDTO authorDTO = new AuthorDTO.Builder("1")
                .withLastName("Collins")
                .withBio("bio")
                .withDateOfBirth(new GregorianCalendar(1980, 11, 21).getTime())
                .build();
        String jsonRequest = objectMapper.writeValueAsString(authorDTO);
        when(authorService.insert(any(AuthorDTO.class))).thenReturn(authorDTO);
        mockMvc.perform(MockMvcRequestBuilders.put("/authors")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        verifyZeroInteractions(authorService);
    }

    @Test
    public void updateTest() throws Exception {
        AuthorDTO authorDTO = new AuthorDTO.Builder("1")
                .withFirstName("Suzanne")
                .withLastName("Collins")
                .withBio("bio")
                .withDateOfBirth(new GregorianCalendar(1980, 11, 21).getTime())
                .build();

        String jsonRequest = objectMapper.writeValueAsString(authorDTO);
        when(authorService.update(any(AuthorDTO.class))).thenReturn(authorDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));

        ArgumentCaptor<AuthorDTO> dtoCaptor = ArgumentCaptor.forClass(AuthorDTO.class);
        verify(authorService, times(1)).update(dtoCaptor.capture());
        verifyNoMoreInteractions(authorService);

        AuthorDTO dtoArgument = dtoCaptor.getValue();
        assertThat(dtoArgument.getLastName(), is("Collins"));
    }

    @Test
    public void deleteTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/authors/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
