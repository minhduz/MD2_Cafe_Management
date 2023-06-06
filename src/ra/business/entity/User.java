package ra.business.entity;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private int userID;
    private String userName;
    private String userPassword;
    private String userConfirmPassword;
    private String userFullName;
    private Date userDate;
    private boolean userStatus;
    private String userEmail;
    private String userPhone;

    public User() {
    }

    public User(int userID, String userName, String userPassword, String userConfirmPassword, String userFullName, Date userDate, boolean userStatus, String userEmail, String userPhone) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userConfirmPassword = userConfirmPassword;
        this.userFullName = userFullName;
        this.userDate = userDate;
        this.userStatus = userStatus;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserConfirmPassword() {
        return userConfirmPassword;
    }

    public void setUserConfirmPassword(String userConfirmPassword) {
        this.userConfirmPassword = userConfirmPassword;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public Date getUserDate() {
        return userDate;
    }

    public void setUserDate(Date userDate) {
        this.userDate = userDate;
    }

    public boolean isUserStatus() {
        return userStatus;
    }

    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
