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
    private int ISBN;
    private int availability;
    private Connection con;

    public Book(String bookTitle, String authorName, String classification, int ISBN, int availability) {
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

    public int getISBN() {
        return ISBN;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public boolean deleteBook(int isbn) {
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

    public boolean updateBook(String adminID, String title, int isbn, String author, String classification, int availability, int prevISBN) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            con = DriverManager.getConnection(ConnectionURL, "root", "root");
            Statement st = con.createStatement();
            String selectSQL = "SELECT isbn FROM book WHERE book_title = " + prevISBN;
            PreparedStatement preparedStmt = con.prepareStatement(selectSQL);
            ResultSet stmtResult = preparedStmt.executeQuery();
            stmtResult.next();
            String query = "update book set book_title = ?, isbn = ?, author_name = ?, classification = ?, availability = ?  WHERE isbn = " + stmtResult.getInt("isbn");
            PreparedStatement preparedStmt1 = con.prepareStatement(query);
            preparedStmt1.setString(1, title);
            preparedStmt1.setInt(2, isbn);
            preparedStmt1.setString(3, author);
            preparedStmt1.setString(4, classification);
            preparedStmt1.setInt(5, availability);
            preparedStmt1.executeUpdate();
            bookUpdateHistory(isbn, adminID);
            st.close();
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
                book.setISBN(rs.getInt("isbn"));
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

    public boolean addBook(String adminID, int isbn, String bookTitle, String authorName, String classification, int availability) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            con = DriverManager.getConnection(ConnectionURL, "root", "root");

            String query = " insert into book (isbn, book_title, author_name, classification, availability)"
                    + " values (?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, isbn);
            preparedStmt.setString(2, bookTitle);
            preparedStmt.setString(3, authorName);
            preparedStmt.setString(4, classification);
            preparedStmt.setInt(5, availability);
            preparedStmt.execute();
            bookUpdateHistory(isbn, adminID);
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

    public void populateBookTabel(JTable bookTabel) {
        DefaultTableModel bookTable = (DefaultTableModel) bookTabel.getModel();
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

    public String issueBook(String userID, int isbn) {
        String bookAvailability = checkBook(isbn);
        String userHistory = checkUserHistory(userID);
        if (bookAvailability.equalsIgnoreCase("") && userHistory.equalsIgnoreCase("")) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
                con = DriverManager.getConnection(ConnectionURL, "root", "root");

                String selectSQL = "SELECT status, id FROM user WHERE Id = '" + userID + "'";
                PreparedStatement preparedStmt = con.prepareStatement(selectSQL);
                ResultSet stmtResult = preparedStmt.executeQuery();

                String selectSQL2 = "SELECT ISBN, availability FROM book WHERE isbn = " + isbn;
                PreparedStatement preparedStmt2 = con.prepareStatement(selectSQL2);
                ResultSet stmtResult2 = preparedStmt2.executeQuery();

                Calendar c = Calendar.getInstance();
                java.util.Date currentDate = new Date();
                java.sql.Date sqlBorrowDate = new java.sql.Date(currentDate.getTime());
                c.setTime(currentDate);
                c.add(Calendar.DAY_OF_MONTH, 5);
                java.sql.Date sqlDueDate = new java.sql.Date(c.getTimeInMillis());

                stmtResult.next();
                stmtResult2.next();

                if (stmtResult.getBoolean("status")) {

                    String query = " insert into borrows (userID, ISBN, borrowed_date, due_date)"
                            + " values (?, ?, ?, ?)";
                    PreparedStatement preparedStmt3 = con.prepareStatement(query);
                    preparedStmt3.setString(1, stmtResult.getString("id"));
                    preparedStmt3.setString(2, stmtResult2.getString("ISBN"));
                    preparedStmt3.setDate(3, sqlBorrowDate);
                    preparedStmt3.setDate(4, sqlDueDate);
                    preparedStmt3.execute();
                    updateBookAvailability(isbn);
                }
                con.close();
            } catch (SQLException s) {
                System.out.println("SQL statement is not executed!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (bookAvailability.equalsIgnoreCase("availability")) {
            return "availability";
        } else if (userHistory.equalsIgnoreCase("history")) {
            return "history";
        }
        return "successful";
    }

    public String checkBook(int isbn) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            con = DriverManager.getConnection(ConnectionURL, "root", "root");

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

    public void updateBookAvailability(int isbn) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            con = DriverManager.getConnection(ConnectionURL, "root", "root");

            String selectSQL = "SELECT availability FROM book WHERE isbn = '" + isbn + "'";
            PreparedStatement preparedStmt = con.prepareStatement(selectSQL);
            ResultSet stmtResult = preparedStmt.executeQuery();
            stmtResult.next();
            //int av = stmtResult.getInt("availability");
            String query = "update book set availability = ? WHERE isbn = '" + isbn + "'";
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

    public String checkUserHistory(String id) {
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
            System.out.println("SQL statement is not executed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "history";
    }

    public void bookUpdateHistory(int ISBN, String adminID) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            con = DriverManager.getConnection(ConnectionURL, "root", "root");

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
            String query = " insert into update_inventory (ISBN, adminID, update_date)"
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

    public void populateUserTabel(JTable borrowedBook, String id) {
        DefaultTableModel userTabel = (DefaultTableModel) borrowedBook.getModel();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            Connection con = DriverManager.getConnection(ConnectionURL, "root", "root");

            String selectSQL = "SELECT ISBN, return_date, due_date, borrowed_date FROM borrows where userID = '" + id + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(selectSQL);
            while (rs.next()) {
                String[] row = {rs.getString("ISBN"), rs.getString("return_date"), rs.getString("due_date"), rs.getString("borrowed_date")};
                userTabel.addRow(row);
            }
            st.close();
        } catch (Exception e) {
            System.out.println("SQL statement is not executed!");
            System.err.println(e.getMessage());
        }
    }

    public void borrowCancelation(int isbn, String userID) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            Connection con = DriverManager.getConnection(ConnectionURL, "root", "root");
            Statement st = con.createStatement();
            st.executeUpdate("delete from borrows where isbn= " + isbn + " AND userID = '" + userID + "'");
            
        } catch (Exception e) {
            System.out.println("SQL statement is not executed!");
            System.err.println(e.getMessage());
        }
    }

    public String query(String filter, String coulmn) {
        if (filter.equalsIgnoreCase("Title")) {
            return "SELECT ISBN, book_title, classification FROM book where book_title like '%" + coulmn + "%'";
        } else if (filter.equalsIgnoreCase("Classification")) {
            return "SELECT ISBN, book_title, classification FROM book where Classification like '%" + coulmn + "%'";
        } else if (filter.equalsIgnoreCase("ISBN")) {
            return "SELECT ISBN, book_title, classification FROM book where ISBN like '%" + coulmn + "%'";
        } else if (filter.equalsIgnoreCase("Author")) {
            return "SELECT ISBN, book_title, classification FROM book where author_name like '%" + coulmn + "%'";
        }
        return "";
    }

    public void search(String filter, JTable bookTabel, String search) {
        DefaultTableModel bookTable = (DefaultTableModel) bookTabel.getModel();
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            Connection con = DriverManager.getConnection(ConnectionURL, "root", "root");
            String sql = query(filter, filter);
            Statement st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                String[] row = {rs.getString("ISBN"), rs.getString("book_title"), rs.getString("classification")};
                bookTable.addRow(row);
            }
        } catch (Exception e) {
            System.out.println("SQL statement is not executed!");
            System.err.println(e.getMessage());
        }
    }
}
