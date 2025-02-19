package com.keyin;

public class Player {
    private String name;
    private Locations location;

    public Player(String name, Locations startingLocation) {
        this.name = name;
        this.location = startingLocation;
    }

    public Locations getLocation() {
        return location;
    }

    public void setLocation(Locations location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }
}
