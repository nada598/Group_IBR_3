package KAULibrary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    private String fullName;
    private String ID;
    private String password;
    private String phoneNum;
    private String email;
    private boolean status;
    private String address;
    private static Connection con;

    public User(String fullName, String ID, String password, String phoneNum, String email, boolean status, String address) {
        this.fullName = fullName;
        this.ID = ID;
        this.password = password;
        this.phoneNum = phoneNum;
        this.email = email;
        this.status = status;
        this.address = address;
    }

    User() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean userLogin(String id, String password) {
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

}
