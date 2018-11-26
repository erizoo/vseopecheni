package ru.vseopecheni.app.data.models;

public class Products {

    private String yes;
    private String no;

    public Products(String yes, String no) {
        this.yes = yes;
        this.no = no;
    }

    public String getYes() {
        return yes;
    }

    public void setYes(String yes) {
        this.yes = yes;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
