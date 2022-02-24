package edu.ntnu.thosve.formations;

import edu.ntnu.thosve.units.Unit;

import java.util.List;

public class TriangleFormation extends Formation {
    public TriangleFormation(int xBottomLeft, int yBottomLeft, int xTopRight, int yTopRight) {
        super(xBottomLeft, yBottomLeft, xTopRight, yTopRight);
    }

    @Override
    public void spreadUnits(List<Unit> units) {
        int row = (int) Math.ceil((-1 + Math.sqrt(1 + 8*units.size()))/2);
        System.out.println(row);
    }

}
