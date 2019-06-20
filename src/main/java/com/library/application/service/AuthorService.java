package com.library.application.service;

import com.library.application.entity.Author;
import com.library.application.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService{
    @Autowired
    private AuthorRepository authorRepository;

    public Optional<Author> findById(String id){
        return authorRepository.findById(id);
    }

    public Author findByLastName(String lastName){
        return authorRepository.findAuthorByLastName(lastName);
    }

    public List<Author> getAll(){
        List<Author> authors = authorRepository.findAll();
        return authors;
    }

    public Author insert(Author author){
        return authorRepository.insert(author);
    }

    public Author update(Author author){
        return authorRepository.save(author);
    }

    public void delete(String id){
        authorRepository.deleteById(id);

    }



}
