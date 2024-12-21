package com.example.hci.exception;

public class HCIException extends RuntimeException {
    public HCIException(String message) {
        super(message);
    }
    public String getMessage(){
        return super.getMessage();
    }

    public static HCIException phoneAlreadyExists(){
        return new HCIException("手机号已经存在!");
    }

    public static HCIException notLogin(){
        return new HCIException("未登录!");
    }

    public static HCIException phoneOrPasswordError(){
        return new HCIException("手机号或密码错误!");
    }

    public static HCIException DIYException(String msg) {return new HCIException(msg);}
}
