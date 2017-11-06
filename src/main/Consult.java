package main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Scanner;

import main.*;
/**
 * Created by Zhao on 2017/5/30.
 */
public class Consult {
    public static void ConsultFine(String usr_num) throws SQLException, ParseException {
        Scanner usr_id = new Scanner(System.in);

        System.out.println("请在接下来一行输入您的账户密码：");
        String password = usr_id.nextLine();

        if(main.Account.CheckAccount(usr_num,password) == 0)
            return;
        else;

        ResultSet rs = main.Connect.SimpleInput(usr_num, SQL.SELECT_TOTAL);
        //System.out.println(rs);

        if (rs.next()){
            ResultSet res = main.Connect.SimpleInput(usr_num, SQL.SELECT_BORROW);

            int flag9 = 1;
            double fine = 0;
            while(res.next()){
                flag9 = 0;
                java.sql.Date day = res.getDate("borrow_day");
                java.util.Date date=new java.util.Date();

                int gap = (int) ((date.getTime()-day.getTime())/(24*60*60*1000));

                double new_fine = 0.1*gap - 2;
                new_fine = (new_fine>=0)?(new_fine):0;

                fine = fine + new_fine;
            }

            if(flag9 == 1){
                System.out.println("你甚至没有借书！");
            }else {

                System.out.println((fine > 0)?("你的罚款是：" + fine):("没有罚款"));
            }
        }else{
            System.out.println("Failed to find the account.");
        }


    }
    //OK
    public static void DeleteReader(String usr_num) throws SQLException, ParseException {
        try {
            Scanner usr_id = new Scanner(System.in);

            System.out.println("请在接下来一行输入您的账户密码：");
            String password = usr_id.nextLine();

            if(main.Account.CheckAccount(usr_num,password) == 0)
                return;
            else;

            int flag = main.Connect.SimpleVariable(usr_num, SQL.DELETE_READER);
            if (flag > 0)
                System.out.println("Delete successfully.");
            else
                System.out.println("Cannot find the account.");
        }catch (Exception p6){
            System.out.println("请您检查是否有未还书籍或欠款");
        }
    }
    //OK
    public static void ConsultRecords(String usr_num) throws SQLException, ParseException {

        Scanner usr_id = new Scanner(System.in);

        System.out.println("请在接下来一行输入您的账户密码：");
        String password = usr_id.nextLine();

        if(main.Account.CheckAccount(usr_num,password) == 0)
            return;
        else;

        ResultSet rs = main.Connect.SimpleInput(usr_num, SQL.SELECT_TOTAL);
        //System.out.println(rs);

        if (rs.next()){
            ResultSet r2s = main.Connect.SimpleInput(usr_num, SQL.SELECT_BORROW);

            String ISBN = null;

            java.sql.Date day;
            Calendar return_day = Calendar.getInstance();

            int flag = 0;
            while (r2s.next()) {
                flag = 1;
                ISBN = r2s.getString("ISBN");
                day = r2s.getDate("borrow_day");
                return_day.setTime(day);


                int year=return_day.get(Calendar.YEAR);
                int month=return_day.get(Calendar.MONTH)+1;
                int date=return_day.get(Calendar.DATE);

                return_day.add(Calendar.DATE, 30);
                year=return_day.get(Calendar.YEAR);
                month=return_day.get(Calendar.MONTH)+1;
                date=return_day.get(Calendar.DATE);

                ResultSet res = main.Connect.SimpleInput(ISBN, SQL.SELECT_BOOK_BY_ISBN);

                String book_name;

                Calendar c = Calendar.getInstance();

                if (res.next()) {
                    book_name = res.getString("book_name");

                    System.out.println("ISBN: " + ISBN + " , book name: " + book_name + "\nborrow day: " + day + " , you should return it back in: " + year+"-"+month+"-"+date + "\n");

                    if(return_day.get(Calendar.DAY_OF_YEAR) < c.get(Calendar.DAY_OF_YEAR))
                        return;

                        System.out.println("尚未逾期，需要借阅吗？");
                        Scanner ptr = new Scanner(System.in);
                        System.out.println("需要借阅吗？\n1.是    2.否");

                        int flag1 = 1;
                        int usr_choice = 0;
                        while (flag1 == 1) {
                            try {
                                flag1 = 0;
                                usr_choice = Integer.parseInt(ptr.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input,try again");
                                flag1 = 1;
                            }
                        }

                        switch (usr_choice) {
                            case 1:
                                try {
                                    ResultSet resultsets = main.Connect.SimpleInput(ISBN, SQL.SELECT_WAITING_BY_ISBN);

                                    if(resultsets.next()){
                                        System.out.println("有预约请求无法续借");
                                    }else{
                                        int result = main.Connect.DoubleVariable(ISBN, usr_num, SQL.UPDATE_BORROW_TIMES);
                                        if(result != 1){
                                            System.out.println("到达借阅次数上限");
                                            return;
                                        }

                                        int result1 = main.Connect.DoubleVariable(ISBN, usr_num, SQL.UPDATE_BORROW_TIMES);
                                        if(result1 == 1){
                                            System.out.println("续借成功");
                                            return_day.setTime(day);
                                            return_day.add(Calendar.DATE, 30);
                                            year=return_day.get(Calendar.YEAR);
                                            month=return_day.get(Calendar.MONTH)+1;
                                            date=return_day.get(Calendar.DATE);
                                            System.out.println("ISBN: " + ISBN + " , book name: " + book_name + "\nborrow day: " + day + " , you should return it back in: " + year+"-"+month+"-"+date + "\n");
                                        }

                                    }

                                } catch (SQLException  e) {
                                    //e.printStackTrace();
                                }
                            case 2:
                                break;
                        }
                    }

            }

            if(flag == 0) {
                System.out.println("该账号不存在借阅记录");
            }
        }else{
            System.out.println("Failed to find the account.");
        }
    }
    //OK
    public static void ConsultWaiting(String usr_num) throws SQLException, ParseException {
        Scanner usr_id = new Scanner(System.in);

        System.out.println("请在接下来一行输入您的账户密码：");
        String password = usr_id.nextLine();

        if(main.Account.CheckAccount(usr_num,password) == 0)
            return;
        else;

        ResultSet rs = main.Connect.SimpleInput(usr_num, SQL.SELECT_WAITING);

        int flag = 1;
        while(rs.next()){
            flag = 0;
            String ISBN = rs.getString("ISBN");
            String state = rs.getString("state");

            ResultSet return2 = main.Connect.SimpleInput(ISBN, SQL.SELECT_WAITING_BY_ISBN);

            if(return2.next()){
                String book_name = return2.getString("book_name");
                System.out.println("ISBN: " + ISBN + " , 书名: " + book_name + " , 状态: " + state);
            }

            if(state.equals("Satisfied")) {
                System.out.println("预约已满足，需要借阅吗？");
                Scanner ptr = new Scanner(System.in);
                System.out.println("需要借阅吗？\n1.是    2.否");

                int flag1 = 1;
                int usr_choice = 0;
                while (flag1 == 1) {
                    try {
                        flag1 = 0;
                        usr_choice = Integer.parseInt(ptr.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input,try again");
                        flag1 = 1;
                    }
                }

                switch (usr_choice) {
                    case 1:
                        try {
                            main.Book.BorrowBooks(ISBN,usr_num);

                            int nonuse = main.Connect.DoubleVariable(usr_num, ISBN, SQL.DELETE_WAITING);
                        } catch (SQLException | ParseException e) {
                            //e.printStackTrace();
                        }
                    case 2:
                        try {
                            int result1 = main.Connect.DoubleVariable(usr_num, ISBN, SQL.DELETE_WAITING);

                            int result2 = main.Connect.SimpleVariable(ISBN, SQL.RECOVER_DUPLICATE_IN_NEED);

                            int result3 = main.Connect.SimpleVariable(ISBN, SQL.SATISFIED);

                            if (result3 != 0) {
                                int nonuse = main.Connect.SimpleVariable(ISBN, SQL.CHANGE_BOOK_STATE_AFTER_SATISFIED);
                            }

                            if(result1 != 0 && result2 != 0){
                                System.out.println("预约已删除");
                            }
                        } catch (SQLException e) {
//                            e.printStackTrace();
                        }
                        break;
                }
            }
        }

        if(flag == 1)
            System.out.println("你不在等待序列中");

    }
}
