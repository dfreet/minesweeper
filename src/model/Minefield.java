package model;

import java.awt.Point;
import java.util.Iterator;
import java.util.Random;

public class Minefield implements Iterable<Square> {
    Square[][] field;
    int width;
    int height;
    int totalBombs;
    boolean initialized;
    boolean openAroundEmpty;

    public Minefield (int width, int height, int bombs) {
        this.width = width;
        this.height = height;
        this.totalBombs = Math.min(bombs, width * height);
        this.initialized = false;
        this.openAroundEmpty = true;

        this.field = new Square[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                field[x][y] = new Square(new Point(x,y));
            }
        }
    }

    public boolean uninitialized() { return !initialized; }

    public Square getSquare (Point position) { return field[position.x][position.y]; }

    @Override
    public Iterator<Square> iterator() { return new MinefieldIterator(field); }

    public SquareGroup getSquareGroup(Point center) { return new SquareGroup(field, center); }

    public void initialize() {
        int bombs = 0;
        Random rand = new Random();
        while (bombs < totalBombs) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            if (field[x][y].bombsAround == -1 || field[x][y].isOpen) {
                continue;
            }
            field[x][y].bombsAround = -1;
            bombs++;
        }
        MinefieldIterator iterator = (MinefieldIterator)this.iterator();
        while (iterator.hasNext()) {
            Square square = iterator.next();
            if (square.bombsAround == -1) {
                continue;
            }
            square.bombsAround = 0;
            for (Square groupSquare : getSquareGroup(square.position)) {
                square.bombsAround += groupSquare.bombsAround == -1 ? 1 : 0;
            }
        }
        this.initialized = true;
    }


    public boolean openSquare(Point coordinates) {
        Square square = getSquare(coordinates);
        square.isOpen = true;
        if (openAroundEmpty && square.bombsAround == 0) {
            for (Square groupSquare : getSquareGroup(coordinates)) {
                if (!groupSquare.isOpen) {
                    openSquare(groupSquare.position);
                }
            }
        }
        return square.bombsAround == -1;
    }

    public void flagSquare(Point coordinates) {
        getSquare(coordinates).toggleFlag();
    }


    public boolean gameWon() {
        for (Square square : this) {
            if (square.bombsAround > -1 && !square.isOpen) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        final int topBuffer = 5;
        StringBuilder display = new StringBuilder();
        display.append(" ".repeat(topBuffer));
        for (int j = 0; j < width; j++) {
            display.append((char)('A' + j)).append(" ");
        }
        display.append("\n").append(" ".repeat(topBuffer)).append("-".repeat(width*2-1)).append("\n");
        for (int y = 0; y < height; y++) {
            display.append(String.format("%" + 2 + "s | ", y));
            for (int x = 0; x < height; x++) {
                if (!field[x][y].isOpen && !field[x][y].isFlagged) {
                    display.append("# ");
                } else if (field[x][y].isFlagged) {
                    display.append("⚑ ");
                } else {
                    display.append(field[x][y]).append(" ");
                }
            }
            display.append("\n");
        }
        return display.toString();
    }

    public String fieldState() {
        StringBuilder strField = new StringBuilder();
        int lastY = 0;
        for (Square square : this) {
            if (square.position.y > lastY) {
                strField.append("\n");
                lastY = square.position.y;
            }
            strField.append(square).append(" ");
        }
        return strField.toString();
    }
}
