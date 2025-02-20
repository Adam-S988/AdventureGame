package com.keyin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Locations location;
    List<Item> inventory;

    public Player(String name, Locations startingLocation) {
        this.name = name;
        this.location = startingLocation;
        this.inventory = new ArrayList<>();
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
}
