package com.library.application.service;

import ch.qos.logback.classic.Logger;
import com.library.application.dto.UserDTO;
import com.library.application.entity.User;
import com.library.application.repository.UserRepository;
import com.library.application.utils.DTOUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private DTOUtil dtoUtil = new DTOUtil();
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User dbUser = this.userRepository.findByUsername(username);

        if (dbUser != null) {
            Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();

            for (String role : dbUser.getRoles()) {
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantedAuthorities.add(authority);
            }

            org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(
                    dbUser.getUsername(), dbUser.getPassword(), grantedAuthorities);
            return user;
        } else {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
    }

    public UserDTO insert(UserDTO user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return dtoUtil.userToDTO(userRepository.save(dtoUtil.dtoToUser(user)));
    }

    public void delete(String id){
        userRepository.deleteById(id);
    }

    public List<UserDTO> getUsers(){

        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            LOGGER.warn("The books list is empty");
        }
        return users.stream()
                .map(user -> dtoUtil.userToDTO(user))
                .collect(Collectors.toList());
    }

}