package edu.ntnu.thosve;

public class Field {
    private int xBottomLeft;
    private int yBottomLeft;
    private int xTopRight;
    private int yTopRight;
    private int columns;
    private int rows;

    public Field(int xBottomLeft, int yBottomLeft, int xTopRight, int yTopRight, int columns, int rows) {
        this.xBottomLeft = xBottomLeft;
        this.yBottomLeft = yBottomLeft;
        this.xTopRight = xTopRight;
        this.yTopRight = yTopRight;
        this.columns = columns;
        this.rows = rows;
    }


}
