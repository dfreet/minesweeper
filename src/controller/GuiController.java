package controller;

import model.Minefield;
import model.Square;
import view.GuiView;

import java.awt.*;
import java.util.ArrayList;

public class GuiController {
    boolean debug;
    ArrayList<GuiSquare> squares;
    public GuiController(GuiView view, boolean debug) {
        this.debug = debug;
        squares = new ArrayList<>();
        int width = 9;
        int height = 9;
        Minefield field = new Minefield(width, height, 10);
        view.form().getFieldPanel().setLayout(new GridLayout(width, height));
        view.form().getFieldPanel().setPreferredSize(new Dimension(width * 10, height * 10));
        for (Square square: field) {
            GuiSquare guiSquare = new GuiSquare(square);
            squares.add(guiSquare);
            if (debug) { guiSquare.setText(square.toString()); }
            view.form().getFieldPanel().add(guiSquare);
            guiSquare.addActionListener(a -> {
                field.openSquare(guiSquare.square.getPosition());
                if (!field.isInitialized()) {
                    field.initialize();
                }
                refresh();
            });
        }
        view.pack();
    }

    public void refresh() {
        for (GuiSquare square: squares) {
            if (square.square.isOpen()) {
                square.setEnabled(false);
            }
            if (debug) {
                square.setText(square.square.toString());
            }
        }
    }

}
