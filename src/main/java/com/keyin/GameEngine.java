package com.keyin;

import java.io.*;
import java.util.Scanner;

public class GameEngine {
    private Player player;
    private Scanner scanner;

    public GameEngine() {
        this.scanner = new Scanner(System.in);
        setupWorld();
    }

    private void setupWorld() {
        // Create Locations
        Locations town = new Locations("Town", "A small town with a bustling marketplace.");
        Locations forest = new Locations("Forest", "A dark forest with towering trees.");
        Locations mountain = new Locations("Mountain", "A rocky mountain with a narrow path leading up.");
        Locations river = new Locations("River", "A fast-flowing river with a wooden bridge.");
        Locations graveyard = new Locations("Graveyard", "A small graveyard that appears overgrown. Many broken graves dot the space.");

        // Connect Locations
        town.setExits("north", forest);
        forest.setExits("south", town);
        forest.setExits("east", mountain);
        mountain.setExits("west", forest);
        town.setExits("west", river);
        river.setExits("east", town);
        graveyard.setExits("west", town);
        town.setExits("east", graveyard);

        // Add NPCs
        NPC oldMan = new NPC("Old Man", "A frail man with a long, white beard.", "The world isn't as safe as it once was...");
        town.addNPC(oldMan);
        NPC ghost = new NPC("Ghost", "A translucent figure floating above the ground.", "Beware... the darkness beyond...");
        graveyard.addNPC(ghost);
        NPC riverGuard = new NPC("River Guard", "A serious looking man who watches over those who cross the river.", "I'm sorry, but I cannot let you cross the river.");
        river.addNPC(riverGuard);

        // Add Items
        town.addItem(new Item("Lantern", "An old lantern with a faint glow."));
        forest.addItem(new Item("Stick", "A sturdy wooden stick, perfect for walking or self-defense."));
        graveyard.addItem(new Item("Amulet", "A mysterious amulet engraved with ancient symbols."));
        river.addItem(new Item("Coin Purse", "A ratty coin purse someone must have dropped. It has a few old coins in it."));

        // Set player's starting position
        this.player = new Player("Hero", town);
    }

    public void startGame() {
        System.out.println("\nWelcome to the Adventure Game!");
        System.out.println("\nType 'go [direction]' to move. Type 'talk to [NPC]' to talk. Type 'look' to look for items. Type 'inventory' to view inventory. Type 'save' to save your game. Type 'load' to load your game. Type 'quit' to exit.\n");

        while (true) {
            System.out.println("\nYou are in: " + player.getLocation().getName());
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
                NPC npc = player.getLocation().findNPC(npcName);
                if (npc != null) {
                    npc.talk();
                } else {
                    System.out.println("\nThere's no one by that name here.");
                }
            } else if (input.equals("look")) {
                player.getLocation().listItems();  // Show items in the current location
            } else if (input.startsWith("take ")) {
                String itemName = input.substring(5).trim();
                Item item = player.getLocation().findItem(itemName);
                if (item != null) {
                    player.getLocation().removeItem(item);
                    player.addItem(item);
                    System.out.println("\nYou picked up the " + itemName + ".");
                } else {
                    System.out.println("\nThere's no item by that name here.");
                }
            } else if (input.startsWith("drop ")) {
                String itemName = input.substring(5).trim();
                Item itemToDrop = null;
                for (Item item : player.inventory) {
                    if (item.getName().equalsIgnoreCase(itemName)) {
                        itemToDrop = item;
                        break;
                    }
                }
                if (itemToDrop != null) {
                    player.inventory.remove(itemToDrop);
                    player.getLocation().addItem(itemToDrop);
                    System.out.println("\nYou dropped the " + itemName + ".");
                } else {
                    System.out.println("\nYou don't have that item.");
                }
            } else if (input.equals("inventory")) {
                player.showInventory();
            } else if (input.equals("save")) {
                saveGame();
            } else if (input.equals("load")) {
                loadGame();
            } else {
                System.out.println("Invalid command.");
            }
        }
    }

    private void movePlayer(String direction) {
        Locations nextLocation = player.getLocation().getExit(direction);
        if (nextLocation != null) {
            player.setLocation(nextLocation);
            System.out.println("\nYou moved " + direction + ".");
        } else {
            System.out.println("You can't go that way.");
        }
    }

    // Save game state (Player's Location and Inventory)
    private void saveGame() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("game_save.dat"))) {
            oos.writeObject(player);  // Save the player object (location and inventory)
            System.out.println("Game saved!");
        } catch (IOException e) {
            System.out.println("Error saving game: " + e.getMessage());
        }
    }

    // Load game state (Player's Location and Inventory)
    private void loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("game_save.dat"))) {
            player = (Player) ois.readObject();  // Load the player object (location and inventory)
            System.out.println("Game loaded!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading game: " + e.getMessage());
        }
    }
}
