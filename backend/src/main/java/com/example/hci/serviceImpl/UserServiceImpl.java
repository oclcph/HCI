package com.example.hci.serviceImpl;

import com.example.hci.exception.HCIException;
import com.example.hci.po.User;
import com.example.hci.repository.UserRepository;
import com.example.hci.service.UserService;
import com.example.hci.util.SecurityUtil;
import com.example.hci.util.TokenUtil;
import com.example.hci.vo.UserVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    SecurityUtil securityUtil;


    @Override
    public Boolean register(UserVO userVO) {
        User user = userRepository.findByPhone(userVO.getPhone());
        if (user != null) {
            throw HCIException.phoneAlreadyExists();
        }
        User newUser = userVO.toPO();
        userRepository.save(newUser);
        return true;
    }

    @Override
    public String login(String phone, String password) {
        User user = userRepository.findByPhoneAndPassword(phone, password);
        if (user == null) {
            throw HCIException.phoneOrPasswordError();
        }
        return tokenUtil.getToken(user);
    }

    @Override
    public UserVO getInformation() {
        User user = securityUtil.getCurrentUser();
        return user.toVO();
    }
    @Override
    public Boolean addCorrectRate(Double rate) {
        if (rate < 0 || rate > 1) {
            throw HCIException.DIYException("正确率应在0-1之间");
        }
        User user = securityUtil.getCurrentUser();
        user.getCorrectRate().add(rate);
        userRepository.save(user);
        return true;
    }
}