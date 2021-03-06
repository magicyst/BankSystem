package com.cx.bank.manager;

import com.cx.bank.model.UserBean;
import com.cx.bank.util.*;

import java.io.IOException;

/**
 * @version bank1.5
 * @author Administrator yst
 * 业务层服务接口
 * Created by Administrator on 2018/6/13.
 */


public interface BankService {

    /**
     * 系统业务：
     *      1.银行金额的查询
     *      2.用户存款
     *      3.用户取款
     *      4.退出
     *      5.注册
     *      6.登录
     *      7.转账
     */

    //1.银行金额的查询
    double inquiry() throws BankSystemLoginException;

    //2.用户存款
    void dePosit(double money) throws InvalidDepositException, BankSystemLoginException;

    //3.用户取款
    void withdrawals(double money) throws AccountOverDrawnException, BankSystemLoginException;

    //4.退出
    void exitSystem();

    //5.注册
    void register(UserBean user) throws IOException,BankSystemRegisterException;

    //6.登录
    void login(UserBean user) throws BankSystemLoginException;

    //7.转账
    void transfer(String username,double money) throws BankSystemLoginException, TransferException;

    //8.获取已登录用户名
    String getLoginUserName();

    //9.注销
    void logOut();
}
