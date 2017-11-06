package main;

/**
 * Created by Zhao on 2017/5/30.
 */
public class SQL {
    public static final String ACCOUNT_CREATE = "INSERT INTO reader(`usr_name`,`address`,`email`,`phone`,`password`) values (?,?,?,?,?)";
    public static final String CONSULT_ACCOUNT = "select * from reader where usr_name = ? and phone = ?";
    public static final String SELECT_ACCOUNT  = "SELECT * FROM reader WHERE usr_num = ?";
    public static final String UPDATE_ACCOUNT_NAME = "UPDATE reader SET usr_name = ? WHERE usr_num = ?";
    public static final String UPDATE_ACCOUNT_ADDRESS = "UPDATE reader SET address = ? WHERE usr_num = ?";
    public static final String UPDATE_ACCOUNT_EMAIL = "UPDATE reader SET email = ? WHERE usr_num = ?";
    public static final String UPDATE_ACCOUNT_PHONE = "UPDATE reader SET phone = ? WHERE usr_num = ?";
    public static final String SELECT_TOTAL = "select total from reader where usr_num = ?";
    public static final String DELETE_READER = "DELETE FROM reader WHERE usr_num = ?";
    public static final String CHECK_ACCOUNT = "select usr_num from reader where usr_num = ? and password = ?";

    public static final String INSERT_BOOK = "INSERT INTO book(`ISBN`,`book_name`,`publish_house`,`writer`,`category`) values(%f,'%s','%s','%s','%s')";
    public static final String SELECT_BOOKS_BY_BOOKNAME = "SELECT * FROM book WHERE book_name like ? limit 20";
    public static final String SELECT_BOOK_BY_ISBN = "select * from book where ISBN = ?";

    public static final String INSERT_BOOK_IN_NEED = "INSERT INTO book_in_need(`book_name`,`publish_house`,`writer`,`state`) values('%s','%s','%s','%s')";

    public static final String SELECT_DUPLICATE = "SELECT * FROM book_info WHERE ISBN = ?";
    public static final String RECOVER_DUPLICATE = "update book_info set state = 'on' where ISBN = ? and state = 'out' limit 1";

    //    public static final String DELETE_ACCOUNT = "DELETE FROM account WHERE account_num = ?";
    public static final String DELETE_BORROW_RECORD = "DELETE FROM borrow WHERE usr_num = ? and ISBN = ?";
    public static final String SELECT_BORROW = "select * from borrow where usr_num = ?";
    public static final String INSERT_BORROW = "insert into borrow (`usr_num`,`ISBN`)values(?,?)";
    public static final String BORROW_BOOK = "update book_info set state = 'out' where ISBN = ? and state = 'on' limit 1";
    public static final String UPDATE_BORROW_TIMES = "update borrow set borrow_times = borrow_times + 1 where ISBN = ? and usr_num = ? and borrow_times = 1";
    public static final String UPDATE_BORROW_DAY = "update borrow set borrow_day = now() where ISBN = ? and usr_num = ?";

    public static final String SATISFIED = "update waiting set state = 'Satisfied' where ISBN = ? and state = 'notyet' order by request_time limit 1";
    public static final String SELECT_WAITING = "select * from waiting where usr_num = ?";
    public static final String DELETE_WAITING = "delete from waiting where usr_num = ? and ISBN = ?";
    public static final String INSERT_WAITING = "INSERT INTO waiting (`usr_num`,`ISBN`)VALUES(?,?)";
    public static final String SELECT_WAITING_BY_ISBN = "select * from waiting where ISBN = ?";
    public static final String CHANGE_BOOK_STATE_AFTER_SATISFIED = "update book_info set state = 'in need' where state = 'on' and ISBN = ? limit 1";
    public static final String RECOVER_DUPLICATE_IN_NEED = "update book_info set state = 'on' where state = 'in need' and ISBN = ? limit 1";
}
