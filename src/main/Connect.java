package main;

import java.sql.*;

/**
 * Created by Zhao on 2017/5/30.
 */
public class Connect {
    public static final String HOST = "localhost";
    public static final int PORT = 3306;
    public static final String DATABASE = "LMS";
    public static final String USER = "root";
    public static final String PASSWORD = "zzq123";

    public static Connection connection;

    public static void connectDB() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?useUnicode=true&characterEncoding=gb2312";
        System.out.println("JDBC URL: " + url);
        connection = DriverManager.getConnection(url, USER, PASSWORD);
    }

    public static int SimpleVariable(String Aim, String Order) throws ClassCastException, SQLException{
        int return_number;
        PreparedStatement stat = main.Connect.connection.prepareStatement(Order);
        stat.setString(1, Aim);
        //为该语句的第一个变量位置赋值book_name
        return_number = stat.executeUpdate();//update sql schema
        //System.out.println(a);

        return return_number;
    }

    public static int DoubleVariable(String Aim1,String Aim2, String Order) throws ClassCastException, SQLException{
        int return_number;
        PreparedStatement stat = main.Connect.connection.prepareStatement(Order);
        stat.setString(1, Aim1);
        stat.setString(2, Aim2);
        //为该语句的第一个变量位置赋值book_name
        return_number = stat.executeUpdate();//update sql schema
        //System.out.println(a);

        return return_number;
    }

    public static ResultSet SimpleInput(String Aim, String Order) throws ClassCastException, SQLException{
        PreparedStatement prepStat = main.Connect.connection.prepareStatement(Order);
        prepStat.setString(1, Aim);
        //System.out.println(prepStat);
        //为该语句的第一个变量位置赋值book_name
        ResultSet rs = prepStat.executeQuery();

        return rs;
    }

    public static ResultSet DoubleInput(String Aim1, String Aim2, String Order) throws ClassCastException, SQLException{
        PreparedStatement prepStat = main.Connect.connection.prepareStatement(Order);
        prepStat.setString(1, Aim1);
        prepStat.setString(2, Aim2);
        //System.out.println(prepStat);
        //为该语句的第一个变量位置赋值book_name
        ResultSet rs = prepStat.executeQuery();

        return rs;
    }

    public static void closeDB() throws SQLException {
        connection.close();
    }

}
