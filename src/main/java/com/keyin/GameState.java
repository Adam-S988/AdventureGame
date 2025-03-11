package com.keyin;

import java.io.Serializable;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;  // Updated serialVersionUID

    private Player player;

    public GameState(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

