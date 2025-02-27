package com.keyin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NPC implements Serializable {
    private String name;
    private String description;
    private String dialogue;
    private List<Quest> quests;
    private String requiredItem;

    public NPC(String name, String description, String dialogue) {
        this.name = name;
        this.description = description;
        this.dialogue = dialogue;
        this.quests = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDialogue() {
        return dialogue;
    }

    public void talk() {
        System.out.println("\n\u001B[36m" + name + "\u001B[0m: " + "\u001B[34m'" + dialogue + "'\u001B[0m");
    }

    public void addQuest(Quest quest) {
        quests.add(quest);
        System.out.println("Quest added to " + name + ": " + quest.getTitle());
    }

    public void showQuests() {
        if (quests.isEmpty()) {
            System.out.println(name + " has no quests available.");
        } else {
            System.out.println(name + "'s Quests:");
            for (Quest quest : quests) {
                System.out.println("\u001B[32m" + quest.getTitle() + " \u001B[0m- " + (quest.isCompleted() ? "Completed" : "In Progress"));
                System.out.println("Description: " + quest.getDescription());
                System.out.println("Current Task: " + quest.getCurrentTask());
                System.out.println("Reward: " + quest.getReward());
                System.out.println();
            }
        }
    }

    public Quest getQuestByTitle(String title) {
        for (Quest quest : quests) {
            if (quest.getTitle().equalsIgnoreCase(title)) {
                return quest;
            }
        }
        return null;
    }
}
