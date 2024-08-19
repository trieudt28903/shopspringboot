package com.dev.shopdienthoai.demo.controller;


import com.dev.shopdienthoai.demo.domain.User;
import com.dev.shopdienthoai.demo.domain.dto.ResultPaginationDTO;
import com.dev.shopdienthoai.demo.domain.dto.UserDTOCreate;
import com.dev.shopdienthoai.demo.domain.dto.UserDTOUpdate;
import com.dev.shopdienthoai.demo.until.annotation.ApiMessage;
import com.dev.shopdienthoai.demo.until.error.IdInvalidException;
import com.dev.shopdienthoai.demo.service.UserService;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.dto.RestUserDTO;

import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/users")
    @ApiMessage("fetch all users")
    public ResponseEntity<ResultPaginationDTO> getAllUser(@Filter Specification<User> spec,Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers(spec,pageable));
    }
    @PostMapping("/users")
    @ApiMessage("Create a new user")
    public ResponseEntity<UserDTOCreate> createNewUser(@Valid @RequestBody User postManUser)
            throws IdInvalidException {
        boolean isEmailExist = this.userService.checkEmail(postManUser.getEmail());
        if (isEmailExist) {
            throw new IdInvalidException(
                    "Email " + postManUser.getEmail() + " đã tồn tại, vui lòng sử dụng email khác.");
        }
        String hashPassword = this.passwordEncoder.encode(postManUser.getPassword());
        postManUser.setPassword(hashPassword);
        User ericUser = this.userService.saveUser(postManUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.convertToResCreateUserDTO(ericUser));
    }
    @DeleteMapping("/users/{id}")
    @ApiMessage("Delete success")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws IdInvalidException {
        User checkId = this.userService.getUserById(id);
        if (checkId == null){
            throw new IdInvalidException("ID "+id+" không tồn tại");
        }
        this.userService.deleteUser(id);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/users")
    @ApiMessage("Update a user")
    public ResponseEntity<UserDTOUpdate> updateUser(@RequestBody User user) throws IdInvalidException {
        User ericUser = this.userService.handleUpdateUser(user);
        if (ericUser == null) {
            throw new IdInvalidException("User với id = " + user.getId() + " không tồn tại");
        }
        return ResponseEntity.ok(this.userService.convertToResUpdateUserDTO(ericUser));
    }
    @GetMapping("users/{id}")
    @ApiMessage("Get user by id")
    public ResponseEntity<RestUserDTO> getUserById(@PathVariable Long id) throws IdInvalidException {
        User user = this.userService.getUserById(id);
        if (user == null) {
            throw new IdInvalidException("id : "+id+" không tồn tại");
        }
        return ResponseEntity.ok(this.userService.convertToResUserDTO(user));
    }

}
