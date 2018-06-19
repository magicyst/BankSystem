package com.cx.bank.manager;

import com.cx.bank.util.AccountOverDrawnException;
import com.cx.bank.util.InvalidDepositException;

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
    public double inquiry();

    //2.用户存款
    public void dePosit(double money) throws InvalidDepositException;

    //3.用户取款
    public void withdrawals(double money) throws AccountOverDrawnException;

    //4.退出
    public void exitSystem();
}
