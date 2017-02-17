package com.kolia.controller;
 
import java.util.List;

import com.kolia.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
 
import com.kolia.model.User;

class WrapperDTO {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

@RestController
@RequestMapping("/api")
public class AuthRestController {
 
    @Autowired
    UserDao userDao;
     
    @RequestMapping(value = "/login/", method = RequestMethod.POST)
    public ResponseEntity<User> login(@RequestBody WrapperDTO dto) {
        User user = userDao.findByLogin(dto.getUsername());
        if(user == null){
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}