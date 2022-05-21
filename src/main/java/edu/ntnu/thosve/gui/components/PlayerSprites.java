package edu.ntnu.thosve.gui.components;

import javafx.scene.canvas.GraphicsContext;

public class PlayerSprites {
    private int walkingCycle = 0;
    private final int MAX_WALK_CYCLE = 4;

    private int x;
    private int y;
    private PlayerStatus playerStatus = PlayerStatus.IDLE;

    public PlayerSprites(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setPlayerStatus(PlayerStatus playerStatus) {
        this.playerStatus = playerStatus;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void draw(GraphicsContext gc) {
    }

}
