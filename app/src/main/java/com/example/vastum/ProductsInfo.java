package com.example.vastum;

public class ProductsInfo {
    private String Name;
    private int imageResource;

    public ProductsInfo(String name, int imageResource) {
        Name = name;
        this.imageResource = imageResource;
    }

    public ProductsInfo() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
