package com.example.projectstore.obj;

public class UserClass {
    String fullName, location, contactNumber, storeName, email, username, password, userType;

    public UserClass() {}

    public UserClass(String fullName, String location, String contactNumber, String email, String username, String password, String userType) {
        this.fullName = fullName;
        this.location = location;
        this.contactNumber = contactNumber;
        this.email = email;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public UserClass(String fullName, String location, String contactNumber, String storeName, String email, String username, String password, String userType) {
        this.fullName = fullName;
        this.location = location;
        this.contactNumber = contactNumber;
        this.storeName = storeName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setLocation(String location) { this.location = location; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public void setStoreName(String storeName) { this.storeName = storeName; }
    public void setEmail(String email) { this.email = email; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setUserType(String userType) { this.userType = userType; }

    public String getFullName() { return fullName; }
    public String getLocation() { return location; }
    public String getContactNumber() { return contactNumber; }
    public String getStoreName() { return storeName; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getUserType() { return userType; }
}
