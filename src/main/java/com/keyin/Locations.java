package com.keyin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Locations implements Serializable {
    private static final long serialVersionUID = 1L; // Optional: Add a serialVersionUID for version control of serialized objects
    private String name;
    private String description;
    private Map<String, Locations> exits;
    private List<NPC> npcs;
    private List<Item> items;

    public Locations(String name, String description) {
        this.name = name;
        this.description = description;
        this.exits = new HashMap<>();
        this.npcs = new ArrayList<>();
        this.items = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Locations getExit(String direction) {
        return exits.get(direction);
    }

    public void setExits(String direction, Locations location) {
        exits.put(direction, location);
    }

    public Map<String, Locations> getExits() {
        return exits;
    }

    public void printExits() {
        System.out.print("Exits: ");
        for (String direction : exits.keySet()) {
            System.out.print(direction + " ");
        }
        System.out.println();
    }

    // New Methods for NPCs
    public void addNPC(NPC npc) {
        npcs.add(npc);
    }

    public void listNPCs() {
        if (npcs.isEmpty()) {
            System.out.println("No one is here.");
        } else {
            System.out.println("You see:");
            for (NPC npc : npcs) {
                System.out.println("- " + npc.getName() + ": " + npc.getDescription());
            }
        }
    }

    public NPC findNPC(String name) {
        for (NPC npc : npcs) {
            if (npc.getName().equalsIgnoreCase(name)) {
                return npc;
            }
        }
        return null;
    }

    // New Methods for Items
    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public Item findItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public void listItems() {
        if (items.isEmpty()) {
            System.out.println("\nThere are no items here.");
        } else {
            System.out.println("\nYou see the following items:");
            for (Item item : items) {
                System.out.println("- " + item);
            }
        }
    }
}
