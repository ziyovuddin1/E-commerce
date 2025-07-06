package org.example.model;

import java.io.Serializable;

public class Card implements Serializable {
    private int id;
    private String userId;
    private String details;

    public Card() {
    }

    public Card(int id, String userId, String details) {
        this.id = id;
        this.userId = userId;
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "💳 Card: " + id + " - User: " + userId + " - Details: " + details;
    }
}
