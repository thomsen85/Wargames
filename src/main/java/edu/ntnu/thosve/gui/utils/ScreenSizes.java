package edu.ntnu.thosve.gui.utils;

public enum ScreenSizes {
    SIZE_1280x720(1280, 720),
    SIZE_1920x1080(1920, 1080);


    final int height;
    final int width;




    ScreenSizes(int width, int height) {
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
