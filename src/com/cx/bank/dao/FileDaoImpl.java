package com.cx.bank.dao;

import com.cx.bank.model.UserBean;
import com.cx.bank.util.*;

import java.io.IOException;
import java.io.File;

/**
 * Created by Administrator on 2018/6/20.
 *
 * 文件存储类:
 *      1、一个用户文件，所有的信息都存储在一个文件夹里面
 *      2、用户的文件标识为:用户名.properties
 */
public class FileDaoImpl implements BankDaoInterface{
    /**
     * 文件dao:
     *      1.注册
     *      2.登录
     */

    //该文件dao维持的一个用户Properties文件对象
    private static ProperFilePo proread = null;
    //文件存储路径
    public  static final String FILE_URL = "filedata\\register\\";



    /**
     * 1.注册
     *      1.1 判断注册用户是否存在
     * @param user
     * @return 如果注册用户存在返回false，否则返回true
     */
    @Override
    public void register(UserBean user) throws BankSystemRegisterException,IOException {

        //新建一个注册用户文件对象
        File file = new File(FILE_URL+user.getUsername()+".properties");

        //如果存在该用户文件，即该用户名已经被注册了，抛出注册异常对象(用户名存在)
        if(file.exists())
            throw new BankSystemRegisterException("the username exist");

        //创建一个默认Properties文件对象
        ProperFilePo proread_register = new ProperFilePo();

        //为文件对象设置信息(用户名，和密码，账户余额默认为模板余额:0.0元)
        proread_register.setPassword(MD5Util.getMD5(user.getPassword()));
        proread_register.setUsername(user.getUsername());

        //如果注册在该语句之前没有抛出异常，这让改对象维持改注册用户的Properties文件对象
        proread = proread_register;

        //该用户Properties对象持久化
        proread.store();

    }

    /**
     * 2.注册
     * @param user
     */
    @Override
    public void login(UserBean user) throws BankSystemLoginException{

        //创建一个默认用户文件对象
        ProperFilePo proread_login = new ProperFilePo();

        //通过该用户名加载该用户文件到对象
        proread_login.load(user.getUsername());

        //如果密码不正确，抛出登录异常对象
        if(!proread_login.getPassword().equals(MD5Util.getMD5(user.getPassword())))
            throw new BankSystemLoginException("密码不正确!");

        //如果登录没有异常,这可以维持改用户Properties文件对象
        proread = proread_login;
    }

    @Override
    public double inquire() throws BankSystemLoginException{

        if(proread != null){

            //重新更新文件对象
            proread.load(proread.getUsername());
            return proread.getMoney();
        }
        else
            throw new BankSystemLoginException("请注册或者登录!");

    }

    /**
     * 取款
     * @param money
     * @throws AccountOverDrawnException
     * @throws BankSystemLoginException
     */
    @Override
    public void withdrawals(double money) throws AccountOverDrawnException,BankSystemLoginException{

        //如果未登录调用
        if(proread == null)
            throw new BankSystemLoginException("请注册或者登录!");

        //如果取款金额为负数
        if(money < 0)
            throw new AccountOverDrawnException("取款为负异常");

        //如果取款金额大于余额
        if(money > proread.getMoney())
            throw new AccountOverDrawnException("余额不足"+money+"元!");

        //无异常，则取款

        //重新更新文件对象，防止系统崩溃后取款信息无法返回服务器
        proread.load(proread.getUsername());

        proread.setMoney(proread.getMoney()-money);

        //对象回显到文件
        try {
            proread.store();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 存款
     * @param money
     * @throws InvalidDepositException
     * @throws BankSystemLoginException
     */
    @Override
    public void dePosit(double money) throws InvalidDepositException,BankSystemLoginException{

        if(proread == null)
            throw new BankSystemLoginException("请注册或者登录!");

        //如果存款金额为负数
        if(money < 0)
            throw new InvalidDepositException("存款为负异常");

        //重新更新文件对象，防止系统崩溃后存款信息无法返回服务器
        proread.load(proread.getUsername());

        //无异常，则存款
        proread.setMoney(proread.getMoney()+money);

        //对象回显到文件
        try {
            proread.store();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 转账
     * @param username
     * @param money
     * @throws BankSystemLoginException
     * @throws TransferException
     */
    @Override
    public void transfer(String username, double money) throws BankSystemLoginException,TransferException{

        if(proread == null)
            throw new BankSystemLoginException("请注册或者登录!");

        //转账金额大于余额
        if(money > proread.getMoney())
            throw new TransferException("余额不足!");

        //如果存款金额为负数
        if(money < 0)
            throw new TransferException("转账为负!");

        //获取转账用户信息
        ProperFilePo transfer_user = new ProperFilePo(username);

        //把钱加到目标用户对象中
        transfer_user.setMoney(transfer_user.getMoney() + money);

        //目标用户信息回显
        try {

            transfer_user.store();

            //如果转账异常，不执行用户扣钱操作
            proread.setMoney(proread.getMoney()-money);

            //客户信息回显,如果可以回显失败?(遗留问题:应该用线程的原子性锁)
            proread.store();
        } catch (IOException e) {

            //如果回显异常,抛出转账异常
            throw new TransferException();
        }
    }


    /**
     *获取登录用户
     * @return
     */
    @Override
    public String getLoginUserName() {

        if(proread != null)
            return proread.getUsername();

        return null;
    }
}
