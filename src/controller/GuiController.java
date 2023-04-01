package controller;

import model.Minefield;
import model.Square;
import view.GuiView;

import java.awt.*;

public class GuiController {

    public GuiController(GuiView view) {
        int width = 9;
        int height = 9;
        Minefield field = new Minefield(width, height, 10);
        view.form().getFieldPanel().setLayout(new GridLayout(width, height));
        view.form().getFieldPanel().setPreferredSize(new Dimension(width * 10, height * 10));
        for (Square square: field) {
            GuiSquare guiSquare = new GuiSquare(square);
            view.form().getFieldPanel().add(guiSquare);
        }
        view.pack();
    }

}
