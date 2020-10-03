package com.example.ecommerceapp.Model;

public class Users {

    private String email,username,phno,password,image,address;

    private Users(){

        }

    public Users(String email, String username, String phno, String password, String image, String address) {
        this.email = email;
        this.username = username;
        this.phno = phno;
        this.password = password;

        this.image = image;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email)   {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}



