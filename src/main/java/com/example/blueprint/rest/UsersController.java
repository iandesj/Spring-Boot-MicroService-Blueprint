package com.example.blueprint.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.blueprint.User;
import com.example.blueprint.config.TenantContext;
import com.example.blueprint.service.UsersService;

@RequestMapping(value = "/api/v1/users")
@RestController
public class UsersController {

    @Autowired
    private UsersService service;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<User> getAllUsers(@RequestHeader("X-TenantID") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        return service.getAllUsers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}")
    public @ResponseBody ResponseEntity<?> findUserById(@RequestHeader("X-TenantID") String tenantName,
            @PathVariable long userId) {
        TenantContext.setCurrentTenant(tenantName);
        final User user = service.findUserById(userId);
        return (user == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody User createUser(@RequestHeader("X-TenantID") String tenantName, @RequestBody User user) {
        TenantContext.setCurrentTenant(tenantName);
        return service.createUser(user);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{userId}")
    public @ResponseBody ResponseEntity<?> updateUser(@RequestHeader("X-TenantID") String tenantName,
            @PathVariable long userId, @RequestBody User user) {
        TenantContext.setCurrentTenant(tenantName);
        final User updatedUser = service.updateUser(userId,user);
        return (updatedUser == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(updatedUser);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{userId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public @ResponseBody void deleteUser(@RequestHeader("X-TenantID") String tenantName, @PathVariable long userId) {
        TenantContext.setCurrentTenant(tenantName);
        service.deleteUser(userId);
    }

}
