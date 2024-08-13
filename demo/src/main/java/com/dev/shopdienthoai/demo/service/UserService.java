package com.dev.shopdienthoai.demo.service;

import com.dev.shopdienthoai.demo.domain.User;
import com.dev.shopdienthoai.demo.domain.dto.Meta;
import com.dev.shopdienthoai.demo.domain.dto.ResultPaginationDTO;
import com.dev.shopdienthoai.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User saveUser(User user){
         return userRepository.save(user);
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
        rs.setResult(pageUser.getContent());
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
    public User updateUser(User user){
       User user1=this.userRepository.getById(user.getId());
       if(user1!=null){
           user1.setPassword(user.getPassword());
           user1.setName(user.getName());
           user1.setEmail(user.getEmail());

           return this.userRepository.save(user1);
       }
       return null;
    }
    public User findUserByEmail(String email){
        return this.userRepository.findByEmail(email);
    }
}
