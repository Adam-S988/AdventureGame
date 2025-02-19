package com.keyin;

public class NPCs {
    private String name;
    private String description;
    private String dialogue;

    public NPCs(String name, String description, String dialogue) {
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

    public void talk() {
        System.out.println(name + " says: " + dialogue);
    }
}
