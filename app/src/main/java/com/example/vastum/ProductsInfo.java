package com.example.vastum;


public class ProductsInfo {
    private String ProductCategory,ProductBrand,ProductID,ProductName,ProductType,ProductAge,ProductPoints,ProductFirstImageURI;
    private String ProductImageUri;
    public ProductsInfo(String productID){
        ProductID = productID;
        ProductCategory="";
        ProductBrand="";
        ProductName="";
        ProductType="";
        ProductAge="";
        ProductPoints="";
        ProductFirstImageURI = "";
        ProductImageUri ="";
    }

    public String getProductFirstImageURI() {
        return ProductFirstImageURI;
    }

    public void setProductFirstImageURI(String productFirstImageURI) {
        ProductFirstImageURI = productFirstImageURI;
    }

    public String getProductImageUri() {
        return ProductImageUri;
    }

    public void setProductImageUri(String ImageURI) {
        ProductImageUri+="," + ImageURI;
    }

    public String getProductCategory() {
        return ProductCategory;
    }

    public void setProductCategory(String productCategory) {
        ProductCategory = productCategory;
    }

    public String getProductBrand() {
        return ProductBrand;
    }

    public void setProductBrand(String productBrand) {
        ProductBrand = productBrand;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String productType) {
        ProductType = productType;
    }

    public String getProductAge() {
        return ProductAge;
    }

    public void setProductAge(String productAge) {
        ProductAge = productAge;
    }

    public String getProductPoints() {
        return ProductPoints;
    }

    public void setProductPoints(String productPoints) {
        ProductPoints = productPoints;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }


    public ProductsInfo() {
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }


}
