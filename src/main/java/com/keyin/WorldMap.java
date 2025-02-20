package com.keyin;

import java.util.HashMap;
import java.util.Map;

public class WorldMap {
    private Map<String, Locations> locations;

    public WorldMap() {
        this.locations = new HashMap<>();
        setupWorld();
    }

    private void setupWorld() {
        // Define all locations (already done in GameEngine)
        Locations town = new Locations("Westgate", "A small town with a bustling marketplace.");
        Locations forest = new Locations("Woods of the Night", "A dark forest with towering trees.");
        Locations mountain = new Locations("Mount Troyal", "A rocky mountain with a narrow path leading up.");
        Locations river = new Locations("Troyal River", "A fast-flowing river with a wooden bridge.");
        Locations graveyard = new Locations("Westgate Graveyard", "A small graveyard that appears overgrown. Many broken graves dot the space.");

        // Add locations to the world map
        locations.put(town.getName(), town);
        locations.put(forest.getName(), forest);
        locations.put(mountain.getName(), mountain);
        locations.put(river.getName(), river);
        locations.put(graveyard.getName(), graveyard);

        // Connect locations
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

    }

    public Locations getLocation(String name) {
        return locations.get(name);
    }

    public Map<String, Locations> getLocations() {
        return locations;
    }
}
