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
    Timer timer;
    int time = 0;
    int width;
    int height;
    int bombs;
    public GuiController(GuiView view, boolean debug) {
        this.view = view;
        this.debug = debug;
        width = 9;
        height = 9;
        bombs = 10;
        timer = new Timer(1000, a -> {
            time++;
            view.form().getTimeLbl().setText(String.format("%-" + 6 + "s", time));
        });
        timer.setRepeats(true);
        view.getNewGameItm().addActionListener(a -> initialize());
        view.getBeginnerBtn().addActionListener(a -> { width = 9; height = 9; bombs = 10; });
        view.getIntermediateBtn().addActionListener(a -> { width = 16; height = 16; bombs = 40; });
        view.getExpertBtn().addActionListener(a -> { width = 30; height = 16; bombs = 99; });
        // Max: 120x50
        view.getCustomBtn().addActionListener(a -> {
            SpinnerModel widthModel = new SpinnerNumberModel(30, 2, 120, 1);
            SpinnerModel heightModel = new SpinnerNumberModel(20, 1, 50, 1);
            SpinnerModel bombsModel = new SpinnerNumberModel(145, 1, 5999, 1);
            JSpinner widthSpn = new JSpinner(widthModel);
            JSpinner heightSpn = new JSpinner(heightModel);
            JSpinner bombsSpn = new JSpinner(bombsModel);
            JComponent[] inputs = new JComponent[] {
                    new JLabel("Width"),
                    widthSpn,
                    new JLabel("Height"),
                    heightSpn,
                    new JLabel("Bombs"),
                    bombsSpn
            };
            int result = JOptionPane.showConfirmDialog(null, inputs, "Custom Size",
                    JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                width = (int)widthSpn.getValue();
                height = (int)heightSpn.getValue();
                bombs = Math.min((int)bombsSpn.getValue(), width * height - 1);
            } else {
                width = 30;
                height = 20;
                bombs = 145;
            }
            view.getCustomBtn().setText("Custom [(" + width + "x" + height + ") " + bombs + "]");
        });

        initialize();
    }

    public void initialize() {
        time = 0;
        view.form().getTimeLbl().setText(String.format("%-" + 6 + "s", time));
        view.form().getBombsLbl().setText(String.format("%" + 6 + "s", bombs));
        view.form().getFieldPanel().removeAll();
        squares = new ArrayList<>();
        field = new Minefield(width, height, bombs);
        view.form().getFieldPanel().setLayout(new GridLayout(height, width));
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
                            timer.start();
                            field.openSquare(square.getPosition());
                        }
                        refresh();
                        if (field.gameWon()) {
                            gameOver(true);
                        }
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        if (!square.isOpen()) {
                            square.toggleFlag();
                            refresh();
                        }
                    } else if (SwingUtilities.isMiddleMouseButton(e)) {
                        if (square.isOpen() && square.getBombsAround() > 0) {
                            int flagsAround = 0;
                            for (Square groupSquare : field.getSquareGroup(square.getPosition())) {
                                flagsAround += groupSquare.isFlagged() ? 1 : 0;
                            }
                            if (flagsAround == square.getBombsAround()) {
                                boolean gameOver = false;
                                for (Square groupSquare : field.getSquareGroup(square.getPosition())) {
                                    if (!groupSquare.isFlagged()) {
                                        gameOver = gameOver || field.openSquare(groupSquare.getPosition());
                                    }
                                }
                                refresh();
                                if (gameOver) {
                                    gameOver(false);
                                } else if (field.gameWon()) {
                                    gameOver(true);
                                }
                            }
                        }
                    }
                }
            });
        }
        view.pack();
    }

    public void refresh() {
        int totalFlags = 0;
        for (GuiSquare square: squares) {
            if (square.square.isOpen()) {
                square.setEnabled(false);
                square.setText(square.square.getBombsAround() == 0 ? "" : square.square.toString());
            } else if (square.square.isFlagged()) {
                square.setText("⚑");
                totalFlags++;
            } else {
                square.setText("");
            }
            if (debug) {
                square.setText(square.square.toString());
            }
        }
        view.form().getBombsLbl().setText(String.format("%" + 6 + "s", bombs - totalFlags));
    }

    public void gameOver(boolean win) {
        timer.stop();
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
            initialize();
        }
    }

}
