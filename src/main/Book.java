package main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Scanner;

/**
 * Created by Zhao on 2017/5/30.
 */
public class Book {
    public static void SelectBooksByBookName(String book_name) throws NumberFormatException, SQLException, ParseException {
        ResultSet rs = main.Connect.SimpleInput('%'+book_name+'%', SQL.SELECT_BOOKS_BY_BOOKNAME);
        String ISBN = null;
        String nbook_name;
        String publish_house;
        String writer;
        String category;

        int signal = 1;

        while (rs.next()) {
            signal = 0;
            ISBN = rs.getString("ISBN");
            nbook_name = rs.getString("book_name");
            publish_house = rs.getString("publish_house");
            writer = rs.getString("writer");
            category = rs.getString("category");

            System.out.println("Book Information:");
            System.out.println("ISBN: " + ISBN + "\nbook name: " + nbook_name + "\npublish house: " + publish_house + "\nwriter: " + writer + "\ncategory:" + category);

            ResultSet result = main.Connect.SimpleInput(ISBN, SQL.SELECT_DUPLICATE);
            while(result.next()) {
                //ResultSet result = duplicate.executeQuery();

                int total = result.getInt("total");
                System.out.println("total:" + total);

                int dup = result.getInt("duplicate_num");
                String state = result.getString("state");

                System.out.println("duplicate " + dup + " , state:" + state);
                //for(int i = 1;i <= total;i++) {
                while (result.next()) {
                    int ndup = result.getInt("duplicate_num");
                    String nstate = result.getString("state");

                    System.out.println("duplicate " + ndup + " , state:" + nstate);
                }

                Scanner ptr = new Scanner(System.in);
                System.out.println("需要借阅吗？\n1.是    2.否");

                int flag = 1;
                int usr_choice = 0;
                while(flag == 1) {
                    try {
                        flag = 0;
                        usr_choice = Integer.parseInt(ptr.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input,try again");
                        flag = 1;
                    }
                }

                switch (usr_choice){
                    case 1:
                        Scanner ptr1 = new Scanner(System.in);
                        System.out.println("请在接下来一行输入您的账户id：");
                        String user_id = ptr1.nextLine();
                        System.out.println("请在接下来一行输入您的账户密码：");
                        String password = ptr1.nextLine();

                        if(main.Account.CheckAccount(user_id,password) == 0)
                            return;
                        else;

                        Book.BorrowBooks(ISBN,user_id);
                        break;
                    //break;

                    case 2:
                        //break;
                        break;

                    default:
                        //break;
                        System.out.println("重新输入，请重新开始操作");
                        continue;
                }
            }
        }
        if(signal == 1){
            System.out.println("The book does not exist.");
        }

    }
    //OK
    public static void BorrowBooks(String ISBN, String usr_num) throws SQLException, ParseException{
        ResultSet rs = main.Connect.SimpleInput(usr_num, SQL.SELECT_TOTAL);
        //System.out.println(rs);

        if (rs.next()){
            int book_total = rs.getInt("total");

            ResultSet r2s = main.Connect.SimpleInput(usr_num, SQL.SELECT_BORROW);
            double new_fine = 0;
            double fine = 0;
            while(r2s.next()){
                java.sql.Date day = r2s.getDate("borrow_day");
                java.util.Date date=new java.util.Date();

                int gap = (int) ((date.getTime()-day.getTime())/(24*60*60*1000));

                new_fine =  0.1*gap - 2;

                new_fine = (new_fine > 0)?(new_fine):0;

                fine = fine + new_fine;
            }

            if (book_total>=10){
                System.out.println("借阅失败，您已达到借书量上限。");
            }else if(fine != 0){
                System.out.println("请先缴纳罚款。");
            }else {
                int a = 0;
                int b = 0;

                try {
                    a = main.Connect.SimpleVariable(ISBN, SQL.BORROW_BOOK);//update sql schema

                    b = main.Connect.DoubleVariable(usr_num, ISBN, SQL.INSERT_BOOK);//update sql schema

                } catch (SQLException e) {
                    int nonuse = main.Connect.SimpleVariable(ISBN, SQL.RECOVER_DUPLICATE);
                    a = 100;
                    b = 100;
                    System.out.println("重复借阅");
                }


                if (a == 1 && b == 1) {
                    //if(res != null){
                    System.out.println("Borrow successfully.");
                } else if (a != 100 && b != 100) {
                    int c = 0;
                    int d = 0;
                    try {
                        c = main.Connect.DoubleVariable(usr_num, ISBN, SQL.DELETE_BORROW_RECORD);

                        d = main.Connect.DoubleVariable(usr_num, ISBN, SQL.INSERT_WAITING);

                        if (d != 0 && c != 0) {
                            System.out.println("You are in waiting list now.");
                        } else {
                            System.out.println("Failed.");
                        }
                    } catch (SQLException p6) {
//                        System.out.println(p6);
                        System.out.println("重复进入等待序列");
                    }
                }
            }


        }else{
            System.out.println("Failed to find the account.");
        }
    }
    //OK
    public static void ReturnBooks(String account_id) throws ParseException, SQLException {
        Scanner usr_id = new Scanner(System.in);

        System.out.println("请在接下来一行输入您的账户密码：");
        String password = usr_id.nextLine();

        if(main.Account.CheckAccount(account_id,password) == 0)
            return;
        else;


        ResultSet res = main.Connect.SimpleInput(account_id, SQL.SELECT_ACCOUNT);

        if(res.next()) {
            ResultSet r1s = main.Connect.SimpleInput(account_id, SQL.SELECT_BORROW);

            int flag8 = 0;
            while (r1s.next()) {
                flag8 = 1;
                String ISBN = r1s.getString("ISBN");

                ResultSet return2 = main.Connect.SimpleInput(ISBN, SQL.SELECT_BOOK_BY_ISBN);

                if (return2.next()) {
                    String book_name = return2.getString("book_name");
                    System.out.println("ISBN: " + ISBN + " , 书名: " + book_name);
                }

                int flag = 1;
                int choice = 0;
                while (flag == 1) {
                    try {
                        flag = 0;
                        Scanner in = new Scanner(System.in);
                        System.out.println("要还这本书吗？\n1. 还 2. 下一本");
                        choice = Integer.parseInt(in.nextLine());
                    } catch (Exception e) {
                        flag = 1;
                        System.out.println("Invalid Input,try again");
                    }
                }

                switch(choice){
                    case 1:
                        int result = main.Connect.DoubleVariable(account_id, ISBN, SQL.DELETE_BORROW_RECORD);

                        int result2 = main.Connect.SimpleVariable(ISBN, SQL.RECOVER_DUPLICATE);

                        int result3 = main.Connect.SimpleVariable(ISBN, SQL.SATISFIED);

                        if (result3 != 0) {
                            int nonuse = main.Connect.SimpleVariable(ISBN, SQL.CHANGE_BOOK_STATE_AFTER_SATISFIED);
                        }

                        if (result != 0 && result2 != 0 || result3 != 0) {
                            System.out.println("Return successfully.");
                        } else {
                            System.out.println("Haven't brought this book.");
                        }
                        break;

                    case 2:
                        break;

                    default:
                        System.out.println("不明操作，下一本");
                        break;
                }
            }

            if(flag8 == 0){
                System.out.println("不存在借阅记录");
            }

        }else{
            System.out.println("账号不存在");
        }
    }
    //OK
    public static int InsertBooksInNeed(String[] content) throws SQLException {
        try {
            Object[] data = new Object[content.length + 1];
            //data[0] = Long.parseLong(content[0]);
            data[0] = content[0];
            data[1] = content[1];
            data[2] = content[2];
            data[3] = "notyet";
            Statement stat = main.Connect.connection.createStatement();
            String sql = String.format(main.SQL.INSERT_BOOK_IN_NEED, data);
//            System.out.println("insert sql: " + sql);
            if (stat.executeUpdate(sql) > 0)
                System.out.println("Insert successfully.");
            else
                System.out.println("Insert failed.");
        } catch (SQLException p4) {
            System.out.println("插入书本出错");
            return 1;
        }
        return 0;
    }
    //OK
    public void InsertBooks(String[] content) throws SQLException {
        Object[] data = new Object[content.length];
        data[0] = Long.parseLong(content[0]);
        data[1] = content[1];
        data[2] = content[2];
        data[3] = content[3];
        data[4] = content[4];
        Statement stat = main.Connect.connection.createStatement();
        String sql = String.format(main.SQL.INSERT_BOOK, data);
        System.out.println("insert sql: " + sql);
        if(stat.executeUpdate(sql)>0)
            System.out.println("Insert successfully.");
        else
            System.out.println("Insert failed.");
    }
    //好像没什么用
}
