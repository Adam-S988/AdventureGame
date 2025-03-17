package com.keyin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NPC implements Serializable {
    private String name;
    private String description;
    private String dialogue;
    private String requiredItem;
    private boolean hasGivenKey;

    public NPC(String name, String description, String dialogue) {
        this.name = name;
        this.description = description;
        this.dialogue = dialogue;
        this.hasGivenKey = false;
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

    public void talk(Player player) {
        // Print the NPC dialogue with color formatting
        System.out.println("\n\u001B[36m" + name + "\u001B[0m: " + "\u001B[34m'" + dialogue + "'\u001B[0m");
        if (name.equals("Mysterious Figure") && !hasGivenKey) {
            Item key = new Item("Mysterious Key", "A strange, old key with intricate markings.");
            player.addItem(key);
            System.out.println("The Mysterious Figure hands you a key.");

            hasGivenKey = true;
        }
    }
}
