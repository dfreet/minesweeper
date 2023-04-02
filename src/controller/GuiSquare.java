package controller;

import model.Square;

import javax.swing.*;
import java.awt.*;

public class GuiSquare extends JButton {
    Square square;

    public GuiSquare(Square square) {
        super();
        this.square = square;
        this.setMinimumSize(new Dimension(20, 20));
        this.setMargin(new Insets(0, 0, 0, 0));
    }
}
