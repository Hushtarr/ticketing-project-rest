package com.cydeo.controller;

import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.UserDTO;
import com.cydeo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper>createUser(@RequestBody UserDTO userDTO){
        userService.save(userDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseWrapper(
                        "user created",
                        HttpStatus.CREATED
                ));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper>updateUser(@RequestBody UserDTO userDTO){
        userService.update(userDTO);
        return ResponseEntity
                .ok(new ResponseWrapper(
                        "user updated",
                        HttpStatus.OK
                ));
    }


    @GetMapping
    public ResponseEntity<ResponseWrapper>getUsers(){
        List<UserDTO>userDTOList=userService.listAllUsers();
        return ResponseEntity //
                .ok(new ResponseWrapper(    //ok-> postman toolBar status
                        "users are listed",
                        userDTOList,
                        HttpStatus.OK)); // ok-> status in the body
    }
    @GetMapping("/{username}")
    public ResponseEntity<ResponseWrapper>getUserByUserName(@PathVariable String username){
        UserDTO userDTO=userService.findByUserName(username);
        return ResponseEntity
                .ok(new ResponseWrapper(
                        "user found",
                        userDTO,
                        HttpStatus.OK));

    }


    @DeleteMapping("/{username}")
    public ResponseEntity<ResponseWrapper>deleteUser(@PathVariable String username){
        userService.delete(username);
        return ResponseEntity
                .ok(new ResponseWrapper(
                        "user deleted",
                        HttpStatus.OK
                ));
    }
}
