package com.keyin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Locations location;
    List<Item> inventory;
    private HashSet<String> visitedLocations;  // Track visited locations

    public Player(String name, Locations startingLocation) {
        this.name = name;
        this.location = startingLocation;
        this.inventory = new ArrayList<>();
        this.visitedLocations = new HashSet<>();  // Initialize the visited locations set
        visitedLocations.add(startingLocation.getName());  // Add the starting location to visited
    }

    public Locations getLocation() {
        return location;
    }

    public void setLocation(Locations location) {
        this.location = location;
        visitedLocations.add(location.getName());  // Add to visited locations when the player moves
    }

    public String getName() {
        return name;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    public void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("\nYour inventory is empty.");
        } else {
            System.out.println("\nYou are carrying:");
            for (Item item : inventory) {
                System.out.println("- " + item);
            }
        }
    }

    // New method to get the visited locations
    public HashSet<String> getVisitedLocations() {
        return visitedLocations;
    }
}
