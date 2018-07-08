package com.cx.bank.model;



/**
 * 单例类Money,模拟银行的存款类型
 * Created by Administrator on 2018/6/12.
 */
public class MoneyBean {

    private double money = 100;  //钱的数量
    private boolean safety = true; //是否安全,如果money小于0则false,否则安全

    private static MoneyBean instance= null; //单例

    private MoneyBean() {} //私有构造方法,单例模式


    /**
     * 单例获取方式
     * @return 返回Money实例
     */
    public static MoneyBean getInstance(){

        //如果已经有money实例则返回实例，否则new一个实例返回
        if(instance == null)
            return new MoneyBean();

        return instance;
    }


    /*
     * 下面是geter and seter方法
     *
     */
    public double getMoney() {
        return money;
    }


    public void setMoney(double money) {
        this.money = money;
    }


    public boolean isSafe() {

        if(this.money < 0)
            this.safety = false;

        return safety;
    }


    public void setSafety(boolean safety) {
        this.safety = safety;
    }


}
