package com.cx.bank.util;

/**
 * Created by Administrator on 2018/6/19.
 */


/**
 * 取款异常
 *      当取款金额大于余额抛出异常
 */
public class InvalidDepositException extends Exception {


    /**
     * 默认错误信息
     */
    public InvalidDepositException(){

        super("存款为负数异常");

    }

    /**
     * 错误信息注入
     * @param message 错误信息
     */
    public InvalidDepositException(String message){

       super(message);

    }

    /**
     * 重写父类方法
     *      获取错误信息
     *
     * @return
     */
    public String getMessage() {

        return super.getMessage();
    }


    /**
     * 重写父类方法
     *
     * @return
     */
    public String toString() {
        return super.toString();
    }

    /**
     * 重写父类方法
     */
    public void printStackTrace() {
        super.printStackTrace();
    }
}
