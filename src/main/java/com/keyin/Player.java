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
    private List<Quest> activeQuests;
    private List<Quest> completedQuests;

    public Player(String name, Locations startingLocation) {
        this.name = name;
        this.location = startingLocation;
        this.inventory = new ArrayList<>();
        this.visitedLocations = new HashSet<>();
        visitedLocations.add(startingLocation.getName());
        this.activeQuests = new ArrayList<>();
        this.completedQuests = new ArrayList<>();
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

    public List<Quest> getActiveQuests() {
        return activeQuests;
    }

    public void acceptQuest(Quest quest) {
        activeQuests.add(quest);
        System.out.println("New Quest Accepted: " + quest.getTitle());
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

    public void completeQuest(String questName) {
        Quest completed = null;

        // Loop through active quests to find the quest to complete
        for (Quest quest : activeQuests) {
            if (quest.getTitle().equalsIgnoreCase(questName)) {
                // Check quest progress and mark as completed if necessary
                quest.checkProgress(this); // Check the progress of the quest

                if (quest.isCompleted()) {
                    completed = quest;  // Only mark as completed if it's fully done
                    break;
                }
            }
        }

        if (completed != null) {
            activeQuests.remove(completed);  // Remove from active quests
            completedQuests.add(completed);  // Add to completed quests
            System.out.println("Quest completed: " + completed.getTitle());
            System.out.println("Reward: " + completed.getReward());
        } else {
            System.out.println("No such quest found or not yet completed.");
        }
    }


    public void showQuests() {
        if (activeQuests.isEmpty() && completedQuests.isEmpty()) {
            System.out.println("You have no quests.");
        } else {
            System.out.println("Your Active Quests:");
            for (Quest quest : activeQuests) {
                String status = quest.isCompleted() ? "Completed" : "In Progress";
                System.out.println("\u001B[32m" + quest.getTitle() + " \u001B[0m- " + status);
                System.out.println("Description: " + quest.getDescription());
                System.out.println("Current Task: " + quest.getCurrentTask());
                System.out.println("Reward: " + quest.getReward());
                System.out.println();
            }

            System.out.println("Your Completed Quests:");
            for (Quest quest : completedQuests) {
                System.out.println(quest.getTitle() + " - Completed");
                System.out.println("Description: " + quest.getDescription());
                System.out.println("Reward: " + quest.getReward());
                System.out.println();
            }
        }
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
