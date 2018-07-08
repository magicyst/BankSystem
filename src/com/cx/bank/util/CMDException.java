package com.cx.bank.util;

/**
 * Created by Administrator on 2018/6/22.
 */
public class CMDException extends NumberFormatException{


    public CMDException(String message) {
        super(message);
    }


    public CMDException() {
        super("指令异常");
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
