package com.library.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.library.application.entity.Author;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class BookDTO {
    private String isbn;
    private String title;
    private String description;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private Date publishedDate;
    private Double rating;
    private Author author;

    private BookDTO(){

    }

    public static class Builder{

        private String isbn;
        private String title;
        private String description;
        private Date publishedDate;
        private Double rating;
        private Author author;

        public Builder (String isbn){
            this.isbn = isbn;
        }

        public Builder withTitle(String title){
            this.title = title;
            return this;
        }

        public Builder withDescription(String description){
            this.description = description;
            return this;
        }

        public Builder withPublishedDate(Date publishedDate){
            this.publishedDate = publishedDate;
            return this;
        }

        public Builder withRating(Double rating){
            this.rating = rating;
            return this;
        }

        public Builder withAuthor(Author author){
            this.author = author;
            return this;
        }

        public BookDTO build(){
            BookDTO bookDTO = new BookDTO();
            bookDTO.isbn = this.isbn;
            bookDTO.title = this.title;
            bookDTO.description = this.description;
            bookDTO.rating = this.rating;
            bookDTO.publishedDate = this.publishedDate;
            bookDTO.author = this.author;

            return bookDTO;
        }
    }
}
