package com.library.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.library.application.validator.Rating;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;
@Getter
@Setter
public class BookDTO {
    private String isbn;
    @NotEmpty(message = "Please provide a title")
    private String title;
    private String description;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    @PastOrPresent(message = "The published date can't be in the future")
    private Date publishedDate;
    @Rating
    private Double rating;
    @NotNull(message = "Please provide an author")
    private AuthorDTO author;

    private BookDTO(){

    }

    public static class Builder{

        private String isbn;
        private String title;
        private String description;
        private Date publishedDate;
        private Double rating;
        private AuthorDTO author;

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

        public Builder withAuthor(AuthorDTO author){
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

    @Override
    public String toString() {
        return "BookDTO{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", publishedDate=" + publishedDate +
                ", rating=" + rating +
                ", author=" + author +
                '}';
    }
}
