package com.library.application.service;

import com.library.application.dto.AuthorDTO;
import com.library.application.entity.Author;
import com.library.application.repository.AuthorRepository;
import com.library.application.utils.DTOUtil;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService{

    private final @NonNull AuthorRepository authorRepository;
    private DTOUtil dtoUtil = new DTOUtil();

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    private Author findById(String id){
       Optional<Author> foundAuthor = authorRepository.findById(id);
        return setFoundAuthor(foundAuthor);
    }

    public AuthorDTO findAuthorById(String id){
        return dtoUtil.authorToDTO(findById(id));
    }

    public Author findByFullName(String lastName, String firstName){

        Optional<Author> foundAuthor = Optional.ofNullable(authorRepository.findAuthorByLastNameAndAndFirstName(lastName, firstName));
        return setFoundAuthor(foundAuthor);
    }

    private Author setFoundAuthor(Optional<Author> foundAuthor) {
        if (foundAuthor.isPresent()) {
            return foundAuthor.get();
        }
        Author author = new Author();
        author.setId(StringUtils.EMPTY);
        author.setBio(StringUtils.EMPTY);
        author.setDateOfBirth(null);
        author.setLastName(StringUtils.EMPTY);
        author.setFirstName(StringUtils.EMPTY);
        return author;
    }

    public AuthorDTO findAuthorByFullName(String lastName, String firstName){
        return dtoUtil.authorToDTO(authorRepository.findAuthorByLastNameAndAndFirstName(lastName, firstName));
    }

    public List<AuthorDTO> getAll(){
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(author -> dtoUtil.authorToDTO(author))
                .collect(Collectors.toList());
    }

    public AuthorDTO insert(AuthorDTO author){
        Author existingAuthor = findByFullName(author.getLastName(), author.getFirstName());
        if(existingAuthor.getId().equals(StringUtils.EMPTY)) {
            return dtoUtil.authorToDTO(authorRepository.insert(dtoUtil.dtoToAuthor(author)));
        }
        author.setId(existingAuthor.getId());
        return this.update(author);
    }

    public AuthorDTO update(AuthorDTO author){
        return dtoUtil.authorToDTO(authorRepository.save(dtoUtil.dtoToAuthor(author)));
    }

    public void delete(String id){
        authorRepository.deleteById(id);

    }



}
