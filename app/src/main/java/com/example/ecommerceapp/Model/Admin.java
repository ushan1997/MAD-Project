package com.example.ecommerceapp.Model;

public class Admin {
    private String Name, Email, Phone, Address, Password;

    public Admin() {
    }

    public Admin(String Name, String Email, String Phone, String Address, String Password) {
        this.Name = Name;
        this.Email = Email;
        this.Phone = Phone;
        this.Address = Address;
        this.Password = Password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
}
