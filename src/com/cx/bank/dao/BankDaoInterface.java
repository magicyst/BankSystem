package com.cx.bank.dao;

import com.cx.bank.model.UserBean;
import com.cx.bank.util.*;

import java.io.IOException;

/**
 * @version bank1.3
 * Created by Administrator on 2018/6/20.
 */
public interface BankDaoInterface {
    /**
     * bank1.3 dao 接口：
     *      1.注册
     *      2.登录
     *      3.转账
     *      4.查询
     *      5.取款
     *      6.存款
     *      7.转账
     *
     */


    /**
     * 1.注册
     * @param user
     * @return
     */
    public void register(UserBean user) throws IOException,BankSystemRegisterException;

    /**
     * 2.登录
     * @param user
     */
    public void login(UserBean user) throws BankSystemLoginException;

    /**
     * 4.余额查询
     * @return 余额
     */
    public double inquire() throws BankSystemLoginException;

    /**
     * 5.用户取款
     * @param money
     * @throws AccountOverDrawnException
     */
    public void withdrawals(double money) throws AccountOverDrawnException, BankSystemLoginException;

    /**
     * 6.用户存款
     * @param money
     * @throws InvalidDepositException
     */
    public void dePosit(double money) throws InvalidDepositException, BankSystemLoginException;

    /**
     * 7.转账
     * @param username
     * @param money
     */
    public void transfer(String username,double money) throws BankSystemLoginException, TransferException;

    public String getLoginUserName();
}
