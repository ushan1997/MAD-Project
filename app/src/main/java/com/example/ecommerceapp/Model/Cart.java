/**IT1975140
 Gunaratne U.A
 Metro Weekday*/
package com.example.ecommerceapp.Model;

public class Cart {

    private int pcode;
    private String pdate,pdescription,pdiscount,pname,pprice,pquantity,ptime,pimage;

    public Cart(){
    }

    public Cart(int pcode, String pdate, String pdescription, String pdiscount, String pname, String pprice, String pquantity, String ptime,String pimage) {
        this.pcode = pcode;
        this.pdate = pdate;
        this.pdescription = pdescription;
        this.pdiscount = pdiscount;
        this.pname = pname;
        this.pprice = pprice;
        this.pquantity = pquantity;
        this.ptime = ptime;
        this.pimage =pimage;
    }

    public int getPcode() {
        return pcode;
    }

    public void setPcode(int pcode) {
        this.pcode = pcode;
    }

    public String getPdate() {
        return pdate;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }

    public String getPdescription() {
        return pdescription;
    }

    public void setPdescription(String pdescription) {
        this.pdescription = pdescription;
    }

    public String getPdiscount() {
        return pdiscount;
    }

    public void setPdiscount(String pdiscount) {
        this.pdiscount = pdiscount;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
    }

    public String getPquantity() {
        return pquantity;
    }

    public void setPquantity(String pquantity) {
        this.pquantity = pquantity;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }
}
