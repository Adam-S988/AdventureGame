package com.keyin;

import java.io.*;
import java.util.Scanner;

public class GameEngine {
    private WorldMap worldMap;
    private Player player;
    private Scanner scanner;  // Declare the scanner

    public GameEngine() {
        worldMap = new WorldMap();
        scanner = new Scanner(System.in);  // Initialize the scanner
        String startingLocationName = "Westgate";
        Locations startingLocation = worldMap.getLocation(startingLocationName);  // Get the location from the world map
        player = new Player("Player1", startingLocation); // Pass location name as a String
    }

    public void startGame() {
        // Game intro and instructions
        System.out.println("\nWelcome to the Adventure Game!");
        System.out.println("\nType 'go [direction]' to move. Type 'talk to [NPC]' to talk. Type 'look' to look for items. Type 'map' to view the world map. Type 'inventory' to view inventory. Type 'save' to save your game. Type 'load' to load your game. Type 'quit' to exit.\n");

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
            } else if (input.equals("map")) {
                showMap();  // Show the world map to the player
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
            } else if (input.startsWith("go to ")) {
                String locationName = input.substring(6).trim();
                Locations targetLocation = worldMap.getLocation(locationName);
                if (targetLocation != null) {
                    player.setLocation(targetLocation);
                    System.out.println("You have moved to " + locationName + ".");
                } else {
                    System.out.println("That location doesn't exist.");
                }
            } else {
                System.out.println("Invalid command.");
            }
        }
    }

    private void movePlayer(String direction) {
        Locations currentLocation = player.getLocation();
        Locations nextLocation = currentLocation.getExit(direction);
        if (nextLocation != null) {
            player.setLocation(nextLocation);
            System.out.println("\nYou move " + direction + " to: " + nextLocation.getName());
            System.out.println(nextLocation.getDescription());
        } else {
            System.out.println("\nYou cannot move in that direction.");
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

    public void showMap() {
        System.out.println("\n--- World Map ---");

        // Loop through each location and check if the player has visited it
        if (player.getVisitedLocations().contains("Westgate")) {
            System.out.println("Westgate: A small town with a bustling marketplace.");
            System.out.println("Exits: east north west");
        }
        if (player.getVisitedLocations().contains("Woods of the Night")) {
            System.out.println("Woods of the Night: A dark forest with towering trees.");
            System.out.println("Exits: east south");
        }
        if (player.getVisitedLocations().contains("Mount Troyal")) {
            System.out.println("Mount Troyal: A rocky mountain with a narrow path leading up.");
            System.out.println("Exits: west");
        }
        if (player.getVisitedLocations().contains("Troyal River")) {
            System.out.println("Troyal River: A fast-flowing river with a wooden bridge.");
            System.out.println("Exits: east");
        }
        if (player.getVisitedLocations().contains("Westgate Graveyard")) {
            System.out.println("Westgate Graveyard: A small graveyard that appears overgrown. Many broken graves dot the space.");
            System.out.println("Exits: west");
        }
        System.out.println("------------------");
    }
}
