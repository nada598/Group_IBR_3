package KAULibrary;

import java.io.*;
import java.sql.*;

public class Database {

    static Connection con = null;

    public static void main(String[] args) {
//        createDb();
//        createTables();
//        createUser();
//        createAdmin();
//          createBook();
//.
    }

    // a method to create the KAU Library Database
    public static void createDb() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306";
            con = DriverManager.getConnection(ConnectionURL, "root", "su");
            Statement st = con.createStatement();
            st.executeUpdate("CREATE DATABASE KAULibraryDB");
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // a method to initialize tables in the database  
    public static void createTables() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            con = DriverManager.getConnection(ConnectionURL, "root", "root");
            Statement st = con.createStatement();

            String adminTabel = "CREATE TABLE Admin "
                    + "(admin_ID VARCHAR(100) not NULL, "
                    + " password VARCHAR(100), "
                    + " phone_no VARCHAR(100), "
                    + " name VARCHAR(100), "
                    + " address VARCHAR(100), "
                    + " email VARCHAR(100), "
                    + " PRIMARY KEY ( admin_ID ))";
            st.executeUpdate(adminTabel);

            String userTabel = "CREATE TABLE user "
                    + "(Id VARCHAR(100) not NULL, "
                    + " password VARCHAR(100), "
                    + " phone_no VARCHAR(100), "
                    + " name VARCHAR(100), "
                    + " address VARCHAR(100), "
                    + " status BOOLEAN, "
                    + " email VARCHAR(100), "
                    + " PRIMARY KEY ( id )) ";
            st.executeUpdate(userTabel);

            String bookTabel = "CREATE TABLE Book "
                    + "(ISBN VARCHAR(100) not NULL, "
                    + " book_title VARCHAR(100), "
                    + " author_name VARCHAR(100), "
                    + " classification VARCHAR(100), "
                    + " availability INTEGER, "
                    + " PRIMARY KEY ( ISBN ))";
            st.executeUpdate(bookTabel);

            String issuedBook = "CREATE TABLE issued_Book "
                    + "(userID VARCHAR(100) not NULL, "
                    + " ISBN INTEGER not NULL, "
                    + " return_date DATE, "
                    + " borrowed_date DATE, "
                    + " due_date DATE, "
                    + " PRIMARY KEY ( userID, ISBN  ), "
                    + " FOREIGN KEY( userID ) REFERENCES user( Id ))";
            st.executeUpdate(issuedBook);

            String inventoryHistory = "CREATE TABLE Inventory_History "
                    + "(ISBN INTEGER not NULL, "
                    + " adminID VARCHAR(100) not NULL, "
                    + " update_date DATE, "
                    + " PRIMARY KEY ( adminID ), "
                    + " FOREIGN KEY ( adminID) REFERENCES Admin( admin_ID))";
            st.executeUpdate(inventoryHistory);

            con.close();
        } catch (SQLException s) {
            System.out.println(s);
            System.out.println("SQL statement is not executed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // a method to create an admin object from a file and use it to insert it in the admin table
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

    // a method to create a user object from a file and use it to insert it in the user table
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

    // a method to create a book object from a file and use it to insert it in the book table
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
                book.setISBN(bookInfo[0]);
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

    // a method to connect with the database and insert the admin object into the admin table using a prepared query
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

    // a method to connect with the database and insert the user object into the user table using a prepared query
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

    // a method to connect with the database and insert the book object into the book table using a prepared query
    public static void insertBook(Book book) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            con = DriverManager.getConnection(ConnectionURL, "root", "root");
            String query = " insert into book (ISBN, book_title, author_name, Classification, Availability)"
                    + " values (?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);

            preparedStmt.setString(1, book.getISBN());
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

      // a method to assist the login method to decide wich query to use
    public static String query(String id, String password) {
        if (id.startsWith("000")) {
            return "SELECT admin_ID, password FROM admin WHERE admin_ID = '" + id + "'" + "and password = '" + password + "'";
        } else {
            return "SELECT Id, password FROM user WHERE Id = '" + id + "'" + "and password = '" + password + "'";
        }
    }
    
     public static boolean login(String id, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            con = DriverManager.getConnection(ConnectionURL, "root", "root");
            String selectSQL = query(id, password);
            PreparedStatement preparedStmt = con.prepareStatement(selectSQL);
            ResultSet stmtResult = preparedStmt.executeQuery();
            if (stmtResult.isBeforeFirst()) {
                return true;
            }
            con.close();
        } catch (SQLException s) {
            System.out.println("SQL statement is not executed!");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    
    
}
