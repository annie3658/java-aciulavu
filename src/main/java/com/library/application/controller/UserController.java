package com.library.application.controller;


import com.library.application.dto.UserDTO;
import com.library.application.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private CustomUserDetailsService userService;

    @Autowired
    public UserController(CustomUserDetailsService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<UserDTO> listUser(){
        return userService.getUsers();
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public UserDTO create(@RequestBody UserDTO user){
        return userService.insert(user);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "id") String id){
        userService.delete(id);

    }

}