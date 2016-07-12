package com.example.blueprint.service;

import com.example.blueprint.User;
import com.example.blueprint.repository.UsersRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UsersRepository repo;

    public List<User> getAllUsers() {
        return (List<User>)repo.findAll();
    }

    public ResponseEntity<?> findUserById(long userId) {
        final User user = repo.findOne(userId);
        return (user == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    public User createUser(User user) {
        return repo.save(user);
    }

    public ResponseEntity<?> updateUser(long userId, User updatedUser) {
        if(repo.exists(userId)) {
            updatedUser.setUserId(userId);
            return ResponseEntity.ok(repo.save(updatedUser));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> deleteUser(long userId) {
        if(repo.exists(userId)) {
            repo.delete(userId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
