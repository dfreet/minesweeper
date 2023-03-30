package model;

import model.Minefield;

import java.awt.*;

public class GuiMinefield extends Minefield {
    int xOffset;
    int yOffset;
    int buttonWidth;
    int buttonHeight;

    public GuiMinefield(int width, int height, int bombs, int xOffset, int yOffset, int buttonWidth, int buttonHeight) {
        super(width, height, bombs);
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight;
        this.field = new GuiSquare[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.field[x][y] = new GuiSquare(new Point(x,y), xOffset, yOffset, buttonWidth, buttonHeight);
            }
        }
    }
}
