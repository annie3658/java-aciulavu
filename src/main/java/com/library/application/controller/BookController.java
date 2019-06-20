package com.library.application.controller;

import com.library.application.dto.BookDTO;
import com.library.application.entity.Book;
import com.library.application.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/all")
    public List<Book> getAllBooks(){
        return bookService.getAll();
    }

    @PutMapping
    public void insert(@RequestBody Book book){
        bookService.insert(book);
    }

    @PostMapping
    public void update (@RequestBody Book book){
        bookService.update(book);
    }

    @DeleteMapping("/{id}")
    public  void delete(@PathVariable("id") String id) {
        bookService.delete(id);
    }

    @GetMapping("/book/{title}")
    public Book getBookByTitle(@PathVariable("title") String title){
        return bookService.findByTitle(title);
    }


    @GetMapping("/byAuthor/{author}")
    public BookDTO getAllBooksByAuthor(@PathVariable("author") String author){
        return bookService.getAllBooksByAuthor(author);
    }

    @GetMapping("/booksByAuthor/{author}")
    public List<Book> getBooksByAuthor(@PathVariable("author") String author){
        return bookService.getBooksByAuthor(author);
    }



}
