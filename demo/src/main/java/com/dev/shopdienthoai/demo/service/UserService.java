package com.dev.shopdienthoai.demo.service;

import com.dev.shopdienthoai.demo.domain.User;
import com.dev.shopdienthoai.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<User> getAllUsers(){
        return this.userRepository.findAll();
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
}
