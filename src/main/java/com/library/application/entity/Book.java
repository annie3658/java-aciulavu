package com.library.application.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "books")
public class Book {
    @Id
    private String isbn;
    private String title;
    private String description;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private Date publishedDate;
    @Indexed(direction = IndexDirection.DESCENDING)
    private Double rating;
    private Author author;
}
