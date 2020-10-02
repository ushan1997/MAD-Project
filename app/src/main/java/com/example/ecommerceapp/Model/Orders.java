package com.example.ecommerceapp.Model;

public class Orders {

    private String address,colomboArea,name,orderDate,orderTime,phoneNo,state,orderId;
    private Cart cart;

    public Orders(){}

    public Orders(String address, String colomboArea, String name, String orderDate, String orderTime, String phoneNo, String state,Cart cart,String orderId) {
        this.address = address;
        this.colomboArea = colomboArea;
        this.name = name;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.phoneNo = phoneNo;
        this.state = state;
        this.cart =cart;
        this.orderId=orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getColomboArea() {
        return colomboArea;
    }

    public void setColomboArea(String colomboArea) {
        this.colomboArea = colomboArea;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }


}
