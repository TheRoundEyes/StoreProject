package com.example.projectstore.obj;

public class User {

    private String fullname, location, storename, emailaddress, password;
    private Long contactnumber;
    private boolean customer = false;

    public User() {}

    public User(String fullname, String location, String storename, String emailaddress, String password, Long contactnumber) {
        this.fullname = fullname;
        this.location = location;
        this.storename = storename;
        this.emailaddress = emailaddress;
        this.password = password;
        this.contactnumber = contactnumber;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String f){
        fullname = f;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String l){
        location = l;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String s){
        storename = s;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String e){
        emailaddress = e;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String p){
        password = p;
    }

    public Long getContactnumber(){
        return contactnumber;
    }

    public void setContactnumber(Long contactnumber) {
        this.contactnumber = contactnumber;
    }

    public boolean isCustomer(){ return this.customer; }

    public void setCustomer(boolean customer) { this.customer = customer; }
}
