package com.library.application.controller;

import com.library.application.dto.AuthorBooksDTO;
import com.library.application.dto.BookDTO;
import com.library.application.service.BookService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final @NonNull BookService bookService;

    @Autowired
    public BookController(@NonNull BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public List<BookDTO> getAllBooks(){
        return bookService.getAll();
    }

    @PutMapping
    public BookDTO insert(@Valid @RequestBody BookDTO book){
        return bookService.insert(book);
    }

    @PostMapping
    public BookDTO update (@Valid @RequestBody BookDTO book){
        return bookService.update(book);
    }

    @DeleteMapping("/{id}")
    public  void delete(@PathVariable("id") String id) {
        bookService.delete(id);
    }

    @GetMapping("/book/{title}")
    public BookDTO getBookByTitle(@PathVariable("title") String title){
        return bookService.findByTitle(title);
    }

    @GetMapping("/bookById/{id}")
    public BookDTO getBookById(@PathVariable("id") String id) {
        return bookService.findBookById(id);
    }


    @GetMapping("/byAuthor/{firstName}/{lastName}")
    public AuthorBooksDTO getAllBooksByAuthor(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName){
        return bookService.getAllBooksByAuthor(lastName, firstName);
    }




}
