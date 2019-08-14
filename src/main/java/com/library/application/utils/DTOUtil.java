package com.library.application.utils;

import com.library.application.dto.AuthorDTO;
import com.library.application.dto.BookDTO;
import com.library.application.dto.UserDTO;
import com.library.application.entity.Author;
import com.library.application.entity.Book;
import com.library.application.entity.User;

public class DTOUtil {

    public AuthorDTO authorToDTO(Author author){
        AuthorDTO authorDTO = new AuthorDTO.Builder(author.getId())
                .withFirstName(author.getFirstName())
                .withLastName(author.getLastName())
                .withBio(author.getBio())
                .withDateOfBirth(author.getDateOfBirth())
                .build();
        return authorDTO;
    }

    public BookDTO bookToDTO(Book book){
        BookDTO bookDTO = new BookDTO.Builder(book.getIsbn())
                .withTitle(book.getTitle())
                .withDescription(book.getDescription())
                .withPublishedDate(book.getPublishedDate())
                .withRating(book.getRating())
                .withAuthor(authorToDTO(book.getAuthor()))
                .build();
        return bookDTO;
    }

    public Book dtoToBook(BookDTO bookDTO){
        Book book = new Book();
        book.setIsbn(bookDTO.getIsbn());
        book.setDescription(bookDTO.getDescription());
        book.setTitle(bookDTO.getTitle());
        book.setPublishedDate(bookDTO.getPublishedDate());
        book.setRating(bookDTO.getRating());
        book.setAuthor(dtoToAuthor(bookDTO.getAuthor()));
        return book;
    }

    public Author dtoToAuthor(AuthorDTO authorDTO){
        Author author = new Author();
        author.setId(authorDTO.getId());
        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());
        author.setDateOfBirth(authorDTO.getDateOfBirth());
        author.setBio(authorDTO.getBio());
        return author;
    }

    public User dtoToUser(UserDTO userDTO){
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        user.setRoles(userDTO.getRoles());
        return  user;
    }

    public UserDTO userToDTO(User user){
        UserDTO userDTO = new UserDTO.Builder(user.getId())
                .withName(user.getName())
                .withUsername(user.getUsername())
                .withPassword(user.getPassword())
                .withRoles(user.getRoles())
                .build();
        return userDTO;
    }
}
