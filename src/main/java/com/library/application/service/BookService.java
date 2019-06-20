package com.library.application.service;

import com.library.application.dto.BookDTO;
import com.library.application.entity.Author;
import com.library.application.entity.Book;
import com.library.application.repository.AuthorRepository;
import com.library.application.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    public Optional<Book> findById(String id){
        return bookRepository.findById(id);
    }

    public Book findByTitle(String title){
        return bookRepository.findBookByTitle(title);
    }

    public List<Book> getAll(){
        List<Book> books = bookRepository.findAll();
        return books;
    }

    public Book insert(Book book){

        authorRepository.insert(book.getAuthor());
        return bookRepository.insert(book);
    }

    public Book update(Book book){
       return bookRepository.save(book);
    }

    public void delete(String id){
        bookRepository.deleteById(id);
    }

    public BookDTO getAllBooksByAuthor(String lastName){
        Author author = authorRepository.findAuthorByLastName(lastName);
        List<Book> books = bookRepository.findByAuthor(lastName);

        BookDTO dto = new BookDTO();
        dto.setAuthor(author);
        dto.setBooks(books);

        return dto;
    }

    public List<Book> getBooksByAuthor(String lastName){
        List<Book> books = bookRepository.findByAuthor(lastName);
        return books;
    }


}
