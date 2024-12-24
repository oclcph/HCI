package com.example.hci.controller;

import com.example.hci.service.UserService;
import com.example.hci.vo.ResultVO;
import com.example.hci.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResultVO<Boolean> register(@RequestBody UserVO userVO){
        return ResultVO.buildSuccess(userService.register(userVO));
    }

    @PostMapping("/login")
    public ResultVO<String> login(@RequestParam("phone") String phone, @RequestParam("password") String password){
        return ResultVO.buildSuccess(userService.login(phone, password));
    }
    @PostMapping("/correctRate")
    public ResultVO<Boolean> addCorrectRate( @RequestParam("rate") Double rate){
        return ResultVO.buildSuccess(userService.addCorrectRate(rate));
    }

    @GetMapping
    public ResultVO<UserVO> getInformation(){
        return ResultVO.buildSuccess(userService.getInformation());
    }

//    @PostMapping
//    public ResultVO<Boolean> updateInformation(@RequestBody UserVO userVO){
//        return ResultVO.buildSuccess(userService.updateInformation(userVO));
//    }
}