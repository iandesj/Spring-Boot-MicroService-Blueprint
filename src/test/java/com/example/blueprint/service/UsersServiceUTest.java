package com.example.blueprint.service;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.example.blueprint.User;
import com.example.blueprint.repository.UsersRepository;

public class UsersServiceUTest {

    @InjectMocks
    private UsersService service;

    @Mock
    private UsersRepository repository;

    private User user1;
    private User user2;

    @BeforeMethod
    private void setup() {
        user1 = new User("foo","bar","baz");
        user1.setUserId(1);
        user2 = new User("ron","don","con");
        user2.setUserId(2);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        when(repository.findAll()).thenReturn(Arrays.asList(user1,user2));
        final List<User> users = service.getAllUsers();
        assertEquals(users.size(),2);
    }

    @Test
    public void testFindUserById() {
        when(repository.findOne(eq(1L))).thenReturn(user1);
        final User retrievedUser = service.findUserById(1);
        assertNotNull(user1);
        assertEquals(retrievedUser.getUserId(),user1.getUserId());
    }

    @Test
    public void testFindUserByIdNotExists() {
        when(repository.findOne(eq(999L))).thenReturn(null);
        final User nullUser = service.findUserById(999);
        assertNull(nullUser);
    }

    @Test
    public void testCreateUser() {
        when(repository.save(any(User.class))).thenReturn(user1);
        final User createdUser = service.createUser(new User("foo","bar","baz"));

        assertEquals(createdUser.getFirstName(),user1.getFirstName());
        assertEquals(createdUser.getLastName(),user1.getLastName());
        assertEquals(createdUser.getEmail(),user1.getEmail());
    }

    @Test
    public void testUpdateUser() {
        when(repository.exists(eq(1L))).thenReturn(true);
        when(repository.save(any(User.class))).thenReturn(user2);
        final User updatedUser = service.updateUser(user1.getUserId(),user2);

        assertEquals(updatedUser.getFirstName(),user2.getFirstName());
        assertEquals(updatedUser.getLastName(),user2.getLastName());
        assertEquals(updatedUser.getEmail(),user2.getEmail());
    }

    @Test
    public void testUpdateUserNotExists() {
        when(repository.exists(eq(999L))).thenReturn(false);
        final User nullUpdatedUser = service.updateUser(999,new User("brad","pitt","guy"));
        assertNull(nullUpdatedUser);
    }

    @Test
    public void testDeleteUser() {
        service.deleteUser(user1.getUserId());
    }

}
