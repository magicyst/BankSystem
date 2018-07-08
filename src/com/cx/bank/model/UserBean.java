package com.cx.bank.model;

/**
 * 这是一个业务bean  UserBo
 * Created by Administrator on 2018/6/22.
 */
public class UserBean {

    //用户的基本信息
    private String username;
    private String password;



    //默认方法
    public UserBean() {}

    //个构造注入用户名，密码
    public UserBean(String username, String password) {

        this.password = password;
        this.username = username;
    }


    //geter and seter
    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


}
