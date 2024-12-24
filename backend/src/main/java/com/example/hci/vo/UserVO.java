package com.example.hci.vo;


import com.example.hci.po.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserVO {

    private Integer id;

    private String name;

    private String phone;

    private String password;

    private Date createTime;

    private List<Long> favPoetry;

    private List<Double> correctRate;


    public User toPO(){

        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPhone(phone);
        user.setPassword(password);
        user.setCreateTime(createTime);
        user.setCorrectRate(correctRate);
        user.setFavPoetry(favPoetry);
        return user;

    }

}

