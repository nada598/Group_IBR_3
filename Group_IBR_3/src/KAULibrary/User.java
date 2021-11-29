package KAULibrary;

public class User {

    private String fullName;
    private String ID;
    private String password;
    private String phoneNum;
    private String email;
    private boolean status;
    private String address;
//    private Connection conn;

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
}
