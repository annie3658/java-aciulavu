package com.library.application.repository;

import com.library.application.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository <Book, String> {

    Book findBookByTitle(String title);

    @Query(value = "{'author.lastName':?0}")
    List<Book> findByAuthor(String lastName);

}
