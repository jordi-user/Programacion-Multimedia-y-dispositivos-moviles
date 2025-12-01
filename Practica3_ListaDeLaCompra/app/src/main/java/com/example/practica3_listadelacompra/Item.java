package com.example.practica3_listadelacompra;

public class Item {
    private String name;
    private int quantity;
    private int imageResId;

    public Item(String name, int quantity, int imageResId) {
        this.name = name;
        this.quantity = quantity;
        this.imageResId = imageResId;
    }

    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public int getImageResId() { return imageResId; }
}