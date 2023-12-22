package com.application.contactmanagementsystem.service.serviceimpl;

import com.application.contactmanagementsystem.entity.UserContact;
import com.application.contactmanagementsystem.exceptions.UserAlreadyExistsException;
import com.application.contactmanagementsystem.exceptions.UserDoesNotExistsException;
import com.application.contactmanagementsystem.repository.AddressRepository;
import com.application.contactmanagementsystem.repository.UserRepository;
import com.application.contactmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }


    /**
     * service method to create new user with unique contact number
     * @param user
     * @return
     * @throws UserAlreadyExistsException
     */
    @Override
    public UserContact createUser(UserContact user) throws UserAlreadyExistsException {
        try{
            if(!userRepository.existsByContactNo(user.getContactNo())){
                addressRepository.save(user.getAddress());
               return userRepository.save(user);
            }
            else {
                throw new UserAlreadyExistsException("The user with contact number already exists");
            }
        }catch (UserAlreadyExistsException e){
            throw new UserAlreadyExistsException("The user with contact number already exists");
        }
    }

    /**
     * Service method to get user by id
     * @param userId
     * @return
     * @throws UserDoesNotExistsException
     */
    @Override
    public UserContact getUserById(Long userId) throws UserDoesNotExistsException {
        try{
            Optional<UserContact> optionalUser = userRepository.findById(userId);
            if(optionalUser.isPresent()){
                return optionalUser.get();
            }
            else {
                throw new UserDoesNotExistsException("The user with given id does not exists");
            }
        }catch (UserDoesNotExistsException e){
            throw new UserDoesNotExistsException("The user with given id does not exists");
        }
    }

    /**
     * method to get all users
     * @return
     */
    @Override
    public List<UserContact> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * method to update the user
     * @param user
     * @return
     * @throws UserDoesNotExistsException
     */
    @Override
    public UserContact updateUser(UserContact user) throws UserDoesNotExistsException {
        try{
            Optional<UserContact> optionalUser = userRepository.findById(user.getUserId());
            if(optionalUser.isPresent()){
                user.setUserId(optionalUser.get().getUserId());
                return userRepository.save(user);
            }
            else{
                throw new UserDoesNotExistsException("The user with given id does not exists");
            }
        }catch (UserDoesNotExistsException e){
            throw new UserDoesNotExistsException("The user with given id does not exists");
        }
    }

    /**
     * method to delete the user
     * @param userId
     * @throws UserDoesNotExistsException
     */
    @Override
    public void deleteUser(Long userId) throws UserDoesNotExistsException {
        try{
            Optional<UserContact> optionalUser = userRepository.findById(userId);
            if(optionalUser.isPresent()){
                UserContact user = optionalUser.get();
                userRepository.delete(user);
                addressRepository.delete(user.getAddress());
            }
            else{
                throw new UserDoesNotExistsException("The user with given id does not exists");
            }
        }catch (UserDoesNotExistsException e){
            throw new UserDoesNotExistsException("The user with given id does not exists");
        }

    }
}
