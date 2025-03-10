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
    private HashSet<String> visitedLocations;

    public Player(String name, Locations startingLocation) {
        this.name = name;
        this.location = startingLocation;
        this.inventory = new ArrayList<>();
        this.visitedLocations = new HashSet<>();
        visitedLocations.add(startingLocation.getName());
    }

    public Locations getLocation() {
        return location;
    }

    public void setLocation(Locations location) {
        this.location = location;
        visitedLocations.add(location.getName());
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

    public HashSet<String> getVisitedLocations() {
        return visitedLocations;
    }

    // Method to check if the player has completed a specific task
    public boolean hasCompletedTask(String task) {
        for (Item item : inventory) {  // Example: Checking if the player has a required item
            if (item.getName().equalsIgnoreCase(task)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }
}
