package com.cx.bank.util;

/**
 * Created by Administrator on 2018/6/29.
 */
public class BankSystemLoginException extends Exception {

    public BankSystemLoginException() {
        super("登录异常!");
    }

    public BankSystemLoginException(String message) {
        super(message);
    }

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
