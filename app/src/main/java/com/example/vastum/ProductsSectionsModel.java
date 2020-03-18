package com.example.vastum;

import java.util.ArrayList;

public class ProductsSectionsModel {
    private String headerTitle;
    private ArrayList<ProductsInfo> allItemsInSection;

    public ProductsSectionsModel() {
    }

    public ProductsSectionsModel(String headerTitle, ArrayList<ProductsInfo> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<ProductsInfo> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<ProductsInfo> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }
}
