package com.cx.bank.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/7/3.
 */
public class BankConfigure {

    private Dao dao = new Dao(); //dao配置

    private final static String path = "bankconfig.xml"; //文件位置

    /**
     * 加载bankconfig.xml文件
     */
    public void init(){

        //获取xml解析器
        SAXReader reader = new SAXReader();

        try{

            //解析文件，得到xml文档对象
            Document doc = reader.read(path);

            //xpath搜索，获取dao节点
            Element ele_dao = (Element)doc.selectSingleNode("//dao");

            //获取dao默认的数据源
            String dao_default = ele_dao.attributeValue("default");

            //存入dao对象
            dao.setDeflult(dao_default);

            //找到dao指定的默认数据源节点下的配置
            List<Element> properties = ele_dao.selectNodes("//dataSrc[@type='"+dao_default+"']/property");

            //创建一个hashmapd对象
            HashMap<String,String> property = new HashMap<String, String>();

            //遍历配置存入map
            for(Element e : properties){
                property.put(e.attributeValue("name"),e.attributeValue("value"));
            }

            //创建dao数据源对象
            Dao.DataSrc dataSrc = dao.new DataSrc();

            //获取默认数据源的实现对象
            Element dataScr_ele = (Element)ele_dao.selectSingleNode("//dataSrc[@type='"+dao_default+"']");

            //数据源的配置set
            dataSrc.setProperties(property);

            //设置dao的实现对象
            dao.setInstance(dataScr_ele.attributeValue("object"));

            //设置dao的数据源配置
            dao.setDataSrc(dataSrc);

            try {
                System.out.println(dao.getInstance().toString());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }

        }catch (DocumentException e){

        }
    }


    public static void main(String[] args) {

        new BankConfigure().init();
    }
}
