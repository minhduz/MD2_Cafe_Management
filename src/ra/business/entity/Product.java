package ra.business.entity;

import java.io.Serializable;

public class Product implements Serializable {
    private String productID;
    private String productName;
    private double productPrice;
    private Catalog productCatalog;
    private boolean productStatus;

    public Product() {
    }

    public Product(String productID, String productName, double productPrice, Catalog productCatalog, boolean productStatus) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCatalog = productCatalog;
        this.productStatus = productStatus;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public Catalog getProductCatalog() {
        return productCatalog;
    }

    public void setProductCatalog(Catalog productCatalog) {
        this.productCatalog = productCatalog;
    }

    public boolean isProductStatus() {
        return productStatus;
    }

    public void setProductStatus(boolean productStatus) {
        this.productStatus = productStatus;
    }
}
