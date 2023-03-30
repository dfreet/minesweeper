package model;

import model.Square;

import javax.swing.*;
import java.awt.*;

public class GuiSquare extends Square {
    JButton button;
    int xOffset;
    int yOffset;
    int width;
    int height;

    public GuiSquare(Point position, int xOffset, int yOffset, int width, int height) {
        super(position);
        this.button = new JButton();
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.width = width;
        this.height = height;
        this.button.setBounds(xOffset + position.x * width, yOffset + position.y * height, width, height);
    }
}
