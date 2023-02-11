import java.awt.*;
import java.util.Scanner;
import java.util.regex.*;

public class Main {
    public static void main(String[] args) {
        Minefield field = new Minefield(10, 10, 10);
        System.out.print(field.displayState());

        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("action: ");
            String command = input.nextLine().toUpperCase();
            String[] arguments = command.split("\\s+");
            try {
                switch (arguments[0]) {
                    case "OPEN":
                        Point coordinates = getCoordinates(arguments[1]);
                        field.openSquare(coordinates);
                        if (!field.initialized) {
                            field.initialize();
                            field.openSquare(coordinates);
                        }
                        System.out.print(field.displayState());
                        break;
                    case "FLAG":
                        field.flagSquare(getCoordinates(arguments[1]));
                        System.out.print(field.displayState());
                        break;
                    case "EXIT":
                        return;
                    default:
                        System.out.println("Valid Commands:" +
                                "\n\topen coordinates\topen square at coordinates" +
                                "\n\tflag coordinates\tflag or unflag square at coordinates" +
                                "\n\texit" +
                                "\n\n\texample: open C6");
                }
            } catch (IllegalArgumentException err) {
                System.out.println(err.getMessage());
            }
        }
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
