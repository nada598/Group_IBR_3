package KAULibrary;

import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DataBase {

    static Connection con = null;

    public static void main(String[] args) {
//        createDb();
        createTables();
//        createUser();
//        createAdmin();
//        createBook();

    }

    public boolean connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            con = DriverManager.getConnection(ConnectionURL, "root", "root");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void createDb() {
        try {
            // (1) load  JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // (2) set the path 
            String ConnectionURL = "jdbc:mysql://localhost:3306";

            // (3) create connection
            con = DriverManager.getConnection(ConnectionURL, "root", "root");

            // (4) create statment object
            Statement st = con.createStatement();

            st.executeUpdate("CREATE DATABASE KAULibraryDB");

            // (5) excute sql statment
//        st.executeUpdate("CREATE DATABASE KAULibraryDB");
            // (6) set the path for database
            ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";

            // (6) create connection for database
            con = DriverManager.getConnection(ConnectionURL, "root", "root");
            // (7) create statment object
            st = con.createStatement();

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createTables() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            con = DriverManager.getConnection(ConnectionURL, "root", "root");
            Statement st = con.createStatement();

//            String adminTabel = "CREATE TABLE Admin "
//                    + "(admin_ID VARCHAR(100) not NULL, "
//                    + " password VARCHAR(100), "
//                    + " phone_no VARCHAR(100), "
//                    + " name VARCHAR(100), "
//                    + " address VARCHAR(100), "
//                    + " email VARCHAR(100), "
//                    + " PRIMARY KEY ( admin_ID ))";
//            st.executeUpdate(adminTabel);
//
//            String userTabel = "CREATE TABLE user "
//                    + "(Id VARCHAR(100) not NULL, "
//                    + " password VARCHAR(100), "
//                    + " phone_no VARCHAR(100), "
//                    + " name VARCHAR(100), "
//                    + " address VARCHAR(100), "
//                    + " status BOOLEAN, "
//                    + " email VARCHAR(100), "
//                    + " PRIMARY KEY ( id )) ";
//            st.executeUpdate(userTabel);

//            String bookTabel = "CREATE TABLE Book "
//                    + "(ISBN INTEGER not NULL, "
//                    + " book_title VARCHAR(100), "
//                    + " author_name VARCHAR(100), "
//                    + " classification VARCHAR(100), "
//                    + " availability INTEGER, "
//                    + " PRIMARY KEY ( ISBN ))";
//            st.executeUpdate(bookTabel);
//
            String borrow = "CREATE TABLE borrows "
                    + "(userID VARCHAR(100) not NULL, "
                    + " ISBN INTEGER not NULL, "
                    + " return_date DATE, "
                    + " due_date DATE, "
                    + " borrowed_date DATE, "
                    + " PRIMARY KEY ( userID, ISBN  ), "
                    + " FOREIGN KEY( userID ) REFERENCES user( Id ), "
                    + " FOREIGN KEY( ISBN ) REFERENCES Book( ISBN )) ";
            st.executeUpdate(borrow);

//            String modifryProfile = "CREATE TABLE modify_Profile "
//                    + "(userID VARCHAR(100) not NULL, "
//                    + " adminID VARCHAR(100) not NULL, "
//                    + " edit_date DATE, "
//                    + " PRIMARY KEY ( userID, adminID  ), "
//                    + " FOREIGN KEY ( adminID) REFERENCES Admin( admin_ID), "
//                    + " FOREIGN KEY( userID ) REFERENCES user( Id )) ";
//
//            st.executeUpdate(modifryProfile);

            String updateInventory = "CREATE TABLE update_Inventory "
                    + "(ISBN INTEGER not NULL, "
                    + " adminID VARCHAR(100) not NULL, "
                    + " update_date DATE, "
                    + " PRIMARY KEY ( adminID, ISBN  ), "
                    + " FOREIGN KEY ( adminID) REFERENCES Admin( admin_ID), "
                    + " FOREIGN KEY( ISBN ) REFERENCES Book( ISBN )) ";
            st.executeUpdate(updateInventory);

            con.close();
        } catch (SQLException s) {
            System.out.println("SQL statement is not executed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createAdmin() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("Admin Database.txt"));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                String[] adminInfo = strLine.split(" ");
                for (int i = 0; i < adminInfo.length; i++) {
                    adminInfo[i] = adminInfo[i].replaceAll("_", " ");
                }
                Admin admin = new Admin();
                admin.setID(adminInfo[0]);
                admin.setPassword(adminInfo[1]);
                admin.setPhoneNum(adminInfo[2]);
                admin.setFullName(adminInfo[3]);
                admin.setAddress(adminInfo[4]);
                admin.setEmail(adminInfo[5]);
                insertAdmin(admin);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File is not found ");
        } catch (IOException ex) {
            System.out.println("File is not found ");
        }
    }

    public static void createUser() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("User Database.txt"));
            String strLine;

            while ((strLine = br.readLine()) != null) {
                String[] userInfo = strLine.split(" ");
                for (int i = 0; i < userInfo.length; i++) {
                    userInfo[i] = userInfo[i].replaceAll("_", " ");
                }
                User user = new User();
                user.setID(userInfo[0]);
                user.setPassword(userInfo[1]);
                user.setPhoneNum(userInfo[2]);
                user.setFullName(userInfo[3]);
                user.setAddress(userInfo[4]);
                user.setStatus(Boolean.parseBoolean(userInfo[5]));
                user.setEmail(userInfo[6]);
                insertUser(user);
            }

        } catch (FileNotFoundException ex) {
            System.out.println("File is not found ");
        } catch (IOException ex) {
            System.out.println("File is not found ");
        }
    }

    public static void createBook() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("Book Inventory Database.txt"));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                String[] bookInfo = strLine.split(" ");
                for (int i = 0; i < bookInfo.length; i++) {
                    bookInfo[i] = bookInfo[i].replace("_", " ");
                }
                Book book = new Book();
                book.setISBN(Integer.parseInt(bookInfo[0]));
                book.setBookTitle(bookInfo[1]);
                book.setAuthorName(bookInfo[2]);
                book.setClassification(bookInfo[3]);
                book.setAvailability(Integer.parseInt(bookInfo[4]));
                insertBook(book);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File is not found ");
        } catch (IOException ex) {
            System.out.println("File is not found ");
        }
    }

    public static void insertAdmin(Admin admin) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            con = DriverManager.getConnection(ConnectionURL, "root", "root");
            String query = " insert into admin (admin_ID, password, phone_no, name, address, email)"
                    + " values (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);

            preparedStmt.setString(1, admin.getID());
            preparedStmt.setString(2, admin.getPassword());
            preparedStmt.setString(3, admin.getPhoneNum());
            preparedStmt.setString(4, admin.getFullName());
            preparedStmt.setString(5, admin.getAddress());
            preparedStmt.setString(6, admin.getEmail());
            preparedStmt.execute();
            con.close();
        } catch (SQLException s) {
            System.out.println("SQL statement is not executed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertUser(User user) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            con = DriverManager.getConnection(ConnectionURL, "root", "root");
            String query = " insert into user (id, password, phone_no, name, address, status, email)"
                    + " values (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);

            preparedStmt.setString(1, user.getID());
            preparedStmt.setString(2, user.getPassword());
            preparedStmt.setString(3, user.getPhoneNum());
            preparedStmt.setString(4, user.getFullName());
            preparedStmt.setString(5, user.getAddress());
            preparedStmt.setBoolean(6, user.isStatus());
            preparedStmt.setString(7, user.getEmail());
            preparedStmt.execute();
            con.close();
        } catch (SQLException s) {
            System.out.println("SQL statement is not executed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertBook(Book book) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            con = DriverManager.getConnection(ConnectionURL, "root", "root");
            String query = " insert into book (ISBN, book_title, author_name, Classification, Availability)"
                    + " values (?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);

            preparedStmt.setInt(1, book.getISBN());
            preparedStmt.setString(2, book.getBookTitle());
            preparedStmt.setString(3, book.getAuthorName());
            preparedStmt.setString(4, book.getClassification());
            preparedStmt.setInt(5, book.getAvailability());
            preparedStmt.execute();
            con.close();
        } catch (SQLException s) {
            System.out.println("SQL statement is not executed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void populateTabel(JTable jTable1) {
        DefaultTableModel bookTable = (DefaultTableModel) jTable1.getModel();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            Connection con = DriverManager.getConnection(ConnectionURL, "root", "root");
            String selectSQL = "SELECT ISBN, book_title, classification FROM book";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(selectSQL);
            while (rs.next()) {
                String[] row = {rs.getString("ISBN"), rs.getString("book_title"), rs.getString("classification")};
                bookTable.addRow(row);
            }
            st.close();
        } catch (Exception e) {
            System.out.println("SQL statement is not executed!");
            System.err.println(e.getMessage());
        }
    }

    public static boolean adminLogin(String id, String password) {
        boolean isAdmin = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            con = DriverManager.getConnection(ConnectionURL, "root", "root");

            String selectSQL = "SELECT admin_ID, password FROM admin WHERE admin_ID = '" + id + "'" + "and password = '" + password + "'";
            PreparedStatement preparedStmt = con.prepareStatement(selectSQL);
            ResultSet stmtResult = preparedStmt.executeQuery();
            if (stmtResult.isBeforeFirst()) {
                return true;
            }
            con.close();
        } catch (SQLException s) {
            System.out.println("SQL statement is not executed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAdmin;
    }

    public static boolean userLogin(String id, String password) {
        boolean isUser = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            con = DriverManager.getConnection(ConnectionURL, "root", "root");
            String selectSQL = "SELECT Id, password FROM user WHERE Id = '" + id + "'" + "and password = '" + password + "'";
            PreparedStatement preparedStmt = con.prepareStatement(selectSQL);
            ResultSet stmtResult = preparedStmt.executeQuery();
            if (stmtResult.isBeforeFirst()) {
                return true;
            }
            con.close();
        } catch (SQLException s) {
            System.out.println("SQL statement is not executed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUser;
    }

    public static String issueBook(String id, String bookTitle) {
        if (checkBook(bookTitle).equalsIgnoreCase("") && checkUserHistory(id).equalsIgnoreCase("")) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
                con = DriverManager.getConnection(ConnectionURL, "root", "root");

                String selectSQL = "SELECT status, id FROM user WHERE Id = '" + id + "'";
                PreparedStatement preparedStmt = con.prepareStatement(selectSQL);
                ResultSet stmtResult = preparedStmt.executeQuery();

                String selectSQL2 = "SELECT ISBN FROM book WHERE book_title = '" + bookTitle + "'";
                PreparedStatement preparedStmt2 = con.prepareStatement(selectSQL2);
                ResultSet stmtResult2 = preparedStmt2.executeQuery();

                java.util.Date date = new java.util.Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                java.sql.Timestamp sqlTime = new java.sql.Timestamp(date.getTime());
                stmtResult.next();
                if (stmtResult.getBoolean("status")) {
                    stmtResult2.next();

                    String query = " insert into borrows (userID, ISBN, borrowed_date)"
                            + " values (?, ?, ?)";
                    PreparedStatement preparedStmt3 = con.prepareStatement(query);
                    preparedStmt3.setString(1, stmtResult.getString("id"));
                    preparedStmt3.setString(2, stmtResult2.getString("ISBN"));
                    preparedStmt3.setDate(3, sqlDate);
                    preparedStmt3.execute();
                    updateBook(bookTitle);
                }
                con.close();
            } catch (SQLException s) {
                System.out.println("issueBook " + s);

                System.out.println("SQL statement is not executed!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (checkBook(bookTitle).equalsIgnoreCase("availability")) {
            return "availability";
        } else if (checkUserHistory(id).equalsIgnoreCase("history")) {
            return "history";
        }
        return "";
    }

    public static String checkBook(String bookTitle) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            con = DriverManager.getConnection(ConnectionURL, "root", "root");

            String selectSQL = "SELECT availability FROM book WHERE book_title = '" + bookTitle + "'";
            PreparedStatement preparedStmt = con.prepareStatement(selectSQL);
            ResultSet stmtResult = preparedStmt.executeQuery();
            stmtResult.next();
            if (stmtResult.getInt("availability") > 0) {
                return "";
            }
            con.close();
        } catch (SQLException s) {
            System.out.println("SQL statement is not executed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "availability";
    }

    public static void updateBook(String bookTitle) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            con = DriverManager.getConnection(ConnectionURL, "root", "root");

            String selectSQL = "SELECT availability FROM book WHERE book_title = '" + bookTitle + "'";
            PreparedStatement preparedStmt = con.prepareStatement(selectSQL);
            ResultSet stmtResult = preparedStmt.executeQuery();
            stmtResult.next();
            //int av = stmtResult.getInt("availability");
            String query = "update book set availability = ? WHERE book_title = '" + bookTitle + "'";
            PreparedStatement preparedStmt2 = con.prepareStatement(query);
            preparedStmt2.setInt(1, stmtResult.getInt("availability") - 1);
            preparedStmt2.executeUpdate();
            con.close();
        } catch (SQLException s) {
            System.out.println("SQL statement is not executed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String checkUserHistory(String id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            con = DriverManager.getConnection(ConnectionURL, "root", "root");

            String selectSQL = "SELECT userID, COUNT(userID) FROM borrows WHERE userID = '" + id + "'";
            PreparedStatement preparedStmt = con.prepareStatement(selectSQL);
            ResultSet stmtResult = preparedStmt.executeQuery();
            stmtResult.next();
            if (stmtResult.getInt("count(userID)") < 5) {
                return "";
            } else {
                String query = "update user set status = ? WHERE id = '" + id + "'";
                PreparedStatement preparedStmt2 = con.prepareStatement(query);
                preparedStmt2.setBoolean(1, false);
                preparedStmt2.executeUpdate();
            }
            con.close();
        } catch (SQLException s) {
            System.out.println("checkUserHistory " + s);
            System.out.println("SQL statement is not executed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "history";
    }

    public static void addBook() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            con = DriverManager.getConnection(ConnectionURL, "root", "root");
            
            
            con.close();
        } catch (SQLException s) {
            System.out.println("checkUserHistory " + s);
            System.out.println("SQL statement is not executed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
