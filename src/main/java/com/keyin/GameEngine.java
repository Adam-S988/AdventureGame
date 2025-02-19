package com.keyin;

import java.util.Scanner;

public class GameEngine {
    private Player player;
    private Scanner scanner;

    public GameEngine() {
        this.scanner = new Scanner(System.in);
        setupWorld();
    }

    private void setupWorld() {
        // Create Locationss
        Locations town = new Locations("Town", "A small town with a bustling marketplace.");
        Locations forest = new Locations("Forest", "A dark forest with towering trees.");
        Locations mountain = new Locations("Mountain", "A rocky mountain with a narrow path leading up.");
        Locations river = new Locations("River", "A fast-flowing river with a wooden bridge.");
        Locations graveyard = new Locations("Graveyard", "A small graveyard that appears overgrown. Many broken graves dot the space.");

        // Connect Locationss
        town.setExits("north", forest);
        forest.setExits("south", town);
        forest.setExits("east", mountain);
        mountain.setExits("west", forest);
        town.setExits("west", river);
        river.setExits("east", town);
        graveyard.setExits("west", town);
        town.setExits("east", graveyard);


        // Add NPCs
        NPCs oldMan = new NPCs("Old Man", "A frail man with a long, white beard.", "'The world isn't as safe as it once was...'");
        NPCs ghost = new NPCs("Ghost", "A translucent figure floating above the ground.", "'Beware... the darkness beyond...'");
        town.addNPC(oldMan);
        graveyard.addNPC(ghost);

        // Set player's starting position
        this.player = new Player("Hero", town);
    }

    public void startGame() {
        System.out.println("\nWelcome to the Adventure Game!");
        System.out.println("\nType 'go [direction]' to move. Type 'talk to [NPC]' to talk. Type 'quit' to exit.\n");

        while (true) {
            System.out.println("You are in: " + player.getLocation().getName());
            System.out.println(player.getLocation().getDescription());
            player.getLocation().listNPCs();
            player.getLocation().printExits();


            System.out.print("> ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("quit")) {
                System.out.println("Goodbye!");
                break;
            } else if (input.startsWith("go ")) {
                String direction = input.substring(3).trim();
                movePlayer(direction);
            } else if (input.startsWith("talk to ")) {
                String npcName = input.substring(8).trim();
                NPCs npc = player.getLocation().findNPC(npcName);
                if (npc != null) {
                    npc.talk();
                } else {
                    System.out.println("There's no one by that name here.");
                }
            } else {
                System.out.println("Invalid command.");
            }

        }

    }

    private void movePlayer(String direction) {
        Locations nextLocations = player.getLocation().getExit(direction);
        if (nextLocations != null) {
            player.setLocation(nextLocations);
            System.out.println("\nYou moved " + direction + ".");
        } else {
            System.out.println("You can't go that way.");
        }
    }
}