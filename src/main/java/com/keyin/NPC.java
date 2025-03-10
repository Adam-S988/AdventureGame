package com.keyin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NPC implements Serializable {
    private String name;
    private String description;
    private String dialogue;
    private String requiredItem;

    public NPC(String name, String description, String dialogue) {
        this.name = name;
        this.description = description;
        this.dialogue = dialogue;
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
    }
}
