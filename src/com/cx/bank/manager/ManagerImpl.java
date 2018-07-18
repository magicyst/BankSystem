package com.cx.bank.manager;

import com.cx.bank.dao.BankDaoFactory;
import com.cx.bank.dao.BankDaoInterface;
import com.cx.bank.model.UserBean;
import com.cx.bank.util.*;

import java.io.IOException;

/**
 * @version 1.5
 * @author yst
 * 业务层实现
 * Created by Administrator on 2018/6/12.
 */
public class ManagerImpl implements BankService{

    /**
     * 系统业务：
     *      1.银行金额的查询
     *      2.用户存款
     *      3.用户取款
     *      4.退出
     *      5.注册
     *      6.登录
     */
    private BankDaoInterface bankdao;//dao层的依赖
    private static ManagerImpl managerinstance = null;   //单例
    //private String login_username = null;                //已经登录的用户

    private ManagerImpl(){

        try {
            bankdao = BankDaoFactory.getDao();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 单例对外接口
     * @return 单例对外接口
     */
    public static ManagerImpl getInstance(){

        //如果单例为空，创建单例对象
        if(managerinstance == null)
            managerinstance = new ManagerImpl();

        return managerinstance;
    }

    /**
     * 1.查询
     * @return 如果余额小于0返回-1,否则返回存款余额
     */
    public double inquiry() throws BankSystemLoginException{

        return bankdao.inquire();

    }

    /**
     * 2.存款
     * @param money 存款金额
     * @exception InvalidDepositException
     */
    public void dePosit(double money) throws InvalidDepositException, BankSystemLoginException {

       bankdao.dePosit(money);

    }

    /**
     * 3.取款
     * @exception AccountOverDrawnException
     */
    public void withdrawals(double money) throws AccountOverDrawnException, BankSystemLoginException {

        bankdao.withdrawals(money);

    }

    /**
     * 4.退出系统
     */
    public void exitSystem() {

        //this.login_username = null;

        //注销
        bankdao.logOut();
        System.exit(1);

    }

    /**
     * 5.注册
     * @throws IOException
     * @throws BankSystemRegisterException
     */
    @Override
    public void register(UserBean user) throws IOException, BankSystemRegisterException {

        bankdao.register(user);

        //this.login_username = bankdao.getLoginUserName();
    }

    /**
     *6. 登录
     */
    @Override
    public void login(UserBean user) throws BankSystemLoginException{

        bankdao.login(user);

        //this.login_username = bankdao.getLoginUserName();
    }

    /**
     * 7.转账
     * @param username 目标用户名
     * @param money    转账金额
     * @throws BankSystemLoginException
     * @throws TransferException
     */
    @Override
    public void transfer(String username,double money) throws BankSystemLoginException, TransferException {

        bankdao.transfer(username,money);
    }

    /**
     * 获取已登录用户名
     * @return 返回登录用户名
     */
    @Override
    public String getLoginUserName() {

        return bankdao.getLoginUserName();
    }

    @Override
    public void logOut() {

        bankdao.logOut();
    }
}
