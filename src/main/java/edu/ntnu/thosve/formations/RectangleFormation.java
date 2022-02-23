package edu.ntnu.thosve.formations;

import edu.ntnu.thosve.units.Unit;

import java.util.List;

public class RectangleFormation extends Formation{
    int xBottomLeft;
    int yBottomLeft;
    int xTopRight;
    int yTopRight;

    public RectangleFormation(int xBottomLeft, int yBottomLeft, int xTopRight, int yTopRight) {
        this.xBottomLeft = xBottomLeft;
        this.yBottomLeft = yBottomLeft;
        this.xTopRight = xTopRight;
        this.yTopRight = yTopRight;
    }

    @Override
    public void spreadUnits(List<Unit> units) {
        int columns = 1;
        int rows = 1;

        int height = yTopRight - yBottomLeft;
        int width = xTopRight - xBottomLeft;


        while (columns * rows < units.size()) {
            if (width / (columns + 1) > height / (rows + 1)) {
                columns += 1;
            } else {
                rows += 1;
            }
        }

        for(int row = 0; row < rows; row++ ) {
            for(int column = 0; column < columns; column++) {
                double x = column * ( (double) width / (columns-1)) + xBottomLeft;
                double y =  row * ( (double) height / (rows-1)) + yBottomLeft;
                int index = column + (row*columns);
                if (index < units.size()) {
                    units.get(index).setPos(x, y);
                }
            }
        }
    }

}
