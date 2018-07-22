package com.cx.bank.dao;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @version bank1.5
 * @author Administrator yst
 * 实现dao层的装配工厂
 * Created by Administrator on 2018/7/14.
 */
public class BankDaoFactory {

    /**
     * 私有构造方法
     */
    private BankDaoFactory(){}

    /**
     * 实现dao的自动装配
     * @return 返回dao实现
     */
    public synchronized BankDaoInterface getDao() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {

        //读取装配文件template.properties
        Properties profile = new Properties();

        FileReader in = new FileReader(ProperFilePo.Template_url);
        profile.load(in);

        String bankdao = profile.getProperty("dao_class");

        return (BankDaoInterface)Class.forName(bankdao).newInstance();

    }

    /**
     * 单例对外接口，静态内部类
     * @return 返回dao工厂单例
     */
    public static final BankDaoFactory getInstance(){

        return BankDaoFactoryHolder.bankDaoFactory_instance;
    }

    /**
     * 工厂单例持有者
     * 解决多线程环境下的单例
     */
    private static class BankDaoFactoryHolder{

        public static BankDaoFactory bankDaoFactory_instance = new BankDaoFactory();

    }
}
