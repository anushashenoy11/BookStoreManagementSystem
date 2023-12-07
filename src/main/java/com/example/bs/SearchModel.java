//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.bs;

import java.math.BigInteger;

public class SearchModel {
    BigInteger bookID;
    String bookTitle;
    String bookAuthor;
    Integer bookPrice;
    Integer bookStock;
    String bookDescription;

    public SearchModel(BigInteger bookID, String bookTitle, String bookAuthor, Integer bookPrice, Integer bookStock, String bookDescription) {
        this.bookID = bookID;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookPrice = bookPrice;
        this.bookStock = bookStock;
        this.bookDescription = bookDescription;
    }

    public BigInteger getBookID() {
        return this.bookID;
    }

    public void setBookID(BigInteger bookID) {
        this.bookID = bookID;
    }

    public String getBookTitle() {
        return this.bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return this.bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public Integer getBookPrice() {
        return this.bookPrice;
    }

    public void setBookPrice(Integer bookPrice) {
        this.bookPrice = bookPrice;
    }

    public Integer getBookStock() {
        return this.bookStock;
    }

    public void setBookStock(Integer bookStock) {
        this.bookStock = bookStock;
    }

    public String getBookDescription() {
        return this.bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }
}
