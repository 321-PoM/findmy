package com.example.findmy;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;

    private String surname;

    private String preferredName;

    private String username;

    private String email;
    private double reliabilityScore;

    private List<User> friends;

    private String avatarURI;

    private boolean hasPremium;

    public User(int id, String surname, String preferredName, String username, String email, double reliabilityScore, String avatarURI, boolean hasPremium) {
        this.id = id;
        this.surname = surname;
        this.preferredName = preferredName;
        this.username = username;
        this.reliabilityScore = reliabilityScore;
        this.email = email;
        friends = new ArrayList<User>();
        this.avatarURI = avatarURI;
        this.hasPremium = hasPremium;
    }

    public int getId() { return this.id; }

    public String getUsername() { return this.username; }

    public String getFullName() { return this.preferredName + " " + this.surname; }

    public double getReliabilityScore() { return this.reliabilityScore; }

    public List<User> getFriends() { return this.friends; }

    public String getAvatarURI() { return this.avatarURI; }

    public boolean isHasPremium() { return this.hasPremium; }
}
