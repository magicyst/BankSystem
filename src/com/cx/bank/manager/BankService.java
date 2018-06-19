package com.cx.bank.manager;

/**
 * Created by Administrator on 2018/6/13.
 */


public interface BankService {

    /**
     * 系统业务：
     *      1.银行金额的查询
     *      2.用户存款
     *      3.用户取款
     *      4.退出
     */

    //1.银行金额的查询
    public double seleMoney();

    //2.用户存款
    public double saveMoney(double money);

    //3.用户取款
    public double getMoney(double money);

    //4.退出
    public void exitSystem();
}
