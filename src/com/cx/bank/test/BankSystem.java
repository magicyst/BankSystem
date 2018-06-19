package com.cx.bank.test;

import com.cx.bank.manager.BankService;
import com.cx.bank.manager.ManagerImpl;
import com.cx.bank.util.AccountOverDrawnException;
import com.cx.bank.util.InvalidDepositException;

import java.util.Scanner;

/**
 * 银行系统入口:
 * Created by Administrator on 2018/6/12.
 * @version bank1.1
 */
public class BankSystem {

    public static void main(String[] args) {

        /**
         * 1.2版本改动:为存款和取款添加两个异常类
         *      1.InvalidDepositException(存款为负异常)
         *      2.AccountOverDrawnException(取款金额大于余额异常)
         *
         * 银行系统入口：
         *      1.键盘输入模拟用户操作
         *      2.while循环实现系统开始状态
         *      3.System.exit()模拟系统关闭
         *
         *
         * 系统界面：
         * =========================
         *      1.查询
         *      2.存款
         *      3.取款
         *      0.退出
         * =========================
         */

        //1.键盘输入模拟用户操作
        Scanner input = new Scanner(System.in);

        //业务层接口
        BankService bankservice = new ManagerImpl();

        //临时记录
        double temp = 0;

        //2.while循环实现系统开始状态,接受命令
        while(true){

            //用户提示
            printHelpInfo();

            //结束用户指令
            System.out.print("请输入命令[1,2,3,0]: ");

            //指令
            int cmd = input.nextInt();



            //判断cmd的指令含义
            switch (cmd){

                //查询
                case 1:
                    System.out.println("1.查询");
                    if((temp = bankservice.inquiry()) == -1)
                        System.out.println("\t余额异常");
                    else
                        System.out.println("\t所剩余额为:"+temp);
                    continue;

                //存款
                case 2:
                    System.out.println("2.存款");
                    System.out.print("\t输入存储的金额:");
                    double money = input.nextDouble();
                    try {

                        //如果存款为负，抛出异常
                        if(money < 0)
                            throw new InvalidDepositException("存款为负数");

                        //如果存款无异常
                        bankservice.dePosit(money);
                        System.out.println("\t存储金额" + money + "  成功");
                        System.out.println("\t您当前余额为:"+bankservice.inquiry());
                        continue;

                    } catch (InvalidDepositException e) {
                        System.out.println("\t"+e);
                        System.out.println("\t操作异常，请重新操作");
                        continue;
                    }

                //取款
                case 3:
                    System.out.println("3.取款");
                    System.out.print("\t请输入取款金额:");
                    double get_money = input.nextDouble();
                    try {

                        //如果取出的金额大于余额或者小于零，抛出异常
                        if(get_money > bankservice.inquiry() || get_money < 0)
                            throw new AccountOverDrawnException("余额不足"+get_money+"元");

                        //如果取款金额无异常
                        bankservice.withdrawals(get_money);
                        System.out.println("\t您当前余额为:"+bankservice.inquiry());
                        continue;
                    } catch (AccountOverDrawnException e) {

                        System.out.println("\t"+e);
                        System.out.println("\t操作异常，请重新操作");
                        continue;
                    }

                //3.System.exit()模拟系统关闭
                case 0:
                    try{

                        System.out.println("0.退出");
                        System.out.println("\t系统已退出");
                        //关闭流，关闭系统
                        input.close();
                        bankservice.exitSystem();
                    }catch(Exception e){

                        //如果退出异常打印异常信息
                        e.printStackTrace();
                    }
                //如果非法指令
                default:
                    System.out.println("输入指令非法请重新输入");
                    continue;


            }


        }


    }

    private static void printHelpInfo() {

        /** 系统界面：
         * =========================
         *      1.查询
         *      2.存款
         *      3.取款
         *      0.退出
         * =========================
         */
        System.out.println("=========<bank1.2>=========");
        System.out.println("          1.查询");
        System.out.println("          2.存款");
        System.out.println("          3.取款");
        System.out.println("          0.退出");
        System.out.println("===========================");
    }

}
