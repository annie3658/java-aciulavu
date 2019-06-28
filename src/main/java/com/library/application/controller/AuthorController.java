package com.library.application.controller;

import com.library.application.dto.AuthorDTO;
import com.library.application.service.AuthorService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final @NonNull AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/all")
    public List<AuthorDTO> getAllAuthors(){
        return authorService.getAll();

    }

    @PutMapping
    public void insert(@RequestBody AuthorDTO author){
        authorService.insert(author);
    }

    @PostMapping
    public void update(@RequestBody AuthorDTO author){
        authorService.update(author);
    }

    @GetMapping("/{id}")
    public AuthorDTO getAuthor(@PathVariable("id") String id){
        return authorService.findAuthorById(id);
    }

    @GetMapping("/author/{firstName}/{lastName}")
    public AuthorDTO getAuthorByLastName(@PathVariable("firstName") String firstName,@PathVariable("lastName") String lastName){
        return authorService.findAuthorByFullName(lastName, firstName);
    }

    @DeleteMapping("/{id}")
    public  void delete(@PathVariable("id") String id)
    {
        authorService.delete(id);
    }



}
