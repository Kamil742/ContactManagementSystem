package com.application.contactmanagementsystem.controller;

import com.application.contactmanagementsystem.entity.UserContact;
import com.application.contactmanagementsystem.exceptions.UserAlreadyExistsException;
import com.application.contactmanagementsystem.exceptions.UserDoesNotExistsException;
import com.application.contactmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * method to create new user
     * @param user
     * @return
     * @throws RuntimeException
     */
    @PostMapping("/createUser")
    public ResponseEntity<?> createNewUser(@RequestBody UserContact user) throws RuntimeException{
        try{
            UserContact userDetails = userService.createUser(user);
            return new ResponseEntity<UserContact>(userDetails, HttpStatus.OK);
        } catch (UserAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * method to get user by id
     * @param userId
     * @return
     */
    @GetMapping("/getUser/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId){
        try{
            UserContact user = userService.getUserById(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserDoesNotExistsException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * method to get all users
     * @return
     */
    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers(){
        List<UserContact> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * method to update the user info
     * @param user
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserContact user){
        try{
            UserContact updatedUser = userService.updateUser(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);

        } catch (UserDoesNotExistsException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * method to delete the user info
     * @param userId
     * @return
     */
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
        try{
           userService.deleteUser(userId);
           return new ResponseEntity<>("UserContact deleted successfully", HttpStatus.OK);
        } catch (UserDoesNotExistsException e) {
            throw new RuntimeException(e);
        }
    }
}
