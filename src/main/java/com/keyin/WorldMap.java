package com.keyin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldMap {
    private Map<String, Locations> locations;

    public WorldMap() {
        this.locations = new HashMap<>();
        setupWorld();
    }

    private void setupWorld() {
        // Define all locations
        Locations town = new Locations("Westgate", "A small town with a bustling marketplace.");
        Locations house1 = new Locations("Quaint House", "A cozy house, perfect for a small family.");
        Locations forest = new Locations("Woods of the Night", "A dark forest with towering trees. A dark cave looms in the north. There is a trail heading off to the east.");
        Locations mountain = new Locations("Mount Troyal", "A rocky mountain with a narrow path leading up.");
        Locations river = new Locations("Troyal River", "A fast-flowing river with a wooden bridge.");
        Locations graveyard = new Locations("Westgate Graveyard", "A small graveyard that appears overgrown. Many broken graves dot the space.");
        Locations darkCave = new Locations("Dark Cave", "A pitch-black cave. You can't see anything inside.");
        darkCave.setRequiredItem("Lantern");
        Locations shrine = new Locations("Sunspire Shrine", "An old shrine, that until recent was frequently used. Now it lays dormant.");
        Locations field = new Locations("Bryn Field", "A small field stretching along some rolling hills.");
        field.setRequiredItem("Crossing Permit");

        // Add locations to the world map
        locations.put(town.getName(), town);
        locations.put(house1.getName(), house1);
        locations.put(forest.getName(), forest);
        locations.put(mountain.getName(), mountain);
        locations.put(river.getName(), river);
        locations.put(graveyard.getName(), graveyard);
        locations.put(darkCave.getName(), darkCave);
        locations.put(shrine.getName(), shrine);
        locations.put(field.getName(), field);

        // Connect locations
        town.setExits("north", forest);
        forest.setExits("south", town);
        forest.setExits("east", mountain);
        mountain.setExits("west", forest);
        town.setExits("west", river);
        river.setExits("east", town);
        graveyard.setExits("west", town);
        town.setExits("east", graveyard);
        town.addExit("nearby house", house1);
        house1.addExit("westgate", town);
        forest.setExits("north", darkCave);
        darkCave.setExits("south", forest);
        forest.setExits("east", shrine);
        shrine.setExits("west", forest);
        river.setExits("west", field);
        field.setExits("east", river);


        // Add NPCs
        NPC oldMan = new NPC("Old Man", "A frail man with a long, white beard.", "The world isn't as safe as it once was...");
        town.addNPC(oldMan);

        NPC elder = new NPC("Elder Rowan", "An old wise man who knows many secrets.", "I have heard that there is a legendary Apple made of pure gold!");
        town.addNPC(elder);

        // NPC guide to help players with commands
        NPC startGuide = new NPC("Guide", "A worldly fellow, who looks eager to help.", "You can use the following commands:\n" +
                "- 'go [direction]': Move in a direction (e.g., 'go north').\n" +
                "- 'talk to [NPC]': Talk to an NPC (e.g., 'talk to Guide').\n" +
                "- 'look': Look around the current location.\n" +
                "- 'map': View the world map.\n" +
                "- 'inventory': View your inventory.\n" +
                "- 'take [item]': Pick up an item.\n" +
                "- 'drop [item]': Drop an item.\n" +
                "- 'save': Save the game.\n" +
                "- 'load': Load a saved game.\n" +
                "- 'quit': Exit the game.\n" +
                "Remember, some commands depend on the items you've picked up. If you're stuck, ask around!\n" +
                "And don't forget, you can type 'talk to guide' anytime for help.\n" +
                "\nGood luck, adventurer!");
        town.addNPC(startGuide);

        NPC ghost = new NPC("Ghost", "A translucent figure floating above the ground.", "Beware... the darkness beyond...");
        graveyard.addNPC(ghost);

        NPC riverGuard = new NPC("River Guard", "A serious looking man who watches over those who cross the river.", "I'm sorry, but I cannot let you cross the river.");
        river.addNPC(riverGuard);

        // Add items to locations
        town.addItem(new Item("Lantern", "An old lantern with a faint glow."));
        forest.addItem(new Item("Stick", "A sturdy wooden stick, perfect for walking or self-defense."));
        graveyard.addItem(new Item("Amulet", "A mysterious amulet engraved with ancient symbols."));
        river.addItem(new Item("Coin Purse", "A ratty coin purse someone must have dropped. It has a few old coins in it."));
        darkCave.addItem(new Item("Golden Apple", "An apple made of pure gold. This thing could be worth a lot."));
        shrine.addItem((new Item("Crossing Permit", "An old permit that allows you to cross at the River Crossing.")));
    }

    public Locations getLocation(String name) {
        return locations.get(name);
    }

    public Map<String, Locations> getLocations() {
        return locations;
    }
}
