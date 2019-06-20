package com.library.application.controller;

import com.library.application.entity.Author;
import com.library.application.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/all")
    public List<Author> getAllAuthors(){
return authorRepository.findAll();
        return authors;
    }

    @PutMapping
    public void insert(@RequestBody Author author){
        authorService.insert(author);
    }

    @PostMapping
    public void update(@RequestBody Author author){
        authorService.update(author);
    }

    @GetMapping("/{id}")
    public Optional<Author> getAuthor(@PathVariable("id") String id){
        return authorService.findById(id);
    }

    @GetMapping("/author/{lastName}")
    public Author getAuthorByLastName(@PathVariable("lastName") String lastName){
        return authorService.findByLastName(lastName);
    }

    @DeleteMapping("/{id}")
    public  void delete(@PathVariable("id") String id)
    {
        authorService.delete(id);
    }



}
