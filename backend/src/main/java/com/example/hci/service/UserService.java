package com.example.hci.service;


import com.example.hci.vo.UserVO;

public interface UserService {
    Boolean register(UserVO userVO);

    String login(String phone,String password);

    UserVO getInformation();

    Boolean addCorrectRate(Double rate);


//    Boolean updateInformation(UserVO userVO);
}
