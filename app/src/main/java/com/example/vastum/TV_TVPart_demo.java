package com.example.vastum;

public class TV_TVPart_demo {
    private String Name;
    private int imageResource;

    public TV_TVPart_demo(String name, int imageResource) {
        Name = name;
        this.imageResource = imageResource;
    }

    public TV_TVPart_demo() {
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
