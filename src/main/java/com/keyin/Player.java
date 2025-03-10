package com.keyin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

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

    public void readItem() {
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
            return;
        }

        System.out.println("Your Inventory:");
        for (Item item : inventory) {
            System.out.println("- " + item.getName()); // Assuming Item has getName() method
        }

        System.out.println("Enter the name of the book you want to read:");

        Scanner scanner = new Scanner(System.in);
        String itemName = scanner.nextLine();

        // Find the item in the inventory
        Item item = null;
        for (Item i : inventory) {
            if (i.getName().equalsIgnoreCase(itemName)) {
                item = i;
                break;
            }
        }

        if (item != null) {
            if (item instanceof BookItem) {
                ((BookItem) item).read();  // Cast to BookItem and call read method
            } else {
                System.out.println("You can't read this item.");
            }
        } else {
            System.out.println("You don't have an item with that name in your inventory.");
        }
    }

}
