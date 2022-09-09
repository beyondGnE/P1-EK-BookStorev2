package com.p1.ek.model.objfiles;

public abstract class Item {
    public Item(String title, double price, int quantity, String imgUrl) {
        this.setTitle(title);
        this.setPrice(price);
        this.setQuantity(quantity);
        this.setImgUrl(imgUrl);
    }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getImgUrl() { return imgUrl; }
    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }

    private String title;
    private double price;
    private int quantity;
    private String imgUrl;
}
