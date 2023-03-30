package model;

import model.Minefield;

import java.awt.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsolePlayer {
    public static void play() {
        Minefield field = new Minefield(10, 10, 10);
        field.createField();
        System.out.print(field.displayState());

        Scanner input = new Scanner(System.in);
        boolean gameOver = false;
        boolean win = false;
        while (!gameOver) {
            System.out.print("action: ");
            String command = input.nextLine().toUpperCase();
            String[] arguments = command.split("\\s+");
            try {
                switch (arguments[0]) {
                    case "OPEN" -> {
                        Point coordinates = getCoordinates(arguments[1]);
                        gameOver = field.openSquare(coordinates);
                        if (!field.initialized) {
                            field.initialize();
                            field.openSquare(coordinates);
                        } else if (field.gameWon()) {
                            gameOver = true;
                            win = true;
                        }
                        System.out.print(field.displayState());
                    }
                    case "FLAG" -> {
                        field.flagSquare(getCoordinates(arguments[1]));
                        System.out.print(field.displayState());
                    }
                    case "EXIT" -> {
                        return;
                    }
                    default -> System.out.println("""
                            Valid Commands:
                            \topen coordinates\topen square at coordinates
                            \tflag coordinates\tflag or unflag square at coordinates
                            \texit

                            \texample: open C6""");
                }
            } catch (IllegalArgumentException err) {
                System.out.println(err.getMessage());
            }
        }
        System.out.println(win ? "You Won!" : "Game Over");
    }

    public static Point getCoordinates(String input) throws IllegalArgumentException {
        Pattern pattern = Pattern.compile("(\\D)(\\d+)");
        Matcher matcher = pattern.matcher(input);
        Point coordinates = new Point();
        if (matcher.matches()) {
            coordinates.x = matcher.group(1).charAt(0) - 'A';
            coordinates.y = Integer.parseInt(matcher.group(2));
        } else {
            throw new IllegalArgumentException("Coordinates invalid.");
        }
        return coordinates;
    }
}
