package com.cx.bank.util;

/**
 * Created by Administrator on 2018/7/4.
 */
public class TransferException extends Exception {

    public TransferException() {
        super("转账异常!");
    }

    public TransferException(String message) {
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
