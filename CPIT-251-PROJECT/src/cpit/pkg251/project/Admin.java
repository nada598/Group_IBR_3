package cpit.pkg251.project;

public class Admin {

    private String fullName;
    private String ID;
    private String password;
    private String phoneNum;
    private String email;
    private String address;
//    private Connection conn;

    Admin(String ID, String password, String phoneNum, String fullName, String address, String email) {
        fullName = this.fullName;
        ID = this.ID;
        password = this.password;
        phoneNum = this.phoneNum;
        email = this.email;
        address = this.address;
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

}
