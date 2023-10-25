package com.example.findmy;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;

    private String name;

    private String email;
    private double reliabilityScore;

    private List<User> friends;

    private String avatarURI;

    private boolean hasPremium;

    public User(int id, String name, String email, double reliabilityScore, String avatarURI, boolean hasPremium) {
        this.id = id;
        this.name = name;
        this.reliabilityScore = reliabilityScore;
        this.email = email;
        friends = new ArrayList<User>();
        this.avatarURI = avatarURI;
        this.hasPremium = hasPremium;
    }

    public int getId() { return this.id; }

    public String getName() { return this.name; }

    public double getReliabilityScore() { return this.reliabilityScore; }

    public List<User> getFriends() { return this.friends; }

    public String getAvatarURI() { return this.avatarURI; }

    public boolean isHasPremium() { return this.hasPremium; }
}
