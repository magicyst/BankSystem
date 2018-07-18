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
     * 实现dao的自动装配
     * @return 返回dao实现
     */
    public static BankDaoInterface getDao() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {

        //读取装配文件template.properties
        Properties profile = new Properties();

        FileReader in = new FileReader(ProperFilePo.Template_url);
        profile.load(in);

        String bankdao = profile.getProperty("dao_class");

        return (BankDaoInterface)Class.forName(bankdao).newInstance();
    }
}
