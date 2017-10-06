package com.example.demo.controller;

import com.example.demo.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(value="/users")
public class UserController {
    private static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());

    @RequestMapping(value="/", method=RequestMethod.GET)
    public List<User> getUserList() {
        return new ArrayList<>(users.values()) ;
    }
}
