package com.application.contactmanagementsystem.service;

import com.application.contactmanagementsystem.entity.UserContact;
import com.application.contactmanagementsystem.exceptions.UserAlreadyExistsException;
import com.application.contactmanagementsystem.exceptions.UserDoesNotExistsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserContact createUser(UserContact user);
    UserContact getUserById(Long userId);
    List<UserContact> getAllUsers();
    UserContact updateUser(UserContact user);
    void deleteUser(Long userId);

}
