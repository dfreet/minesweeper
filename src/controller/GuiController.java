package controller;

import model.Minefield;
import model.Square;
import view.GuiView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GuiController {
    GuiView view;
    boolean debug;
    Minefield field;
    ArrayList<GuiSquare> squares;
    public GuiController(GuiView view, boolean debug) {
        this.view = view;
        this.debug = debug;
        initialize();
    }

    public void initialize() {
        squares = new ArrayList<>();
        int width = 9;
        int height = 9;
        field = new Minefield(width, height, 10);
        view.form().getFieldPanel().setLayout(new GridLayout(width, height));
        view.form().getFieldPanel().setPreferredSize(new Dimension(width * 10, height * 10));
        for (Square square: field) {
            GuiSquare guiSquare = new GuiSquare(square);
            squares.add(guiSquare);
            if (debug) { guiSquare.setText(square.toString()); }
            view.form().getFieldPanel().add(guiSquare);
            guiSquare.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        if (square.isFlagged()) {
                            square.toggleFlag();
                            refresh();
                            return;
                        }
                        if (field.openSquare(square.getPosition())) {
                            gameOver(false);
                            return;
                        }
                        if (field.uninitialized()) {
                            field.initialize();
                            field.openSquare(square.getPosition());
                        }
                        refresh();
                        if (field.gameWon()) {
                            gameOver(true);
                        }
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        square.toggleFlag();
                        refresh();
                    }
                }
            });
        }
        view.pack();
    }

    public void refresh() {
        for (GuiSquare square: squares) {
            if (square.square.isOpen()) {
                square.setEnabled(false);
                square.setText(square.square.getBombsAround() == 0 ? "" : square.square.toString());
            } else if (square.square.isFlagged()) {
                square.setText("⚑");
            } else {
                square.setText("");
            }
            if (debug) {
                square.setText(square.square.toString());
            }
        }
    }

    public void gameOver(boolean win) {
        if (!win) {
            for (GuiSquare square : squares) {
                if (square.square.getBombsAround() == -1) {
                    this.field.openSquare(square.square.getPosition());
                }
            }
            refresh();
        }
        Object[] options = {"Reset", "Exit"};
        String message = win ? "You Win!" : "Game Over.";
        int action = JOptionPane.showOptionDialog(view, message, message, JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (action == 1) {
            System.exit(0);
        } else {
            view.form().getFieldPanel().removeAll();
            initialize();
        }
    }

}
