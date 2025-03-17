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
        Locations town = new Locations("Westgate", "A small town with a bustling marketplace. Merchants call out their wares, filling the air with the scent of fresh bread, spices, and cured meats.\nThe cobblestone streets are lined with timber-framed buildings, their windows glowing warmly as townsfolk go about their business. A weathered stone fountain\nsits at the heart of the square, where travelers rest and share tales of distant lands.");
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
        Locations house2 = new Locations("Abandoned House", "A house that looks like it hasn't been lived in for quite some time.");
        house2.setRequiredItem("Mysterious Key");

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
        town.setExits("nearby house", house1);
        house1.setExits("westgate", town);
        forest.setExits("north", darkCave);
        darkCave.setExits("south", forest);
        forest.setExits("east", shrine);
        shrine.setExits("west", forest);
        river.setExits("west", field);
        field.setExits("east", river);
        town.setExits("abandoned house", house2);
        house2.setExits("westgate", town);


        // Add NPCs
        NPC mysteriousFigure = new NPC("Mysterious Figure",
                "A shadowy figure, their face obscured by the dim light. Their presence feels almost unreal.",
                "You don't remember me, do you? Do you even know who you are anymore? What a shame. You were showing so much promise. You probably don't even\nremember your objective do you? You were to bring to me a Sword from here in town. Do that for me, and I'll tell you more.\n This should help you.");
        house1.addNPC(mysteriousFigure);

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
        house1.addNPC(startGuide);

        NPC ghost = new NPC("Ghost", "A translucent figure floating above the ground.", "Beware... the darkness beyond...");
        graveyard.addNPC(ghost);

        NPC riverGuard = new NPC("River Guard", "A serious looking man who watches over those who cross the river.", "I'm sorry, but I cannot let you cross the river.");
        river.addNPC(riverGuard);

        NPC pilgrim = new NPC("Travelling Pilgrim", "A wordly traveller who has come to visit the sacred Sunspire Shrine.", "I have heard stories about this shrine, but it looks so much more beautiful than I could have ever imagined!");
        shrine.addNPC(pilgrim);

        // Add items to locations
        town.addItem(new Item("Lantern", "An old lantern with a faint glow."));
        forest.addItem(new Item("Stick", "A sturdy wooden stick, perfect for walking or self-defense."));
        graveyard.addItem(new Item("Amulet", "A mysterious amulet engraved with ancient symbols."));
        river.addItem(new Item("Coin Purse", "A ratty coin purse someone must have dropped. It has a few old coins in it."));
        darkCave.addItem(new Item("Golden Apple", "An apple made of pure gold. This thing could be worth a lot."));
        shrine.addItem((new Item("Crossing Permit", "An old permit that allows you to cross at the River Crossing.")));
        house2.addItem(new Item("Rusty Sword", "While quite worn, this sword looks like it was once very powerful."));

        // Add the book as an item to the shrine location
        String book1Content = "The Sunspire Shrine stands tall, reaching towards the heavens. It was once a place of great power, filled with light and sacred rituals.\nNow, it stands quiet, waiting for those brave enough to restore its former glory.";
        BookItem book1 = new BookItem("Secrets of Sunspire", "An ancient book detailing the history of the shrine.", "\n\u001B[34m" + book1Content + "\n\u001B[0m");
        shrine.addItem(book1);

        String book2Content = "The land of Tylpha was said to be founded by the Goddess Tylphana after she ascended from the Heavens.\nShe was said to have bathed in the very waters coming from the mountains.";
        BookItem book2 = new BookItem("Lands of Tylphyn", "An ancient book detailing the country of Tylphyn.", "\n\u001B[34m" + book2Content + "\n\u001B[0m");
        shrine.addItem(book2);
    }


    public Locations getLocation(String name) {
        return locations.get(name);
    }

    public Map<String, Locations> getLocations() {
        return locations;
    }
}
