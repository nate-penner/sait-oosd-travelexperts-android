package oosd.sait.travelexperts.data;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Product implements Serializable {
    private int productId;
    private String productName;

    public Product(int productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @NonNull
    @Override
    public String toString() {
        return productName;
    }
}
