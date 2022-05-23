package edu.ntnu.thosve.models.formation;

import edu.ntnu.thosve.models.units.Unit;

import java.util.List;

/**
 * WIP class, not yet implemented.
 */
public class TriangleFormation extends Formation {
    public TriangleFormation(int xBottomLeft, int yBottomLeft, int xTopRight, int yTopRight) {
        super(xBottomLeft, yBottomLeft, xTopRight, yTopRight);
    }

    @Override
    public void spreadUnits(List<Unit> units) {
        if (units.size() == 0) {
            throw new IllegalStateException("List of units can not be empty");
        }

        int columnsAndRows = (int) Math.ceil((-1 + Math.sqrt(1 + 8 * units.size())) / 2);
        double columnWidth = this.width / columnsAndRows;
        double rowWidth = this.height / columnsAndRows;
        int currentColumn = 1;
        int currentRow = 1;

        for (int i = 0; i < units.size(); i++) {
            if (currentRow > currentColumn) {
                currentRow = 1;
                currentColumn += 1;
            } else {
                units.get(i).setPos(1, 1);
            }
        }
    }

}
