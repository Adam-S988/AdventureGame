package com.keyin;

import java.io.Serializable;
import java.util.List;

public class Quest implements Serializable {
    private String title;
    private String description;
    private List<String> tasks;
    private String currentTask;
    private String reward;
    private boolean completed;
    private boolean isCompleted;
    private int currentTaskIndex;

    // Constructor
    public Quest(String title, String description, List<String> tasks, String reward) {
        this.title = title;
        this.description = description;
        this.tasks = tasks;
        this.reward = reward;
        this.completed = false;
        this.isCompleted = false;
        this.currentTaskIndex = 0;
        this.currentTask = tasks.get(0);  // Initialize the first task
    }

    // Getter and Setter Methods
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTasks() {
        return tasks;
    }

    public String getReward() {
        return reward;
    }

    public boolean isCompleted() {
        return completed || isCompleted;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getCurrentTask() {
        if (currentTaskIndex < tasks.size()) {
            return tasks.get(currentTaskIndex);
        }
        return "No more tasks";  // If all tasks are completed
    }

    // Method to check the player's progress on the quest
    public void checkProgress(Player player) {
        if (currentTaskIndex < tasks.size()) {
            String currentTask = tasks.get(currentTaskIndex);
            System.out.println("Current Task: " + currentTask);

            if (player.hasCompletedTask(currentTask)) {
                System.out.println("You have completed this task: " + currentTask);
                currentTaskIndex++;
                if (currentTaskIndex < tasks.size()) {
                    currentTask = tasks.get(currentTaskIndex);
                }
            } else {
                System.out.println("You haven't completed this task yet.");
            }
        }

        // Mark the quest as completed once all tasks are done
        if (currentTaskIndex == tasks.size()) {
            if (!isCompleted) {
                isCompleted = true;  // Set quest as completed
                System.out.println("Quest completed: " + title);
            }
        }
    }


    // Auto-complete method based on certain conditions
    public boolean completeAutomatically(Player player) {
        // Example for quest auto-completion based on item "letter" or other conditions
        if (this.title.equals("Deliver the letter")) {
            if (player.hasCompletedTask("letter")) {
                completed = true;
                return true;
            }
        }

        // You can add more auto-completion logic for different quests here
        if (this.title.equals("Find the Golden Apple")) {
            if (player.hasItem("Golden Apple")) {
                System.out.println("Thank you for bringing me the Golden Apple. Here is your reward!");
                completed = true;
                return true;
            }
        }

        return completed;
    }
}
