package main;

import java.sql.*;
import java.util.*;
import java.text.ParseException;
import java.io.*;
import java.lang.NumberFormatException;

/**
 * Created by Zhao on 2017/5/9.
 */

public class LibraryManagementSystem {
    public static void main(String[] args) throws ArrayIndexOutOfBoundsException, NumberFormatException, SQLException, ClassNotFoundException, IOException, ParseException {
            LibraryManagementSystem bankDB = new LibraryManagementSystem();
            main.Connect.connectDB();
            Scanner sc = new Scanner(System.in);
            System.out.println("欢迎使用Library Management ，您可以办理以下业务");
            System.out.println("1.账户相关     2.书目流通相关(借还书)    3.荐购    0.退出");
            while (sc.hasNext()) {
                int flag = 1;
                int code = 0;
                while(flag == 1) {
                    try {
                        flag = 0;
                        code = Integer.parseInt(sc.nextLine());
                    } catch (Exception e) {
                        System.out.println("Invalid input,try again");
                        flag = 1;
                    }
                }
                switch (code) {
                    case 1:
                        System.out.println("1.创建账户     2.修改账户信息    3.注销账户    4.查询账户欠款    5.查询借阅应还    6.查询预约请求    0.退出");

                        int flag11 = 1;
                        int choice = 0;
                        while(flag11 == 1) {
                            try {
                                flag11 = 0;
                                choice = Integer.parseInt(sc.nextLine());
                            } catch (Exception e) {
                                System.out.println("Invalid input,try again");
                                flag11 = 1;
                            }
                        }

                        switch (choice) {
                            case 1:
                                int flag1 = 1;
                                String[] customerData;
                                while(flag1 == 1) {
                                    try {
                                        flag1 = 0;
                                        System.out.println("请在接下来一行输入您的个人信息，以一个空格符分割：name address email phone password");
                                        customerData = sc.nextLine().split(" ");
                                        main.Account.CreateAccount(customerData);
                                    } catch (Exception e) {
                                        System.out.println("Invalid input,try again");
                                        flag1 = 1;
                                    }
                                }

                                break;

                            case 2:
                                int flag2 = 1;
                                Integer new_choice;
                                while(flag2 == 1) {
                                    try {
                                        flag2 = 0;
                                        System.out.println("请在接下来一行输入您想要更新的信息:1. 名字 2. 地址 3. 邮箱 4. 电话");
                                        new_choice = Integer.parseInt((sc.nextLine()));
                                        main.Account.UpdateAccount(new_choice);
                                    } catch (Exception e) {
                                        System.out.println("Invalid input,try again");
                                        flag2 = 1;
                                    }
                                }

                                break;

                            case 3:
                                int flag3 = 1;
                                String usr_num;
                                while(flag3 == 1) {
                                    try {
                                        flag3 = 0;
                                        System.out.println("请在接下来一行输入您的账户号：");
                                        usr_num = sc.nextLine();
                                        main.Consult.DeleteReader(usr_num);
                                    } catch (Exception e) {
                                        System.out.println("Invalid input,try again");
                                        flag3 = 1;
                                    }
                                }

                                break;

                            case 4:
                                int flag4 = 1;
                                String usr_number;
                                while(flag4 == 1) {
                                    try {
                                        flag4 = 0;
                                        System.out.println("请在接下来一行输入您的账户号：");
                                        usr_number = sc.nextLine();
                                        main.Consult.ConsultFine(usr_number);
                                    } catch (Exception e) {
                                        System.out.println("Invalid input,try again");
                                        flag4 = 1;
                                    }
                                }

                                break;

                            case 5:
                                int flag5 = 1;
                                String usr_numbers;
                                while(flag5 == 1) {
                                    try {
                                        flag5 = 0;
                                        System.out.println("请在接下来一行输入您的账户号：");
                                        usr_numbers = sc.nextLine();
                                        main.Consult.ConsultRecords(usr_numbers);
                                    } catch (Exception e) {
                                        System.out.println("Invalid input,try again");
                                        flag5 = 1;
                                    }
                                }

                                break;

                            case 6:
                                int flag6 = 1;
                                String usr_number1;
                                while(flag6 == 1) {
                                    try {
                                        flag6 = 0;
                                        System.out.println("请在接下来一行输入您的账户号：");
                                        usr_number1 = sc.nextLine();
                                        main.Consult.ConsultWaiting(usr_number1);
                                    } catch (Exception e) {
                                        System.out.println("Invalid input,try again");
                                        flag6 = 1;
                                    }
                                }

                                break;

                            case 0:
                                break;

                            default:
                                System.out.println("请重新输入");
                                System.out.println("1.创建账户     2.修改账户信息    3.注销账户    4.查询账户欠款    5.查询借阅应还    6.查询预约请求    0.退出");
                        }

                        System.out.println("操作完成，您可以继续办理业务或退出");
                        System.out.println("1.账户相关     2.书目流通相关(借还书)    3.荐购    0.退出");
                        continue;

                    case 2:

                        int flag10 = 1;
                        while(flag10 == 1) {
                        System.out.println("1.借书    2.还书    0.退出");

                        int flag1 = 1;
                        int tree = 0;
                        while(flag1 == 1) {
                            try {
                                flag1 = 0;
                                tree = Integer.parseInt(sc.nextLine());
                            } catch (Exception e) {
                                System.out.println("Invalid input,try again");
                                flag1 = 1;
                            }
                        }

                            switch (tree) {
                                case 1:
                                    flag10 = 0;
                                    int flag2 = 1;
                                    String book_name;
                                    while (flag2 == 1) {
                                        try {
                                            flag2 = 0;
                                            System.out.println("请在接下来一行输入您想查阅的书名：");
                                            book_name = sc.nextLine();

                                            main.Book.SelectBooksByBookName(book_name);
                                        } catch (Exception e) {
                                            System.out.println("Invalid input,try again");
                                            flag2 = 1;
                                        }
                                    }

                                    break;
                                case 2:
                                    flag10 = 0;
                                    int flag3 = 1;
                                    String account;
                                    while (flag3 == 1) {
                                        try {
                                            flag3 = 0;
                                            System.out.println("请在接下来一行输入您的账户号：");
                                            account = sc.nextLine();

                                            main.Book.ReturnBooks(account);
                                        } catch (Exception e) {
                                            System.out.println("Invalid input,try again");
                                            flag3 = 1;
                                        }
                                    }

                                    break;

                                case 0:
                                    flag10 = 0;
                                    break;
                            }
                            if(flag10 == 1){
                                System.out.println("请重新输入");
                            }
                        }

                        System.out.println("操作完成，您可以继续办理业务或退出");
                        System.out.println("1.账户相关     2.书目流通相关(借还书)    3.荐购    0.退出");
                        continue;

                    case 3:
                        int flag5 = 1;
                        while (flag5 == 1) {
                            System.out.println("请在接下来一行输入您想荐购的书目信息，以一个空格符分割：book_name publish_house writer");
                            String[] book_info = sc.nextLine().split(" ");

                            flag5 = main.Book.InsertBooksInNeed(book_info);
                        }
                        System.out.println("操作完成，您可以继续办理业务或退出");
                        System.out.println("1.账户相关     2.书目流通相关(借还书)    3.荐购    0.退出");
                        continue;

                    case 0:
                        main.Connect.closeDB();
                        System.exit(0);

                    default:
                        System.out.println("请重新输入");
                        System.out.println("1.账户相关     2.书目流通相关(借还书)    3.荐购    0.退出");
                }
            }

    }
}