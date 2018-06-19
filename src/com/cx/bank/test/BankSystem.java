package com.cx.bank.test;

import com.cx.bank.manager.BankService;
import com.cx.bank.manager.ManagerImpl;

import java.util.Scanner;

/**
 * 银行系统入口:
 * Created by Administrator on 2018/6/12.
 * @version bank1.1
 */
public class BankSystem {

    public static void main(String[] args) {

        /**
         * 版本改动:实现接口编程：业务层提供BankService接口,解除控制层与层业务层具体实现的耦合性。
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
                    if((temp = bankservice.seleMoney()) == -1)
                        System.out.println("\t余额异常");
                    else
                        System.out.println("\t所剩余额为:"+temp);
                    continue;

                //存款
                case 2:
                    System.out.println("2.存款");
                    System.out.print("\t输入存储的金额:");
                    double money = input.nextDouble();
                    if((temp = bankservice.saveMoney(money)) == -1) {

                        System.out.println("\t存储的金额不正确，请重新操作");
                        continue;
                    }
                    else {
                        System.out.println("\t存储金额" + money + "  成功");
                        System.out.println("\t您当前余额为:"+bankservice.seleMoney());
                    }
                    continue;
                //取款
                case 3:
                    System.out.println("3.取款");
                    System.out.print("\t请输入取款金额:");
                    double get_money = input.nextDouble();
                    if((temp = bankservice.getMoney(get_money)) == -1){

                        System.out.println("\t无法取得当前金额:" + get_money);
                        System.out.println("\t您当前余额为:"+bankservice.seleMoney());
                    }
                    else{
                        System.out.println("\t您当前余额为:"+bankservice.seleMoney());
                    }
                    continue;

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
        System.out.println("系统操作指令:bank1.2");
        System.out.println("=========================");
        System.out.println("      1.查询");
        System.out.println("      2.存款");
        System.out.println("      3.取款");
        System.out.println("      0.退出");
        System.out.println("=========================");
    }

}
