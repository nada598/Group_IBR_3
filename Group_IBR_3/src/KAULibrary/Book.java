package KAULibrary;

import static KAULibrary.DataBase.con;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static KAULibrary.Search.book;

public class Book {

    private String bookTitle;
    private String authorName;
    private String classification;
    private int ISBN;
    private int availability;
    private static Connection conn;

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

    public static Connection getConn() {
        return conn;
    }

    public static void setConn(Connection conn) {
        Book.conn = conn;
    }

    //##
    public void search(String searchType, int index) {
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            conn = DriverManager.getConnection(ConnectionURL, "root", "sumar");
            Statement st = conn.createStatement();

            String typeName = null;
            ResultSet res = null;
            switch (index) {
                case 0:
                    typeName = "Title";
                    break;
                case 1:
                    typeName = "ISBN";
                    res = st.executeQuery("SELECT * FROM  Book where " + typeName + "=" + searchType);
                    break;
                case 2:
                    typeName = "Author";
                    break;
                case 3:
                    typeName = "Classification";
                    break;

            }

            if (index != 1) {
                res = st.executeQuery("SELECT * FROM  Book where " + typeName + "='" + searchType + "'");
            }
            //method from search class takes res then do the following 0u0       
            new Search().searchResult(res);

//         while (res.next()) {
//        String t = res.getString(1);
//        int i = res.getInt(2);
//        String a = res.getString(3);
//        String c = res.getString(4);
//        System.out.println(t +" "+i+" "+a+" "+c);
//        }
            conn.close();
        } catch (SQLException s) {
            System.out.println("SQL statement is not executed!");
            s.printStackTrace();
        }
        // return book;
    }

    public boolean deleteBook(String isbn) {
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            conn = DriverManager.getConnection(ConnectionURL, "root", "root");
            Statement st = conn.createStatement();
            st.executeUpdate("delete from book where isbn= '" + isbn + "'");

            conn.close();
        } catch (SQLException s) {
            System.out.println("SQL statement is not executed!");
            s.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateBook(String title, int isbn, String author, String classification, int availability, int prevISBN) {
        try {
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            conn = DriverManager.getConnection(ConnectionURL, "root", "root");
          //  Statement st = conn.createStatement();
          //  st.executeUpdate("update book set  book_title='" + title + "', isbn= " + isbn + ", author_name='" + author + "', classification='" + classification + "', availability= '" + availability + "' where isbn=" + prevISBN);

            String query = "update book set book_title = ?, isbn = ?, author_name = ?, classification = ?, availability = ?  WHERE isbn = " + prevISBN;
            PreparedStatement preparedStmt = con.prepareStatement(query);
             ResultSet stmtResult = preparedStmt.executeQuery();
            stmtResult.next();
            preparedStmt.setString(1, title);
            preparedStmt.setInt(2, isbn);
            preparedStmt.setString(3, author);
            preparedStmt.setString(4, classification);
            preparedStmt.setInt(5, availability);
            preparedStmt.executeUpdate();

            conn.close();
        } catch (SQLException s) {
            System.out.println("SQL statement is not executed!");
            s.printStackTrace();
            return false;
        }
        return true;
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
                System.out.println("update");
                return book;

            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("SQL statement is not executed!");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public boolean addBook(int isbn, String bookTitle, String authorName, String classification, int availability) {
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

    public boolean isExist(String isbn) {
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            String ConnectionURL = "jdbc:mysql://localhost:3306/KAULibraryDB";
            conn = DriverManager.getConnection(ConnectionURL, "root", "root");
            // Statement st = conn.createStatement();
            // st.executeQuery("SELECT isbn FROM  Book where isbn=" + isbn);

            String selectSQL = "SELECT isbn FROM  Book where isbn= '" + isbn + "'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(selectSQL);
            if (!rs.isBeforeFirst()) {
                return false;
            }

            conn.close();
        } catch (SQLException s) {
            System.out.println("SQL statement is not executed!");
            s.printStackTrace();
            return false;
        }
        return true;
    }

}
