package com.library.application.controller;

import com.library.application.dto.AuthorBooksDTO;
import com.library.application.dto.BookDTO;
import com.library.application.dto.CoverDTO;
import com.library.application.service.BookService;
import com.library.application.service.CoverService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final @NonNull BookService bookService;
    private final @NotNull CoverService coverService;


    @Autowired
    public BookController(@NonNull BookService bookService, @NonNull CoverService coverService) {
        this.bookService = bookService;
        this.coverService = coverService;

    }

    @GetMapping
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

    @GetMapping("/book-title/{title}")
    public BookDTO getBookByTitle(@PathVariable("title") String title){

        return bookService.findByTitle(title);
    }

    @GetMapping("/book/{id}")
    public BookDTO getBookById(@PathVariable("id") String id) {
        return bookService.findBookById(id);
    }


    @GetMapping("/author/{firstName}/{lastName}")
    public AuthorBooksDTO getAllBooksByAuthor(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName){
        return bookService.getAllBooksByAuthor(lastName, firstName);
    }

    @GetMapping("/book/cover/{title}")
    public CoverDTO getBookCover(@PathVariable("title") String title) {
        return coverService.findCoverByBookTitle(title);
    }

    @PutMapping("/book/cover")
    public CoverDTO insert(@Valid @RequestBody CoverDTO cover){
        return coverService.insert(cover);
    }

    @PostMapping("/book/cover")
    public CoverDTO update (@Valid @RequestBody CoverDTO cover){
        return coverService.update(cover);
    }

    @DeleteMapping("/book/cover/{id}")
    public void deleteCover (@PathVariable("id") String id){
          coverService.delete(id);
    }






}
