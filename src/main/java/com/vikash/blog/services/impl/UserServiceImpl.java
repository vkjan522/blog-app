package com.vikash.blog.services.impl;

import com.vikash.blog.entities.User;
import com.vikash.blog.exceptions.ResourceNotFoundException;
import com.vikash.blog.payloads.UserDTO;
import com.vikash.blog.repositories.UserRepository;
import com.vikash.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    private User dtoToUser(UserDTO userDTO){
        User user = this.modelMapper.map(userDTO, User.class);
        /*
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAbout(userDTO.getAbout());
        */
        return user;
    }
    public UserDTO userToDto(User user){

        UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);
       /*
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setAbout(user.getAbout());
        */

        return userDTO;
    }


    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = this.dtoToUser(userDTO);
        User savedUser =  this.userRepository.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Integer userID) {
        User user = this.userRepository.findById(userID).orElseThrow(() ->
                new ResourceNotFoundException("User","id",userID));
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAbout(userDTO.getAbout());
        User updatedUser =  this.userRepository.save(user);
        UserDTO userDto = this.userToDto(updatedUser);
        return  userDto;
    }

    @Override
    public UserDTO getUserById(Integer userID) {
        User user= this.userRepository.findById(userID).orElseThrow(()->
                new ResourceNotFoundException("User","id",userID));
        UserDTO userDTO =  this.userToDto(user);
        return userDTO;
    }

    @Override
    public List<UserDTO> getAllUser() {
        List<User> users =  this.userRepository.findAll();
        List<UserDTO> userDTOS = users.stream().map(user ->
                this.userToDto(user)).collect(Collectors.toList());
        return userDTOS;
    }

    @Override
    public void deleteUserById(Integer userID) {
        User user= this.userRepository.findById(userID).orElseThrow(()->
                new ResourceNotFoundException("User","id",userID));
        this.userRepository.delete(user);
    }
}
