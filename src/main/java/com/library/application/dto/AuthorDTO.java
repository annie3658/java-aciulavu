package com.library.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.util.Date;
@Getter
@Setter
public class AuthorDTO {


    private String id;
    @NotEmpty(message = "Please provide a first name")
    private String firstName;
    @NotEmpty(message = "Please provide a last name")
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Past(message = "Date of birth must be in the past")
    private Date dateOfBirth;
    private String bio;

    private AuthorDTO(){

    }

    public static class Builder {
        private String id;
        private String firstName;
        private String lastName;
        private Date dateOfBirth;
        private String bio;

        public Builder (String id){
            this.id = id;
        }

        public Builder withFirstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public Builder withDateOfBirth(Date dateOfBirth){
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder withBio(String bio){
            this.bio = bio;
            return this;
        }

        public AuthorDTO build(){

            AuthorDTO authorDTO = new AuthorDTO();
            authorDTO.firstName = this.firstName;
            authorDTO.lastName = this.lastName;
            authorDTO.dateOfBirth = this.dateOfBirth;
            authorDTO.id = this.id;
            authorDTO.bio = this.bio;

            return authorDTO;

        }
    }

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", bio='" + bio + '\'' +
                '}';
    }
}
