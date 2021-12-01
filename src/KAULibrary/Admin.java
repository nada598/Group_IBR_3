package KAULibrary;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin {

    private String fullName;
    private String ID;
    private String password;
    private String phoneNum;
    private String email;
    private String address;
    private Connection con;

    public Admin(String fullName, String ID, String password, String phoneNum, String email, String address) {
        this.fullName = fullName;
        this.ID = ID;
        this.password = password;
        this.phoneNum = phoneNum;
        this.email = email;
        this.address = address;
    }

    Admin() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public boolean adminLogin(String id, String password) {
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

}
