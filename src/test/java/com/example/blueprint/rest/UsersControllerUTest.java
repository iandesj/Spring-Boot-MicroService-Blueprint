package com.example.blueprint.rest;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.Arrays;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.example.blueprint.User;
import com.example.blueprint.service.UsersService;

public class UsersControllerUTest {

    private MockMvc restMvc;

    @InjectMocks
    private UsersController controller;

    @Mock
    private UsersService service;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
        restMvc = standaloneSetup(controller).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @Test
    public void testGetAllUsers() throws Exception {
        when(service.getAllUsers()).thenReturn(Arrays.asList(new User(1),new User(2)));
        restMvc.perform(get("/api/v1/users").header("X-TenantID","1")).andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
        verify(service,times(1)).getAllUsers();
    }

    @Test
    public void testFindUserById() throws Exception {
        final User user = new User(1);
        user.setEmail("baz");
        user.setFirstName("foo");
        user.setLastName("bar");
        when(service.findUserById(anyLong())).thenReturn(user);
        restMvc.perform(get("/api/v1/users/1").header("X-TenantID","1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1)).andExpect(jsonPath("$.email").value("baz"))
                .andExpect(jsonPath("$.firstName").value("foo")).andExpect(jsonPath("$.lastName").value("bar"));
        verify(service,times(1)).findUserById(eq(1L));
    }

    @Test
    public void testFindUserByIdNotFound() throws Exception {
        restMvc.perform(get("/api/v1/users/999").header("X-TenantID","1")).andExpect(status().isNotFound());
        verify(service,times(1)).findUserById(eq(999L));
    }

    @Test
    public void testCreateUser() throws Exception {
        final User user = new User();
        user.setEmail("foo@bar.com");
        user.setFirstName("foo");
        user.setLastName("bar");

        when(service.createUser(any(User.class))).thenReturn(user);

        final String userBody = "{\"email\":\"foo@bar.com\",\"firstName\":\"foo\",\"lastName\":\"bar\"}";
        restMvc.perform(post("/api/v1/users").header("X-TenantID","1").content(userBody)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(service,times(1)).createUser(any(User.class));
    }

    @Test
    public void testUpdateUser() throws Exception {
        final User user = new User(1);
        user.setEmail("foo@bar.com");
        user.setFirstName("foo");
        user.setLastName("baz");

        when(service.updateUser(anyLong(),any(User.class))).thenReturn(user);

        final String userBody = "{\"email\":\"foo@bar.com\",\"firstName\":\"foo\",\"lastName\":\"baz\"}";
        restMvc.perform(put("/api/v1/users/1").header("X-TenantID","1").content(userBody)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("foo")).andExpect(jsonPath("$.lastName").value("baz"))
                .andExpect(jsonPath("$.email").value("foo@bar.com"));
        verify(service,times(1)).updateUser(anyLong(),any(User.class));
    }

    @Test
    public void testUpdateUserNotFound() throws Exception {
        final String userBody = "{\"email\":\"foo@bar.com\",\"firstName\":\"foo\",\"lastName\":\"baz\"}";
        restMvc.perform(put("/api/v1/users/999").header("X-TenantID","1").content(userBody)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(service,times(1)).updateUser(eq(999L),any(User.class));
    }

    @Test
    public void testDeleteUser() throws Exception {
        restMvc.perform(delete("/api/v1/users/1").header("X-TenantID","1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(service,times(1)).deleteUser(eq(1L));
    }

}