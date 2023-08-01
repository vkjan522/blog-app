package com.vikash.blog.services;

import com.vikash.blog.payloads.UserDTO;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO,Integer userID);
    UserDTO getUserById(Integer userID);
    List<UserDTO> getAllUser();
    void deleteUserById(Integer userID);
}
