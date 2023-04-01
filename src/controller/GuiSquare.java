package controller;

import model.Square;

import javax.swing.*;
import java.awt.Dimension;

public class GuiSquare extends JButton {
    Square square;

    public GuiSquare(Square square) {
        super();
        this.square = square;
        this.setMinimumSize(new Dimension(10, 10));
    }
}
