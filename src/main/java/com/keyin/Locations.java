package com.keyin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Locations {
    private String name;
    private String description;
    private Map<String, Locations> exits;
    private List<NPCs> npcs;  // Fixed here

    public Locations(String name, String description) {
        this.name = name;
        this.description = description;
        this.exits = new HashMap<>();
        this.npcs = new ArrayList<>();  // Initialize the list
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
    public void addNPC(NPCs npc) {
        npcs.add(npc);
    }

    public void listNPCs() {
        if (npcs.isEmpty()) {
            System.out.println("No one is here.");
        } else {
            System.out.println("You see:");
            for (NPCs npc : npcs) {
                System.out.println("- " + npc.getName() + ": " + npc.getDescription());
            }
        }
    }

    public NPCs findNPC(String name) {
        for (NPCs npc : npcs) {
            if (npc.getName().equalsIgnoreCase(name)) {
                return npc;
            }
        }
        return null;
    }
}
