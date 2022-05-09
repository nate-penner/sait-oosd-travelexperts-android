package oosd.sait.travelexperts.data;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

/**
 * Data class representing a supplier record
 * @author Nate Penner
 * */
public class Supplier implements Serializable {
    private int id;
    private String name;
    private List<Product> products;

    public Supplier(int id, String name, List<Product> products) {
        this.id = id;
        this.name = name;
        this.products = products;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
