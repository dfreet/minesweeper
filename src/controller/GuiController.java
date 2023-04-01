package controller;

import model.Minefield;
import view.GuiView;

public class GuiController {
    private GuiView view;

    public GuiController(GuiView view) {
        this.view = view;
        Minefield field = new Minefield(9, 9, 10);
    }

}
