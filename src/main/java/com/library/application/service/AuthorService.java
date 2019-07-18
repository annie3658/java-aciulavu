package com.library.application.service;

import ch.qos.logback.classic.Logger;
import com.library.application.dto.AuthorDTO;
import com.library.application.entity.Author;
import com.library.application.exception.AuthorNotFoundException;
import com.library.application.repository.AuthorRepository;
import com.library.application.utils.DTOUtil;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService{

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(AuthorService.class);
    private final @NonNull AuthorRepository authorRepository;
    private DTOUtil dtoUtil = new DTOUtil();

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    private Author findById(String id){
       Optional<Author> foundAuthor = authorRepository.findById(id);
        if (foundAuthor.isPresent()) {
            return foundAuthor.get();
        }
        LOGGER.error("The Author with id {} was not found", id);
        throw new AuthorNotFoundException(id);
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
       Author author = authorRepository.findAuthorByLastNameAndAndFirstName(lastName, firstName);
       if(author == null){
           LOGGER.error("The Author {} {} was not found", firstName, lastName);
           throw new AuthorNotFoundException(firstName + lastName);
       }
        return dtoUtil.authorToDTO(author);
    }

    public List<AuthorDTO> getAll(){
        List<Author> authors = authorRepository.findAll();
        if(authors.isEmpty()){
            LOGGER.warn("The authors list is empty");
        }
        return authors.stream()
                .map(author -> dtoUtil.authorToDTO(author))
                .collect(Collectors.toList());
    }

    public AuthorDTO insert(AuthorDTO author){
        Author existingAuthor = findByFullName(author.getLastName(), author.getFirstName());
        if(existingAuthor.getId().equals(StringUtils.EMPTY)) {
            LOGGER.info("Created new author: " + author.toString());
            return dtoUtil.authorToDTO(authorRepository.insert(dtoUtil.dtoToAuthor(author)));
        }
        author.setId(existingAuthor.getId());
        return this.update(author);
    }

    public AuthorDTO update(AuthorDTO author){
        LOGGER.info("Updated author: " + author.toString());
        return dtoUtil.authorToDTO(authorRepository.save(dtoUtil.dtoToAuthor(author)));
    }

    public void delete(String id){
        LOGGER.info("Deleted author with id: " + id);
        authorRepository.deleteById(id);

    }



}
