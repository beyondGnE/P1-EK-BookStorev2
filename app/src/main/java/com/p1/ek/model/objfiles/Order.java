package com.p1.ek.model.objfiles;

import java.util.ArrayList;
import java.util.List;

public class Order {
    
    public Order() {
        books = new ArrayList<>();
        subTotal = 0.0;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public double getSubTotal() { return subTotal; }
    public void setSubTotal(double subTotal) { this.subTotal = subTotal; }

    private List<Book> books;
    private double subTotal;
}
