package com.example.cryptochecker.model;

public class CryptoItem {
    private String name;
    private String price;
    private String change;
    private final int imageResource;

    public CryptoItem(String name, String price, String change, int imageResource) {
        this.name = name;
        this.price = price;
        this.change = change;
        this.imageResource = imageResource;

    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getChange() {
        return change;
    }
    public int getImageResource(){
        return imageResource;
    }
}
