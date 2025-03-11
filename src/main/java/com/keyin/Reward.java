package com.keyin;

import com.keyin.Item;
import com.keyin.Player;

public class Reward {
    private String description;
    private Item item;
    private int gold;

    public Reward(String description, Item item, int gold) {
        this.description = description;
        this.item = item;
        this.gold = gold;
    }

    public String getDescription() {
        return description;
    }

    public Item getItem() {
        return item;
    }

    public int getGold() {
        return gold;
    }

    public void giveReward(Player player) {
        if (item != null) {
            player.addItem(item);
            System.out.println("You received a " + item.getName() + " as a reward!");
        }
    }
}
