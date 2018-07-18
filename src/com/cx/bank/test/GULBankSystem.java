package com.cx.bank.test;

import com.cx.bank.manager.BankService;
import com.cx.bank.manager.ManagerImpl;
import com.cx.bank.model.UserBean;
import com.cx.bank.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * @version 1.5
 * @author yst
 * bank系统的gui界面
 * Created by Administrator on 2018/7/17.
 */
public class GULBankSystem {

    public static void main(String[] args) {
        new BankWindow();
    }
}


class BankWindow extends Frame {

    BankService service = ManagerImpl.getInstance();
    Image image;//logo
    private JFrame manager_jf;//主面板
    private JFrame login_jf;

    private JLabel login_user = new JLabel("未登录");

    JTextField username_ipt = new JTextField(10);//用户名输入
    JPasswordField pass_ipt = new JPasswordField(10);//密码输入

    private JButton login = new JButton("登录");
    private JButton register = new JButton("注册");
    private JButton inquiry = new JButton("查询");
    private JButton dePosit = new JButton("存款");
    private JButton withdrawals = new JButton("取款");
    private JButton transfer = new JButton("转账");
    private JButton logout = new JButton("注销");
    private JButton exit = new JButton("退出");


    {
        try {
            image = ImageIO.read(BankWindow.class.getResource("/filedata/logo.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BankWindow() {

        initButtons();
        //frameInit();//初始化

        loginFrame();//登录界面显示

        //managerFrame();

        //manager_jf.setVisible(true);
        login_jf.setVisible(true);


    }

    public void frameInit() {


    }

    public void loginFrame() {

        pass_ipt.setText("");
        login_jf = new JFrame();

        //登录页面设置
        login_jf.setTitle("登录系统");
        login_jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        login_jf.setLocationRelativeTo(null);
        login_jf.setIconImage(image);
        login_jf.setSize(400, 400);
        login_jf.setForeground(Color.LIGHT_GRAY);


        //第0个JPanel,页面提示
        JPanel panel0 = new JPanel();
        panel0.add(new JLabel("银行登录系统"));

        // 第 1 个 JPanel, 使用默认的浮动布局
        JPanel panel1 = new JPanel();
        panel1.add(new JLabel("用户名"));
        panel1.add(username_ipt);

        // 第 2 个 JPanel, 使用默认的浮动布局
        JPanel panel2 = new JPanel();
        panel2.add(new JLabel("密   码"));
        panel2.add(pass_ipt);

        // 第 3 个 JPanel, 使用浮动布局, 并且容器内组件居中显示
        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel3.add(login);
        panel3.add(register);

        // 创建一个垂直盒子容器, 把上面 3 个 JPanel 串起来作为内容面板添加到窗口
        Box vBox = Box.createVerticalBox();
        vBox.add(panel0);
        vBox.add(panel1);
        vBox.add(panel2);
        vBox.add(panel3);

        login_jf.setContentPane(vBox);

        login_jf.pack();//紧缩一起
        //jf.setVisible(true);
    }

    private void managerFrame() {

        manager_jf = new JFrame();
        //系统界面设置
        manager_jf.setTitle("银行系统");
        manager_jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        manager_jf.setLocationRelativeTo(null);
        manager_jf.setIconImage(image);
        manager_jf.setSize(300, 400);
        manager_jf.setForeground(Color.cyan);

        //登录用户
        JPanel jp0 = new JPanel();
        String username = "";
        if ((username = service.getLoginUserName()) != null)
            login_user.setText("欢迎，" + username + " 用户");
        else
            login_user.setText("请先登录或注册");

        login_user.setFont(new Font("楷体", 1, 15));
        login_user.setForeground(Color.RED);
        jp0.add(login_user);

        //第1个功能:查询
        JPanel jp1 = new JPanel();
        jp1.add(inquiry);

        //第2功能:存款
        JPanel jp2 = new JPanel();
        jp2.add(dePosit);

        //第3个功能:取款
        JPanel jp3 = new JPanel();
        jp3.add(withdrawals);

        //第4个功能:转账
        JPanel jp4 = new JPanel();
        jp4.add(transfer);

        //第5个功能:注销
        JPanel jp5 = new JPanel();
        jp5.add(logout);

        //第6个功能:退出
        JPanel jp6 = new JPanel();
        jp6.add(exit);

        Box vbox = Box.createVerticalBox();
        vbox.add(jp0);
        vbox.add(jp1);
        vbox.add(jp2);
        vbox.add(jp3);
        vbox.add(jp4);
        vbox.add(jp5);
        vbox.add(jp6);

        manager_jf.setContentPane(vbox);

        //manager_jf.pack();

    }

    private void initButtons() {

        //登录
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //用户名密码
                String username;
                char[] password;

                if ((username = username_ipt.getText()).equals("") || (password = pass_ipt.getPassword()).length == 0) {
                    //用户名密码为空
                    JOptionPane.showMessageDialog(null, "密码或用户名不能为空", "系统提示", JOptionPane.WARNING_MESSAGE);
                } else {

                    //创建用户
                    UserBean user = new UserBean(username, new String(password));
                    try {
                        //登录
                        service.login(user);
                        //无异常则登录成功
                        JOptionPane.showMessageDialog(null, "登录成功", "系统提示", JOptionPane.INFORMATION_MESSAGE);
                        login_jf.setVisible(false);
                        managerFrame();
                        manager_jf.setVisible(true);
                    } catch (BankSystemLoginException e1) {

                        //登录异常:提示
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "登录异常", JOptionPane.ERROR_MESSAGE);
                        pass_ipt.setText("");
                    }

                }
            }
        });

        //注册
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //用户名密码
                String username;
                char[] password;

                if ((username = username_ipt.getText()).equals("") || (password = pass_ipt.getPassword()).length == 0) {
                    //用户名密码为空
                    JOptionPane.showMessageDialog(null, "密码或用户名不能为空", "系统提示", JOptionPane.WARNING_MESSAGE);
                } else {

                    //创建用户
                    UserBean user = new UserBean(username, new String(password));
                    try {
                        //注册
                        service.register(user);

                        //无异常提示注册成功
                        JOptionPane.showMessageDialog(null, "注册成功", "系统提示", JOptionPane.INFORMATION_MESSAGE);
                        login_jf.setVisible(false);

                        managerFrame();
                        manager_jf.setVisible(true);
                    } catch (IOException e1) {

                        //系统io异常
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "服务器异常", JOptionPane.ERROR_MESSAGE);
                    } catch (BankSystemRegisterException e1) {

                        //注册异常
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "注册异常", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        //查询
        inquiry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    //查询
                    double money = service.inquiry();
                    JOptionPane.showMessageDialog(null, money, "查询结果", JOptionPane.INFORMATION_MESSAGE);
                } catch (BankSystemLoginException e1) {

                    //查询异常
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "查询异常", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //取款
        withdrawals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String money;
                while (true) {

                    money = (String) JOptionPane.showInputDialog(null, "请输入取出金额：\n", "取  款", JOptionPane.PLAIN_MESSAGE, null, null, "请输入数字");

                    //如果用户输入无异常
                    try {

                        Double get_money = Double.valueOf(money);
                        service.withdrawals(get_money);

                        //如果取款无异常
                        JOptionPane.showMessageDialog(null, "取款成功,余额为" + service.inquiry() + "元", "取款提示", JOptionPane.INFORMATION_MESSAGE);
                        break;

                    } catch (NumberFormatException e1) {

                        //非法金额提示
                        JOptionPane.showMessageDialog(null, "金额应该为数字", "系统提示", JOptionPane.WARNING_MESSAGE);
                    } catch (AccountOverDrawnException e1) {

                        //取款金额异常提示
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "取款提示", JOptionPane.ERROR_MESSAGE);
                    } catch (BankSystemLoginException e1) {

                        //未登录异常提示
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "取款提示", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    } catch (NullPointerException e1) {

                        //取消输入
                        break;
                    }

                }
            }
        });

        //存款
        dePosit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String money;

                while (true) {

                    money = (String) JOptionPane.showInputDialog(null, "请输入取出金额：\n", "存  款", JOptionPane.PLAIN_MESSAGE, null, null, "请输入数字");

                    //如果用户输入无异常
                    try {

                        Double in_money = Double.valueOf(money);
                        service.dePosit(in_money);

                        //如果存款无异常
                        JOptionPane.showMessageDialog(null, "存款成功,余额为" + service.inquiry() + "元", "取款提示", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    } catch (NumberFormatException e1) {

                        //非法金额提示
                        JOptionPane.showMessageDialog(null, "金额应该为数字", "系统提示", JOptionPane.WARNING_MESSAGE);
                    }catch (InvalidDepositException e1) {

                        //存款金额异常提示
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "存款提示", JOptionPane.ERROR_MESSAGE);
                    } catch (BankSystemLoginException e1) {

                        //未登录异常提示
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "存款提示", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    } catch (NullPointerException e1) {

                        //用户取消输入
                        break;
                    }

                }
            }
        });

        //转账
        transfer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String transfer_user;
                String transfer_money;

                while (true){

                    transfer_user = (String) JOptionPane.showInputDialog(null, "请输入转账用户：\n", "转 账", JOptionPane.PLAIN_MESSAGE, null, null, "用户名");

                    transfer_money = (String) JOptionPane.showInputDialog(null, "请输入转账金额：\n", "转 账", JOptionPane.PLAIN_MESSAGE, null, null, "请输入数字");

                    try {

                        //转账
                        service.transfer(transfer_user,Double.valueOf(transfer_money));

                        //如果存款无异常
                        JOptionPane.showMessageDialog(null, "转账成功,余额为" + service.inquiry() + "元", "转账提示", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }catch(NumberFormatException e1){

                        //非法金额提示
                        JOptionPane.showMessageDialog(null, "金额应该为数字", "系统提示", JOptionPane.WARNING_MESSAGE);

                    }catch (BankSystemLoginException e1) {

                        //未登录异常
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "存款提示", JOptionPane.ERROR_MESSAGE);

                    } catch (TransferException e1) {

                        //转账异常
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "存款提示", JOptionPane.ERROR_MESSAGE);
                    } catch (NullPointerException e1){

                        //用户取消输入
                        break;
                    }
                }

            }
        });

        //注销
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //注销用户
                service.logOut();

                //系统页面隐藏
                manager_jf.setVisible(false);

                //提示
                JOptionPane.showMessageDialog(null, "注销成功", "系统提示", JOptionPane.INFORMATION_MESSAGE);

                //显示登录页面
                loginFrame();
                login_jf.setVisible(true);
            }
        });

        //退出
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                service.exitSystem();
            }
        });
    }

}
