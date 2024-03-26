package com.springMVC.mvc.entity;

public class Product {
    private int id;
    private String name;
    private int harga;


    public Product() {
    }

    public Product(int id, String name, int harga) {
        this.id = id;
        this.name = name;
        this.harga = harga;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
