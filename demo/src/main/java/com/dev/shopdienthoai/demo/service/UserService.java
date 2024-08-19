package com.dev.shopdienthoai.demo.service;

import com.dev.shopdienthoai.demo.domain.User;
import com.dev.shopdienthoai.demo.domain.dto.Meta;
import com.dev.shopdienthoai.demo.domain.dto.ResultPaginationDTO;
import com.dev.shopdienthoai.demo.domain.dto.UserDTOCreate;
import com.dev.shopdienthoai.demo.domain.dto.UserDTOUpdate;
import com.dev.shopdienthoai.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.dto.RestUserDTO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User saveUser(User user){
         return userRepository.save(user);
    }
    public boolean checkEmail(String email){
        return userRepository.existsByEmail(email);
    }
    public void deleteUser(Long id){
        this.userRepository.deleteById(id);
    }
    public ResultPaginationDTO getAllUsers(Specification<User> specification,Pageable pageable){
        Page<User> pageUser = userRepository.findAll(specification,pageable);
        ResultPaginationDTO rs=new ResultPaginationDTO();
        Meta meta=new Meta();
        //trang hiện tại
        meta.setPage(pageable.getPageNumber()+1);
        //tổng số phần tử trang hiện tại
        meta.setTotal(pageUser.getTotalElements());
        //tổng số trang
        meta.setPages(pageUser.getTotalPages());
        //tổng số phần tử
        meta.setPageSize(pageable.getPageSize());

        rs.setMeta(meta);
        List<RestUserDTO> listUser = pageUser.getContent()
                .stream().map(item -> new RestUserDTO(
                        item.getId(),
                        item.getEmail(),
                        item.getName(),
                        item.getGender(),
                        item.getAddress(),
                        item.getAge(),
                        item.getUpdatedAt(),
                        item.getCreatedAt()))
                .collect(Collectors.toList());

        rs.setResult(listUser);
        return rs;
    }
    public User getUserById(Long id){
        //optional giúp check xem user có null hay không
        Optional<User> user = this.userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        }
        return null;
    }
    public User handleUpdateUser(User reqUser) {
        User currentUser = this.getUserById(reqUser.getId());
        if (currentUser != null) {
            currentUser.setAddress(reqUser.getAddress());
            currentUser.setGender(reqUser.getGender());
            currentUser.setAge(reqUser.getAge());
            currentUser.setName(reqUser.getName());
            // update
            currentUser = this.userRepository.save(currentUser);
        }
        return currentUser;
    }
    public User findUserByEmail(String email){
        return this.userRepository.findByEmail(email);
    }

    public UserDTOCreate convertToResCreateUserDTO(User user) {
        UserDTOCreate res = new UserDTOCreate();
        res.setId(user.getId());
        res.setEmail(user.getEmail());
        res.setName(user.getName());
        res.setAge(user.getAge());
        res.setCreatedAt(user.getCreatedAt());
        res.setGender(user.getGender());
        res.setAddress(user.getAddress());
        return res;
    }
    public UserDTOUpdate convertToResUpdateUserDTO(User user) {
        UserDTOUpdate res = new UserDTOUpdate();
        res.setId(user.getId());
        res.setName(user.getName());
        res.setAge(user.getAge());
        res.setUpdatedAt(user.getUpdatedAt());
        res.setGender(user.getGender());
        res.setAddress(user.getAddress());
        return res;
    }
    public RestUserDTO convertToResUserDTO(User user) {
        RestUserDTO res = new RestUserDTO();
        res.setId(user.getId());
        res.setEmail(user.getEmail());
        res.setName(user.getName());
        res.setAge(user.getAge());
        res.setUpdatedAt(user.getUpdatedAt());
        res.setCreatedAt(user.getCreatedAt());
        res.setGender(user.getGender());
        res.setAddress(user.getAddress());
        return res;
    }
}
