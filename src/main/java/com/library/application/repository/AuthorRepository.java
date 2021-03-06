package com.library.application.repository;

import com.library.application.entity.Author;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String> {

  Author findAuthorByLastNameAndAndFirstName(String lastName, String firstName);
}
