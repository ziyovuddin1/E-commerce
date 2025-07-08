package org.example.model;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private int categoryId;
    private String name;

    public Product() {
    }

    public Product(int id, int categoryId, String name) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "📦 Product: " + id + " - " + name + " (Category: " + categoryId + ")";
    }
}
