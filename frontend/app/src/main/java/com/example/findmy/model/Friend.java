package com.example.findmy.model;

public class Friend extends User {
    private Boolean isConfirmed;

    public Friend(User user, Boolean isConfirmed) {
        super(user);
        this.isConfirmed = isConfirmed;
    }

    public Boolean isConfirmed() {
        return isConfirmed;
    }
}
