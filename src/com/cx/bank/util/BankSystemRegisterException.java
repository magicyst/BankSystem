package com.cx.bank.util;

/**
 * Created by Administrator on 2018/6/22.
 */
public class BankSystemRegisterException extends Exception {

    public BankSystemRegisterException() {
    }

    //构造注入异常信息
    public BankSystemRegisterException(String message) {
        super(message);
    }

    //重写方法
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
