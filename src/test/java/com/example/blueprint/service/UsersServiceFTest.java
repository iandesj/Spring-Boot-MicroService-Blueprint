package com.example.blueprint.service;

import static org.testng.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.example.blueprint.User;
import com.example.blueprint.config.TestConfig;

@ContextConfiguration(classes = {TestConfig.class})
public class UsersServiceFTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private UsersService service;

    // This method creates some sample data in H2,
    // then returns the id's of those created objects
    public List<Long> initUsers() {
        final User user1 = new User("foo","bar","baz");
        final User createdUser1 = service.createUser(user1);
        final User user2 = new User("ron","don","con");
        final User createdUser2 = service.createUser(user2);
        return Arrays.asList(createdUser1.getUserId(),createdUser2.getUserId());
    }

    @Test
    public void testGetAllUsers() {
        initUsers();
        final List<User> users = service.getAllUsers();
        assertEquals(users.size(),2);
    }

    @Test
    public void testFindUserById() {
        // create test data
        List<Long> userIds = initUsers();

        final User user1 = service.findUserById(userIds.get(0));
        assertNotNull(user1);

        final User user2 = service.findUserById(userIds.get(1));
        assertNotNull(user2);
    }

    @Test
    public void testFindUserByIdNotExists() {
        final User user = service.findUserById(999);
        assertNull(user);
    }

    @Test
    public void testCreateUser() {
        final User createdUser = service.createUser(new User("foo","bar","baz"));

        assertEquals(createdUser.getFirstName(),"foo");
        assertEquals(createdUser.getLastName(),"bar");
        assertEquals(createdUser.getEmail(),"baz");
    }

    @Test
    public void testUpdateUser() {
        final User createdUser = service.createUser(new User("foo","bar","baz"));
        final User updatedUser = service.updateUser(createdUser.getUserId(),new User("brad","pitt","guy"));

        assertEquals(updatedUser.getFirstName(),"brad");
        assertEquals(updatedUser.getLastName(),"pitt");
        assertEquals(updatedUser.getEmail(),"guy");
    }

    @Test
    public void testUpdateUserNotExists() {
        final User updatedUser = service.updateUser(999,new User("brad","pitt","guy"));
        assertNull(updatedUser);
    }

    @Test
    public void testDeleteUser() {
        final User createdUser = service.createUser(new User("foo","bar","baz"));
        service.deleteUser(createdUser.getUserId());

        final User deletedUser = service.findUserById(createdUser.getUserId());
        assertNull(deletedUser);
    }

}
