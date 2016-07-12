package com.example.blueprint.rest;

import com.example.blueprint.User;
import com.example.blueprint.service.UsersService;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping(value = "/api/v1/users")
@RestController
public class UsersController {

    @Autowired
    private UsersService service;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}")
    public @ResponseBody ResponseEntity<?> findUserById(@PathVariable long userId) {
        return service.findUserById(userId);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody User createUser(@RequestBody User user) {
        return service.createUser(user);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{userId}")
    public @ResponseBody ResponseEntity<?> updateUser(@PathVariable long userId, @RequestBody User user) {
        return service.updateUser(userId, user);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{userId}")
    public @ResponseBody ResponseEntity<?> deleteUser(@PathVariable long userId) {
        return service.deleteUser(userId);
    }

}
