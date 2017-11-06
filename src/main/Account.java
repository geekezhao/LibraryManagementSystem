package main;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

/**
 * Created by Zhao on 2017/5/30.
 */
public class Account {
    public static void CreateAccount(String[] content) throws SQLException {
        try {
            PreparedStatement stat = main.Connect.connection.prepareStatement(main.SQL.ACCOUNT_CREATE);//connect mysql

            stat.setString(1, content[0]);
            stat.setString(2, content[1]);
            stat.setString(3, content[2]);
            stat.setString(4, content[3]);
            stat.setString(5, content[4]);

            stat.executeUpdate();//update sql schema

            PreparedStatement stat2 = main.Connect.connection.prepareStatement(main.SQL.CONSULT_ACCOUNT);//connect mysql
            stat2.setString(1, content[0]);
            stat2.setString(2, content[3]);

            ResultSet res2 = stat2.executeQuery();//update sql schema
            if (res2.next()) {
                System.out.println("你的账户编号是" + res2.getString("usr_num"));
            }
        }catch (SQLException p0){
            System.out.println("出错了！是不是重复注册了");
        }
    }
    //OK
    public static void UpdateAccount(Integer choice) throws SQLException, IOException, ParseException {

        if(choice.equals(1)||choice.equals(2)||choice.equals(3)||choice.equals(4)) {

            Scanner usr_id = new Scanner(System.in);
            System.out.println("请在接下来一行输入您的账户id：");
            String user_id = usr_id.nextLine();
//            System.out.println(user_id);
            System.out.println("请在接下来一行输入您的账户密码：");
            String password = usr_id.nextLine();
//            System.out.println(password);

            if(CheckAccount(user_id,password) == 0)
                return;
            else;

            PreparedStatement prepStat = main.Connect.connection.prepareStatement(main.SQL.SELECT_ACCOUNT);
            prepStat.setString(1, user_id);

            ResultSet rs = prepStat.executeQuery();


            if (rs.next()) {
                try {

                    String usr_name = rs.getString("usr_name");
                    String address = rs.getString("address");
                    String mail = rs.getString("email");
                    String phone = rs.getString("phone");

                    switch(choice){
                        case 1:
                            System.out.println(usr_name);
                            //System.out.println("jinle diyige switch");
                            break;
                        case 2:
                            System.out.println(address);
                            break;
                        case 3:
                            System.out.println(mail);
                            break;
                        case 4:
                            System.out.println(phone);
                            break;
                    }

                    System.out.println("之前的你是这样的，你真的要改吗？\n1. 我一定要改 2. 算了");
                    Scanner new_choice = new Scanner(System.in);
                    String now_choice = new_choice.nextLine();

                    if(now_choice.equals("2")) {
                        choice = 0;
                    }
                    else;

                    switch (choice) {

                        case 1:
                            Scanner s1 = new Scanner(System.in);
                            System.out.println("请在接下来一行输入您更新的个人信息：");
                            String User_name = s1.nextLine();
                            PreparedStatement prepname = main.Connect.connection.prepareStatement(main.SQL.UPDATE_ACCOUNT_NAME);
                            prepname.setString(1, User_name);
                            prepname.setString(2, user_id);
                            prepname.executeUpdate();

                            break;

                        case 2:
                            Scanner s2 = new Scanner(System.in);
                            System.out.println("请在接下来一行输入您更新的个人信息：");
                            String User_add = s2.nextLine();
                            PreparedStatement prepadd = main.Connect.connection.prepareStatement(main.SQL.UPDATE_ACCOUNT_ADDRESS);
                            prepadd.setString(1, User_add);
                            prepadd.setString(2, user_id);
                            prepadd.executeUpdate();

                            break;

                        case 3:
                            Scanner s3 = new Scanner(System.in);
                            System.out.println("请在接下来一行输入您更新的个人信息：");
                            String User_email = s3.nextLine();
                            PreparedStatement prepemail = main.Connect.connection.prepareStatement(main.SQL.UPDATE_ACCOUNT_EMAIL);
                            prepemail.setString(1, User_email);
                            prepemail.setString(2, user_id);
                            prepemail.executeUpdate();

                            break;

                        case 4:
                            Scanner s4 = new Scanner(System.in);
                            System.out.println("请在接下来一行输入您更新的个人信息：");
                            String User_phone = s4.nextLine();
                            PreparedStatement prepphone = main.Connect.connection.prepareStatement(main.SQL.UPDATE_ACCOUNT_PHONE);
                            prepphone.setString(1, User_phone);
                            prepphone.setString(2, user_id);
                            prepphone.executeUpdate();

                            break;

                        default:
                            break;
                    }
                } catch (SQLException p1) {
                    //System.out.print(p1);
                    System.out.println("输入出错啦！");
                }
            } else {
                System.out.println("Cannot find the account.");
            }
        }else{
            System.out.println("无法识别");
        }

    }

    public static int CheckAccount(String usr_num,String password) throws SQLException, ParseException {
        try {
            ResultSet res = main.Connect.DoubleInput(usr_num, password, SQL.CHECK_ACCOUNT);
            if (res.next()) {
                System.out.println("登陆成功");
                return 1;
            }
            else{
                System.out.println("登录失败");
                return 0;
            }
        }catch (Exception p0){
//            System.out.print(p0);
            System.out.println("出错了！");
            return 0;
        }
    }
}
