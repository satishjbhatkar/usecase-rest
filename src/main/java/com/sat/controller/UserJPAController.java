package com.sat.controller;

import com.sat.model.User;
import com.sat.repository.UserDaoService;
import com.sat.repository.UserRepository;
import com.sat.util.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserJPAController {

    @Autowired
    UserRepository userRepository;
    UserRepository userRepository1;

    @GetMapping("/jpa/users")
    public List<User> retriveAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/jpa/users/{id}")
    public Optional<User>retriveUser(@PathVariable int id){
        Optional<User> user= userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException("id- "+id);
        }
        //Hateoas

        return user;
    }

    @PostMapping("/jpa/users")
    public ResponseEntity createUser(@Valid @RequestBody User user){
        System.out.println("for validation");
        User savedUser= userRepository.save(user);

        URI location= ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id){
       userRepository.deleteById(id);
    }
}
