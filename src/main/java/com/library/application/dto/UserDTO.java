package com.library.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Objects;
@Data
public class UserDTO {
    private String id;
    private String name;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private List<String> roles;

    public UserDTO() {
    }

        public static class Builder{
            private String id;
            private String name;
            private String username;
            private String password;
            private List<String> roles;

            public Builder (String id){
                this.id = id;
            }

            public Builder withName(String name){
                this.name = name;
                return this;
            }

            public Builder withUsername(String username){
                this.username = username;
                return this;
            }

            public Builder withPassword(String name){
                this.name = name;
                return this;
            }

            public Builder withRoles(List<String> roles){
                this.roles = roles;
                return this;
            }

            public UserDTO build(){

                UserDTO userDTO = new UserDTO();
                userDTO.name = this.name;
                userDTO.username = this.username;
                userDTO.password = this.password;
                userDTO.roles = this.roles;
                userDTO.id = this.id;

                return userDTO;

            }
        }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        UserDTO userDTO = (UserDTO) o;
        return id.equals(userDTO.id) &&
                Objects.equals(name, userDTO.name) &&
                Objects.equals(username, userDTO.username) &&
                Objects.equals(password, userDTO.password) &&
                Objects.equals(roles, userDTO.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, username, password, roles);
    }
}
