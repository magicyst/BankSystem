package com.cx.bank.test;

import com.cx.bank.manager.BankService;
import com.cx.bank.manager.ManagerImpl;
import com.cx.bank.model.UserBean;
import com.cx.bank.util.*;

import java.io.IOException;
import java.util.Scanner;

/**
 * 银行系统入口:
 * Created by Administrator on 2018/6/12.
 * @version bank1.5
 */
public class BankSystem {

    public static void main(String[] args) {

        /**
         * 1.3版本改动:
         *
         * 银行系统入口：
         *      1.键盘输入模拟用户操作
         *          1.1 登录
         *      2.while循环实现系统开始状态
         *      3.System.exit()模拟系统关闭
         *
         *
         * 系统界面：
         * =========================
         *      1.查询
         *      2.存款
         *      3.取款
         *      4.注册
         *      5.登录
         *      6.转账
         *      0.退出
         * =========================
         */

        //1.键盘输入模拟用户操作
        Scanner input = new Scanner(System.in);

        //业务层依赖
        //BankService bankservice = ManagerImpl.getInstance();
        BankService bankservice = new ManagerImpl();

        //1.1 登录
        UserBean user = new UserBean();

        //临时记录
        //double temp;
        int cmd;

        //2.while循环实现系统开始状态,接受用户命令
        while(true){

            //用户提示
            printHelpInfo(bankservice.getLoginUserName());

            //指令
            try {
                cmd = Integer.valueOf(input.next());

            } catch (NumberFormatException e) {
                System.out.println("指令异常:"+e.getMessage()+"   请重新输入");
                continue;
            }


            //判断cmd的指令含义
            switch (cmd){

                //1.查询
                case 1:

                    System.out.println("1.查 询");
                    try {

                        System.out.println("\t所剩余额为:"+bankservice.inquiry());
                    } catch (BankSystemLoginException e) {

                        System.out.println("\t"+e.getMessage());
                    }
                    continue;

                //2.存款
                case 2:
                    System.out.println("2.存款");
                    System.out.print("\t输入存储的金额:");
                    double money = input.nextDouble();
                    try {

                        //如果存款无异常
                        bankservice.dePosit(money);
                        System.out.println("\t存储金额" + money + "  成功");
                        System.out.println("\t您当前余额为:"+bankservice.inquiry());
                        continue;

                    } catch (BankSystemLoginException e){

                        //输出异常
                        System.out.println("\t"+e.getMessage());
                        continue;

                    } catch(InvalidDepositException e) {

                        System.out.println("\t"+e.getMessage());
                        System.out.println("\t操作异常，请重新操作");
                        continue;
                    }

                //3.取款
                case 3:
                    System.out.println("3.取款");
                    System.out.print("\t请输入取款金额:");
                    double get_money = input.nextDouble();
                    try {

                        //取款
                        bankservice.withdrawals(get_money);
                        System.out.println("\t您当前余额为:"+bankservice.inquiry());
                        continue;
                    } catch(BankSystemLoginException e){

                        System.out.println("\t"+e.getMessage());
                        continue;

                    } catch(AccountOverDrawnException e) {

                        System.out.println("\t"+e.getMessage());
                        continue;

                    }

                //4.注册
                case 4:
                    while(true){

                        System.out.println("4.注册:");
                        System.out.print("username:");
                        //清空缓冲区(清空上一次的回车换行符)
                        //input.nextLine();
                        String username = input.next();
                        System.out.print("password:");
                        String password = input.next();

                        try{

                            bankservice.register(new UserBean(username,password));
                            break;

                        }catch(BankSystemRegisterException e){

                            System.out.println("operation exception:"+e.getMessage());
                            continue;
                        }catch(IOException e){

                            System.out.println("store exception");
                            continue;
                        }

                    }

                    System.out.println("register successful");
                    continue;

                //5.登录
                case 5:
                    while(true){

                        //输入用户名与密码
                        System.out.print("username:");
                        user.setUsername(input.next());
                        System.out.print("password:");
                        user.setPassword(input.next());

                        try {

                            //登录
                            bankservice.login(user);

                            //如果登录无异常这跳出循环
                            System.out.println("登录成功!");
                            break;
                        } catch (BankSystemLoginException e) {

                            //如果登录异常，则打印异常信息
                            System.out.println("登录异常:"+e.getMessage());
                            continue;
                        }
                    }
                    continue;

                //6.转账
                case 6:

                    System.out.println("6.转账:");
                    System.out.print("\t请输入对方用户名:");
                    String username = input.next();
                    System.out.print("\t请输入转账金额:");
                    double transfer_money = Double.valueOf(input.next());

                    try {

                        //转账
                        bankservice.transfer(username,transfer_money);
                        System.out.println("转账成功！余额为"+bankservice.inquiry());
                        continue;

                    } catch (BankSystemLoginException e) {

                        //转账用户不存在
                        System.out.println(e.getMessage());
                        continue;
                    } catch (TransferException e) {

                        //转账异常
                        System.out.println(e.getMessage());
                        continue;
                    }

                case 7:

                    bankservice.logOut();
                    continue;

                    //0.System.exit()模拟系统关闭
                case 0:
                    try{

                        System.out.println("0.退出");
                        System.out.println("\t系统已退出");
                        //关闭流，关闭系统
                        input.close();

                        //退出系统
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


    /**
     * 银行系统界面
     */
    private static void printHelpInfo(String username) {

        /** 系统界面：
         * =========================
         *      1.查询
         *      2.存款
         *      3.取款
         *      4.注册
         *      5.登录
         *      6.转账
         *      0.退出
         * =========================
         */
        String lab = "欢迎,"+username+"用户";
        if(username == null) {
            lab = "未登录";
            System.out.println("=========<bank1.3>=========");
            System.out.println("* "+lab+" *");
            System.out.println("          4.注册");
            System.out.println("          5.登录");
            System.out.println("===========================");
            System.out.print("请输入命令[4,5]: ");
            return;
        }
        System.out.println("=========<bank1.3>=========");
        System.out.println("* "+lab+" *");
        System.out.println("          1.查询");
        System.out.println("          2.存款");
        System.out.println("          3.取款");
        System.out.println("          4.注册");
        System.out.println("          5.登录");
        System.out.println("          6.转账");
        System.out.println("          0.退出");
        System.out.println("===========================");
        System.out.print("请输入命令[1,2,3,4,5,6,0]: ");
    }

}
