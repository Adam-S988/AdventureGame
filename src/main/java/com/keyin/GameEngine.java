package com.keyin;

import java.io.*;
import java.util.Scanner;

public class GameEngine {
    private WorldMap worldMap;
    private Player player;
    private Scanner scanner;
    private boolean isFirstRun;

    public GameEngine() {
        worldMap = new WorldMap();
        scanner = new Scanner(System.in);
        String startingLocationName = "Quaint House";
        Locations startingLocation = worldMap.getLocation(startingLocationName);
        player = new Player("Player1", startingLocation);
        isFirstRun = true;
    }

    public void startGame() {
        if (isFirstRun) {
            System.out.println("\nWelcome to the Adventure Game!");
            System.out.println("\nType 'talk to [NPC]' to talk. Type 'quit' to exit.\n");
            System.out.println("\nYou have woken up, not exactly sure where you are.");
            System.out.println("Your head feels heavy, as if a fog has settled in your mind, clouding your thoughts.");
            scanner.nextLine();
            System.out.println("You slowly open your eyes, but the surroundings are unfamiliar. The walls are made of weathered stone, and the dim light from a small window reveals\nnothing but shadows.");
            System.out.println("As you look around the room, you realize that nothing seems familiar... not the furniture, not the decorations, not even the smell of the air.");
            System.out.println("A figure stands across the room. A person, their face obscured by the dim light, staring at you. You can't remember how they got there, or even if you know them.");
            System.out.println("The person speaks, their voice soft yet filled with urgency: 'Are you alright? Do you remember anything?'");
            scanner.nextLine();
            System.out.println("You try to speak, but your own voice sounds distant, like it's coming from someone else. You struggle to remember who you are, but your mind remains blank, like a\npage waiting to be written.");
            System.out.println("Your memory seems fractured, pieces of it slipping away just as quickly as you try to grasp them. Who are you? How did you get here?");
            System.out.println("The person in front of you waits for a response, concern in their eyes, but you can't shake the feeling that something isn't quite right...");
            scanner.nextLine();
            isFirstRun = false;
        }

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
                    if (npc.getName().equalsIgnoreCase("River Guard")) {
                        if (player.hasItem("Crossing Permit")) {
                            System.out.println("\n\u001B[36mRiver Guard\u001B[0m: \u001B[34m'Ah, you have a Crossing Permit! You may pass safely.'\u001B[0m");
                        } else {
                            System.out.println("\n\u001B[36mRiver Guard\u001B[0m: \u001B[34m'Stop! You cannot cross without a permit.'\u001B[0m");
                        }
                    }
                    else if (npc.getName().equalsIgnoreCase("Elder Rowan")) {
                        if (player.hasItem("Golden Apple")) {
                            System.out.println("\n\u001B[36mRiver Guard\u001B[0m: \u001B[34m'You found the Apple?! Bless. As a reward, I will teach you a secret...'\u001B[0m");
                        } else {
                            System.out.println("\n\u001B[36mRiver Guard\u001B[0m: \u001B[34m'I have heard that there is a legendary Apple made of pure gold!'\u001B[0m");
                        }
                    } else {
                        npc.talk(player);
                    }
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
            } else if (input.startsWith("read ")) {
                player.readItem();
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
            } else {
                System.out.println("Invalid command.");
            }
        }
    }

    private void movePlayer(String direction) {
        Locations currentLocation = player.getLocation();
        Locations nextLocation = currentLocation.getExit(direction);
        if (nextLocation != null) {
            if (nextLocation.getRequiredItem() != null && !player.hasItem(nextLocation.getRequiredItem())) {
                System.out.println("\nYou need a \u001B[33m" + nextLocation.getRequiredItem() + "\u001B[0m to enter this area.");
                return;
            }
            player.setLocation(nextLocation);
            System.out.println("\nYou move " + direction + " to: \u001B[35m" + nextLocation.getName() + "\u001B[0m");
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
        if (player.getVisitedLocations().contains("Sunspire Shrine")) {
            System.out.println("\u001B[35mSunspire Shrine" + "\u001B[0m");
            System.out.println("Exits: west");
        }
        if (player.getVisitedLocations().contains("Bryn Field")) {
            System.out.println("\u001B[35mBryn Field" + "\u001B[0m");
            System.out.println("Exits: east");
        }
        System.out.println("------------------");
    }
}