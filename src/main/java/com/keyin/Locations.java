package com.keyin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Locations implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String description;
    private Map<String, Locations> exits;
    private Map<String, Locations> additionalExits;
    private List<NPC> npcs;
    private List<Item> items;

    public Locations(String name, String description) {
        this.name = name;
        this.description = description;
        this.exits = new HashMap<>();
        this.additionalExits = new HashMap<>();
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

    public List<NPC> getNPCs() {
        return npcs;  // Assuming you have a private List<NPC> npcs field
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Locations getExit(String direction) {
        Locations exit = exits.get(direction);  // Check regular exits first
        if (exit == null) {
            exit = additionalExits.get(direction);  // Then check additional exits
        }
        return exit;
    }

    public void addExit(String direction, Locations location) {
        additionalExits.put(direction, location);  // Add to additionalExits
    }

    public void setExits(String direction, Locations location) {
        exits.put(direction, location);  // Add to regular exits
    }

    public Map<String, Locations> getExits() {
        return exits;
    }

    public void printExits() {
        if (!exits.isEmpty() || !additionalExits.isEmpty()) {
            StringBuilder output = new StringBuilder();

            // Handle Exits
            if (!exits.isEmpty()) {
                output.append("Exits: ");
                for (String direction : exits.keySet()) {
                    output.append(direction).append(", ");
                }
                output.delete(output.length() - 2, output.length());
            }

            // Handle Interior Entrances
            if (!additionalExits.isEmpty()) {
                if (output.length() > 0) {
                    output.append(" | ");
                }
                output.append("Interior: ");
                for (String entrance : additionalExits.keySet()) {
                    output.append(entrance).append(", ");
                }
                output.delete(output.length() - 2, output.length());
            }

            // Print the final result
            System.out.println(output);
        }
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
                System.out.println("-\u001B[36m " + npc.getName() + "\u001B[0m: " + npc.getDescription());
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

    // New Method for Navigation
    public Locations getExitToLocation(String direction) {
        Locations exit = exits.get(direction);
        if (exit == null) {
            exit = additionalExits.get(direction);
        }
        return exit;
    }

    private String requiredItem;

    public void setRequiredItem(String item) {
        this.requiredItem = item;
    }

    public String getRequiredItem() {
        return requiredItem;
    }

}
