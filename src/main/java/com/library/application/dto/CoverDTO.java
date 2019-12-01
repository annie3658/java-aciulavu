package com.library.application.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Data
public class CoverDTO {
    private String id;
    @NotEmpty(message = "Please provide a link")
    private String link;
    private String bookTitle;

    public CoverDTO() {
    }

    public static class Builder{
        private String id;
        private String link;
        private String bookTitle;

        public Builder (String id){
            this.id = id;
        }

        public Builder withLink(String link){
            this.link = link;
            return this;
        }

        public Builder withBookTitle(String bookTitle){
            this.bookTitle = bookTitle;
            return this;
        }

        public CoverDTO build(){

            CoverDTO coverDTO = new CoverDTO();
            coverDTO.link = this.link;
            coverDTO.bookTitle = this.bookTitle;
            coverDTO.id = this.id;

            return coverDTO;

        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CoverDTO)) return false;
        CoverDTO coverDTO = (CoverDTO) o;
        return Objects.equals(id, coverDTO.id) &&
                Objects.equals(link, coverDTO.link) &&
                Objects.equals(bookTitle, coverDTO.bookTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, link, bookTitle);
    }

    @Override
    public String toString() {
        return "CoverDTO{" +
                "id='" + id + '\'' +
                ", link='" + link + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                '}';
    }
}
