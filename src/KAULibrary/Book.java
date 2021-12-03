package KAULibrary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static KAULibrary.Search.book;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Book {

    private String bookTitle;
    private String authorName;
    private String classification;
    private String ISBN;
    private int availability;
    private Connection con;

    public Book(String bookTitle, String authorName, String classification, String ISBN, int availability) {
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.classification = classification;
        this.ISBN = ISBN;
        this.availability = availability;
    }

    public Book() {
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public boolean deleteBook(String isbn) {
        try {
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            con = DriverManager.getConnection(ConnectionURL, "root", "root");
            Statement st = con.createStatement();
            st.executeUpdate("delete from book where isbn= " + isbn + "");

            con.close();
        } catch (SQLException s) {
            System.out.println("SQL statement is not executed!");
            s.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateBook(String adminID, String title, String isbn, String author, String classification, int availability) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            con = DriverManager.getConnection(ConnectionURL, "root", "root");
            Statement st = con.createStatement();
            String query = "update book set book_title = ?, author_name = ?, classification = ?, availability = ?  WHERE isbn = " + isbn ;
            PreparedStatement preparedStmt1 = con.prepareStatement(query);
            preparedStmt1.setString(1, title);
            preparedStmt1.setString(2, author);
            preparedStmt1.setString(3, classification);
            preparedStmt1.setInt(4, availability);
            preparedStmt1.executeUpdate();
            st.close();
            con.close();
            return true;
        } catch (SQLException s) {
            System.out.println(s + " updateBook");
            System.out.println("SQL statement is not executed!");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Book getBookInfo(Book isbn) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            Connection con = DriverManager.getConnection(ConnectionURL, "root", "root");
            String selectSQL = "SELECT book_title, classification, availability, isbn, author_name FROM book where isbn= '" + isbn.getISBN() + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(selectSQL);
            if (rs.next()) {

                book.setBookTitle(rs.getString("book_title"));
                book.setISBN(rs.getString("isbn"));
                book.setAuthorName(rs.getString("author_name"));
                book.setAvailability(rs.getInt("availability"));
                System.out.println(rs.getString("author_name"));
                book.setClassification(rs.getString("classification"));
                return book;

            }
        } catch (Exception e) {
            System.out.println("SQL statement is not executed!");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public boolean addBook(String adminID, String isbn, String bookTitle, String authorName, String classification, int availability) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            con = DriverManager.getConnection(ConnectionURL, "root", "root");

            String query = " insert into book (isbn, book_title, author_name, classification, availability)"
                    + " values (?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, isbn);
            preparedStmt.setString(2, bookTitle);
            preparedStmt.setString(3, authorName);
            preparedStmt.setString(4, classification);
            preparedStmt.setInt(5, availability);
            preparedStmt.execute();
            con.close();
            return true;
        } catch (SQLException s) {
            System.out.println("SQL statement is not executed!");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void increaseBookAvailability(String isbn) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            con = DriverManager.getConnection(ConnectionURL, "root", "root");

            String selectSQL = "SELECT availability FROM book WHERE isbn = '" + isbn + "'";
            PreparedStatement preparedStmt = con.prepareStatement(selectSQL);
            ResultSet stmtResult = preparedStmt.executeQuery();
            stmtResult.next();
            
            String query = "update book set availability = ? WHERE isbn = '" + isbn + "'";
            PreparedStatement preparedStmt2 = con.prepareStatement(query);
            preparedStmt2.setInt(1, stmtResult.getInt("availability") + 1);
            preparedStmt2.executeUpdate();
            con.close();
        } catch (SQLException s) {
            System.out.println("SQL statement is not executed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void borrowCancelation(String isbn, String userID) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            Connection con = DriverManager.getConnection(ConnectionURL, "root", "root");
            Statement st = con.createStatement();
            st.executeUpdate("delete from issued_book where isbn= " + isbn + " AND userID = '" + userID + "'");
            increaseBookAvailability(isbn);
        } catch (Exception e) {
            System.out.println("SQL statement is not executed!");
            System.err.println(e.getMessage());
        }
    }
 
        // a method to check the copied of the book
    public String checkBook(String isbn) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            con = DriverManager.getConnection(ConnectionURL, "root", "sumar");

            String selectSQL = "SELECT availability FROM book WHERE ISBN = '" + isbn + "'";
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
    
      // a method to check if the user has borrowed more than 5 books if so their status become false
    public String checkUserHistory(String id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            con = DriverManager.getConnection(ConnectionURL, "root", "sumar");

            String selectSQL = "SELECT userID, COUNT(userID) FROM issued_book WHERE userID = '" + id + "'";
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
            System.out.println(s + " checkUserHistory");
            System.out.println("SQL statement is not executed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "history";
    }
    
    // a method to store the process that happens on the book along with the admin that updated the book inventory
    public void bookUpdateHistory(String ISBN, String adminID) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            con = DriverManager.getConnection(ConnectionURL, "root", "sumar");

            String selectSQL = "SELECT admin_ID FROM admin WHERE admin_ID = '" + adminID + "'";
            PreparedStatement preparedStmt = con.prepareStatement(selectSQL);
            ResultSet stmtResult = preparedStmt.executeQuery();

            String selectSQL2 = "SELECT ISBN FROM book WHERE isbn = '" + ISBN + "'";
            PreparedStatement preparedStmt2 = con.prepareStatement(selectSQL2);
            ResultSet stmtResult2 = preparedStmt2.executeQuery();

            java.util.Date date = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            stmtResult.next();
            stmtResult2.next();
            String query = " insert into inventory_history (ISBN, adminID, update_date)"
                    + " values (?, ?, ?)";
            PreparedStatement preparedStmt3 = con.prepareStatement(query);
            preparedStmt3.setInt(1, stmtResult2.getInt("ISBN"));
            preparedStmt3.setString(2, stmtResult.getString("admin_ID"));
            preparedStmt3.setDate(3, sqlDate);
            preparedStmt3.execute();
            con.close();
        } catch (SQLException s) {
            System.out.println("SQL statement is not executed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  // a method that display all the search results to the user and the admin
    public void search(String filter, JTable bookTabel, String search) {
        DefaultTableModel bookTable = (DefaultTableModel) bookTabel.getModel();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            Connection con = DriverManager.getConnection(ConnectionURL, "root", "root");
            String sql = query(filter, search);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String[] row = {rs.getString("ISBN"), rs.getString("book_title"), rs.getString("classification")};
                bookTable.addRow(row);
            }
        } catch (Exception e) {
            System.out.println("SQL statement is not executed!");
            System.err.println(e.getMessage());
          
        }
    }
    // a method that assist the search process when using filters
    public String query(String filter, String search) {
        // this method return a query with the choosen search filter that the user/admin chose 
        if (filter.equalsIgnoreCase("Title")) {
            return "SELECT ISBN, book_title, classification FROM book where book_title like '%" + search + "%'";
        } else if (filter.equalsIgnoreCase("Classification")) {
            return "SELECT ISBN, book_title, classification FROM book where Classification like '%" + search + "%'";
        } else if (filter.equalsIgnoreCase("ISBN")) {
            return "SELECT ISBN, book_title, classification FROM book where ISBN like '%" + search + "%'";
        } else if (filter.equalsIgnoreCase("Author")) {
            return "SELECT ISBN, book_title, classification FROM book where author_name like '%" + search + "%'";
        }
        return "";
    }
    
}//class book
