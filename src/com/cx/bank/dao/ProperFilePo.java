package com.cx.bank.dao;

import com.cx.bank.util.BankSystemLoginException;

import java.io.*;
import java.util.Properties;

/**
 * Created by Administrator on 2018/6/22.
 *
 * 这是一个文件持久化文件对象及按用户名为关键字的Properties对象(Po)
 *
 *
 */
public class ProperFilePo {

    //用户信息文件的储存格式
    private String username;    //用户名
    private String password;    //密码
    private Double money;       //余额

    private Properties profile; //Properties文件存取对象


    //初始读取模板Properties文件
    {
        Reader read = null;
        String filename = null;
        try{
            profile = new Properties();
            filename = "filedata\\template.properties";
            read = new FileReader(filename);
            profile.load(read);
        } catch(FileNotFoundException e){

            System.out.println("初始化文件资源异常!");
        } catch(IOException e){

            System.out.println(e);
            try {
                read.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }


    /**
     * 默认初始化template.properties文件
     */
    public ProperFilePo(){

        FileToObj();

    }

    /**
     * 加载指定用户文件读取到改对象中
     * @param username
     * @throws BankSystemLoginException
     */
    public ProperFilePo(String username) throws BankSystemLoginException{

        this.load(username);
    }

    /**
     * 文件信息转化为该对象
     */
    private void FileToObj() {

        //把文件里面的信息set入该持久化对象中
        this.username = this.profile.getProperty("username");
        this.password = this.profile.getProperty("password");
        this.money = Double.valueOf(this.profile.getProperty("money"));
    }

    /**
     * 加载username为文件名的Properties文件
     * @param username 用户名
     * @throws BankSystemLoginException
     */
    public void load(String username) throws BankSystemLoginException {

        //创建一个Properties对象
        Properties profile_temp = new Properties();

        //Properties文件路径
        String filename = FileDaoImpl.FILE_URL+username+".properties";

        //加载的流
        Reader read = null;

        try {
            read = new FileReader(filename);

            //加载该文件
            profile_temp.load(read);

        } catch (FileNotFoundException e) {

            //如果捕获FileNotFoundException，则表示改用户没有注册
            throw new BankSystemLoginException("该用户没有注册");

        } catch (IOException e) {

            //加载异常
            e.printStackTrace();
            try {
                read.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        //如果加载的用户文件存在，为profile属性赋值
        this.profile = profile_temp;

        //文件信息写入本对象属性
        FileToObj();
    }

    /**
     * 文件持久化对象写入文件
     * @throws IOException
     */
    public void store() throws IOException {

        //将该持久化实体对象写入文件对象中
        setToProperties();

        //写入的改用户文件
        String out_dir = FileDaoImpl.FILE_URL+this.username+".properties";
        File out_file = new File(out_dir);
        out_file.createNewFile();

        //字符写入流
        Writer writer = new FileWriter(out_dir);

        //文件写入
        this.profile.store(writer,this.username+"  register");

        //关闭流
        writer.close();
    }


    /**
     * 实体类信息写入持久化对象
     */
    private void setToProperties(){

        this.profile.setProperty("username",this.username);
        this.profile.setProperty("password",this.password);
        this.profile.setProperty("money",this.money.toString());

    }


    //geter and seter
    public String getUsername(){

        return this.username;
    }

    public String getPassword(){

        return this.password;
    }

    public void setUsername(String username){

        this.username = username;
    }

    public void setPassword(String password){

        this.password = password;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
