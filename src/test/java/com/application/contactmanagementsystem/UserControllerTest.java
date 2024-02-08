package com.application.contactmanagementsystem;

import com.application.contactmanagementsystem.controller.UserController;
import com.application.contactmanagementsystem.entity.Address;
import com.application.contactmanagementsystem.entity.UserContact;
import com.application.contactmanagementsystem.exceptions.UserAlreadyExistsException;
import com.application.contactmanagementsystem.exceptions.UserDoesNotExistsException;
import com.application.contactmanagementsystem.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    private static final String END_POINT_PATH = "/api/user";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService service;

    @Test
    public void shouldReturnBadRequestWhenAddingUserWithInvalidData() throws Exception, UserAlreadyExistsException {
        UserContact newUser = new UserContact(1L, "Kamil", "9645330312", "kamil@gmail.com",
                new Address(null, "123", "Building", "Street", "City", "State",
                        "Country", 123456L));

        String requestBody = objectMapper.writeValueAsString(newUser);

        String requestURI = END_POINT_PATH + "/createUser";
        // Perform the POST request
        mockMvc.perform(post(requestURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void shouldReturn201CreatedWhenAddingValidUser() throws Exception, UserAlreadyExistsException {
        UserContact newUser = new UserContact(1L, "Kamil", "9645330312", "kamil@gmail.com",
                new Address(null, "123", "Building", "Street", "City", "State",
                "Country", 123456L));

        Mockito.when(service.createUser(newUser)).thenReturn(newUser);

        String requestBody = objectMapper.writeValueAsString(newUser);

        String requestURI = END_POINT_PATH + "/createUser";
        mockMvc.perform(post(requestURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andDo(print());
    }


    @Test
    public void shouldReturnNotFoundWhenGettingNonExistingUser() throws Exception, UserDoesNotExistsException {
        Long userId = 123L;
        String requestURI = END_POINT_PATH + "/getUser/" + userId;

        Mockito.when(service.getUserById(userId)).thenThrow(UserDoesNotExistsException.class);

        mockMvc.perform(get(requestURI))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void shouldReturnOkWhenGettingExistingUser() throws Exception, UserDoesNotExistsException {
        Long userId = 123L;
        String requestURI = END_POINT_PATH + "/getUser/" + userId;

        UserContact user = new UserContact(userId, "Kamil", "9645330312", "kamil@gmail.com",
                new Address(null, "123", "Building", "Street", "City", "State",
                "Country", 123456L));

        Mockito.when(service.getUserById(userId)).thenReturn(user);

        mockMvc.perform(get(requestURI))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.emailId").value("kamil@gmail.com"))
                .andDo(print());
    }

    @Test
    public void shouldReturnNoContentWhenListingEmptyUsers() throws Exception, UserDoesNotExistsException {
        Mockito.when(service.getAllUsers()).thenReturn(new ArrayList<>());

        String requestURI = END_POINT_PATH + "/getAllUsers";

        mockMvc.perform(get(requestURI))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void shouldReturnOkWhenListingUsers() throws Exception, UserDoesNotExistsException {
        UserContact user1 = new UserContact(1L, "Kamil", "9645330312", "kamil@gmail.com",
                new Address(null, "123", "Building", "Street", "City", "State",
                "Country", 123456L));
        UserContact user2 = new UserContact(2L, "Arjun", "9645333332", "arjun@gmail.com",
                new Address(null, "123", "Building", "Street", "City", "State",
                        "Country", 123456L));

        List<UserContact> listUser = List.of(user1, user2);

        Mockito.when(service.getAllUsers()).thenReturn(listUser);

        String requestURI = END_POINT_PATH + "/getAllUsers";

        mockMvc.perform(get(requestURI))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].emailId").value("kamil@gmail.com"))
                .andExpect(jsonPath("$[1].emailId").value("arjun@gmail.com"))
                .andDo(print());
    }

//    @Test
//    public void shouldReturnNotFoundWhenUpdatingNonExistingUser() throws Exception, UserDoesNotExistsException {
//        Long userId = 123L;
//        String requestURI = END_POINT_PATH + "/" + userId;
//
//        UserContact newUser = new UserContact(userId, "Kamil", "9645330312", "kamil@gmail.com", null);
//
//        Mockito.when(service.updateUser(newUser)).thenThrow(UserDoesNotExistsException.class);
//
//        String requestBody = objectMapper.writeValueAsString(newUser);
//
//        mockMvc.perform(put(requestURI)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
//                .andExpect(status().isNotFound())
//                .andDo(print());
//    }
//
//    @Test
//    public void shouldReturnBadRequestWhenUpdatingUserWithInvalidData() throws Exception {
//        Long userId = 123L;
//        String requestURI = END_POINT_PATH + "/" + userId;
//
//        UserContact newUser = new UserContact(userId, "Kamil", "9645330312", "kamil@gmail.com", null);
//
//        String requestBody = objectMapper.writeValueAsString(newUser);
//
//        mockMvc.perform(put(requestURI)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
//                .andExpect(status().isBadRequest())
//                .andDo(print());
//    }
//
//    @Test
//    public void shouldReturnOkWhenUpdatingExistingUser() throws Exception, UserDoesNotExistsException {
//        Long userId = 123L;
//        String requestURI = END_POINT_PATH + "/" + userId;
//
//        UserContact user = new UserContact(userId, "Kamil", "9645330312", "kamil@gmail.com", null);
//
//        Mockito.when(service.updateUser(user)).thenReturn(user);
//
//        String requestBody = objectMapper.writeValueAsString(user);
//
//        mockMvc.perform(put(requestURI)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.emailId").value("kamil@gmail.com"))
//                .andDo(print());
//    }

    @Test
    public void shouldReturnNotFoundWhenDeletingNonExistingUser() throws Exception, UserDoesNotExistsException {
        Long userId = 123L;
        String requestURI = END_POINT_PATH + "/delete/" + userId;

        Mockito.doThrow(UserDoesNotExistsException.class).when(service).deleteUser(userId);

        mockMvc.perform(delete(requestURI))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void shouldReturnNoContentWhenDeletingExistingUser() throws Exception, UserDoesNotExistsException {
        Long userId = 123L;
        String requestURI = END_POINT_PATH + "/delete/" + userId;

        Mockito.doNothing().when(service).deleteUser(userId);

        mockMvc.perform(delete(requestURI))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
