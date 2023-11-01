package com.example.findmy.user;

import java.util.ArrayList;
import java.util.List;

public class Usero {

    public static final Usero testUser = new Usero(0, "John", "Doe", "jdoe123", "jdoe@gmail.com", 1.0, "smth.com/avatar", true);
    private int id;

    private String surname;

    private String preferredName;

    private String username;

    private String email;
    private double reliabilityScore;

    private List<Usero> friends;

    private String avatarURI;

    private boolean hasPremium;

    public Usero(int id, String surname, String preferredName, String username, String email, double reliabilityScore, String avatarURI, boolean hasPremium) {
        this.id = id;
        this.surname = surname;
        this.preferredName = preferredName;
        this.username = username;
        this.reliabilityScore = reliabilityScore;
        this.email = email;
        friends = new ArrayList<Usero>();
        this.avatarURI = avatarURI;
        this.hasPremium = hasPremium;
    }

    public int getId() { return this.id; }

    public String getUsername() { return this.username; }

    public String getFullName() { return this.preferredName + " " + this.surname; }

    public double getReliabilityScore() { return this.reliabilityScore; }

    public List<Usero> getFriends() { return this.friends; }

    public String getAvatarURI() { return this.avatarURI; }

    public boolean isHasPremium() { return this.hasPremium; }
}
