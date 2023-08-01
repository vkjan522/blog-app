package com.vikash.blog.controllers;

import com.vikash.blog.payloads.ApiResponse;
import com.vikash.blog.payloads.UserDTO;
import com.vikash.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    //POST - Create User
    @PostMapping("/create/")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
       UserDTO userDTO1 =  this.userService.createUser(userDTO);
       return new ResponseEntity<>(userDTO1, HttpStatus.CREATED);
    }

    //PUT - update User

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable("userId") Integer uid){
        UserDTO updatedUser = this.userService.updateUser(userDTO, uid);
        return ResponseEntity.ok(updatedUser);
    }
    //DELETE - delete user

    @DeleteMapping("/delete/{userID}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userID") Integer uid){
        this.userService.deleteUserById(uid);
        return new ResponseEntity(new ApiResponse("User Deleted Successfully",true),HttpStatus.OK);
    }
    //GET - Single User get
    @GetMapping("/getOneGet/{userID}")
    public ResponseEntity<UserDTO> singleUserGet(@PathVariable("userID") Integer uid){
       UserDTO userDTO = this.userService.getUserById(uid);
       return ResponseEntity.ok(userDTO);
    }

    @GetMapping("{userID}")
    public ResponseEntity<UserDTO> singleUser(@RequestParam("userID") Integer uid){
        UserDTO userDTO = this.userService.getUserById(uid);
        return ResponseEntity.ok(userDTO);
    }
    //GET - All User get

    @GetMapping("/all/")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> listUserDTO = this.userService.getAllUser();
        return ResponseEntity.ok(listUserDTO);
    }
}
