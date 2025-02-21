package com.keyin;

import java.util.List;

public class Quest {
    private String title;
    private String description;
    private List<String> tasks;
    private String reward;
    private boolean isCompleted;
    private int currentTaskIndex;

    public Quest(String title, String description, List<String> tasks, String reward) {
        this.title = title;
        this.description = description;
        this.tasks = tasks;
        this.reward = reward;
        this.isCompleted = false;
        this.currentTaskIndex = 0;
    }

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
        return isCompleted;
    }

    public String getCurrentTask() {
        if (currentTaskIndex < tasks.size()) {
            return tasks.get(currentTaskIndex);
        }
        return "No more tasks";  // If all tasks are completed
    }

    public void checkProgress(Player player) {
        if (currentTaskIndex < tasks.size()) {
            String currentTask = tasks.get(currentTaskIndex);
            System.out.println("Current Task: " + currentTask);

            if (player.hasCompletedTask(currentTask)) {
                System.out.println("You have completed this task: " + currentTask);
                currentTaskIndex++;
            } else {
                System.out.println("You haven't completed this task yet.");
            }
        }

        if (currentTaskIndex == tasks.size()) {
            isCompleted = true;
            System.out.println("Quest completed: " + title);
        }
    }
}
