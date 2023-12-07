//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.bs;

public class CartItemModel {
    private Integer bookID;
    private String title;
    private int price;

    public CartItemModel(Integer bookID, String title, int price) {
        this.bookID = bookID;
        this.title = title;
        this.price = price;
    }

    public Integer getBookID() {
        return this.bookID;
    }

    public String getTitle() {
        return this.title;
    }

    public int getPrice() {
        return this.price;
    }

    public void setBookID(Integer bookID) {
        this.bookID = bookID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
