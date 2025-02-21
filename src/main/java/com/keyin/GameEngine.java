package com.keyin;

import java.io.*;
import java.util.Scanner;

public class GameEngine {
    private WorldMap worldMap;
    private Player player;
    private Scanner scanner;

    public GameEngine() {
        worldMap = new WorldMap();
        scanner = new Scanner(System.in);
        String startingLocationName = "Westgate";
        Locations startingLocation = worldMap.getLocation(startingLocationName);
        player = new Player("Player1", startingLocation);
    }

    public void startGame() {
        // Game intro and instructions
        System.out.println("\nWelcome to the Adventure Game!");
        System.out.println("\nType 'talk to [NPC]' to talk. Type 'quit' to exit.\n");

        while (true) {
            System.out.println("\nYou are in: \u001B[35m" + player.getLocation().getName() + "\u001B[0m");
            System.out.println(player.getLocation().getDescription());
            player.getLocation().listNPCs();
            player.getLocation().printExits();

            System.out.print(">");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("quit")) {
                System.out.println("Goodbye!");
                break;
            } else if (input.startsWith("go ")) {
                String direction = input.substring(3).trim();
                movePlayer(direction);
            } else if (input.equals("map")) {
                showMap();
            } else if (input.startsWith("talk to ")) {
                String npcName = input.substring(8).trim();
                NPC npc = player.getLocation().findNPC(npcName);
                if (npc != null) {
                    npc.talk();
                    npc.showQuests();
                } else {
                    System.out.println("\nThere's no one by that name here.");
                }
            } else if (input.equals("look")) {
                player.getLocation().listItems();
            } else if (input.startsWith("take ")) {
                String itemName = input.substring(5).trim();
                Item item = player.getLocation().findItem(itemName);
                if (item != null) {
                    player.getLocation().removeItem(item);
                    player.addItem(item);
                    System.out.println("\nYou picked up the \u001B[33m" + itemName + "\u001B[0m.");
                } else {
                    System.out.println("\nThere's no item by that name here.");
                }
            } else if (input.startsWith("enter ")) {
                String locationName = input.substring(6).trim();
                Locations targetLocation = player.getLocation().getExit(locationName);
                if (targetLocation != null) {
                    player.setLocation(targetLocation);
                    System.out.println("You have moved to \u001B[35m" + targetLocation.getName() + "\u001B[0m.");
                } else {
                    System.out.println("That location doesn't exist.");
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
            } else if (input.equals("quests")) {
                player.showQuests();
            } else if (input.startsWith("accept quest ")) {
                String questTitle = input.substring(13).trim();
                acceptQuestFromCurrentLocation(questTitle);
            } else if (input.startsWith("complete quest ")) {
                String questTitle = input.substring(14).trim();
                player.completeQuest(questTitle);
            } else {
                System.out.println("Invalid command. Type 'talk to guide' for help.");
            }
        }
    }

    private void acceptQuestFromCurrentLocation(String questTitle) {
        boolean questFound = false;
        for (NPC npc : player.getLocation().getNPCs()) {
            Quest quest = npc.getQuestByTitle(questTitle);
            if (quest != null) {
                // Check if the player already has this quest
                boolean alreadyHasQuest = false;
                for (Quest activeQuest : player.getActiveQuests()) {
                    if (activeQuest.getTitle().equalsIgnoreCase(questTitle)) {
                        alreadyHasQuest = true;
                        break;
                    }
                }

                if (alreadyHasQuest) {
                    System.out.println("You have already accepted this quest!");
                } else {
                    player.acceptQuest(quest);
                    System.out.println("Quest accepted from " + npc.getName() + "!");
                    System.out.println("Description: " + quest.getDescription());
                    System.out.println("First task: " + quest.getCurrentTask());
                }
                questFound = true;
                break;
            }
        }

        if (!questFound) {
            System.out.println("No such quest available in this location.");
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

    private void saveGame() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("game_save.dat"))) {
            oos.writeObject(player);
            System.out.println("Game saved!");
        } catch (IOException e) {
            System.out.println("Error saving game: " + e.getMessage());
        }
    }

    private void loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("game_save.dat"))) {
            player = (Player) ois.readObject();
            System.out.println("Game loaded!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading game: " + e.getMessage());
        }
    }

    public void showMap() {
        System.out.println("\n--- World Map ---");

        // Loop through each location and check if the player has visited it
        if (player.getVisitedLocations().contains("Westgate")) {
            System.out.println("\u001B[35mWestgate" + "\u001B[0m");
            System.out.println("Exits: east north west");
        }
        if (player.getVisitedLocations().contains("Woods of the Night")) {
            System.out.println("\u001B[35mWoods of the Night" + "\u001B[0m");
            System.out.println("Exits: east south");
        }
        if (player.getVisitedLocations().contains("Mount Troyal")) {
            System.out.println("\u001B[35mMount Troyal" + "\u001B[0m");
            System.out.println("Exits: west");
        }
        if (player.getVisitedLocations().contains("Troyal River")) {
            System.out.println("\u001B[35mTroyal River" + "\u001B[0m");
            System.out.println("Exits: east");
        }
        if (player.getVisitedLocations().contains("Westgate Graveyard")) {
            System.out.println("\u001B[35mWestgate Graveyard" + "\u001B[0m");
            System.out.println("Exits: west");
        }
        System.out.println("------------------");
    }
}