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
    }

    public boolean isInitialized() { return initialized; }

    public Square getSquare (Point position) { return field[position.x][position.y]; }

    @Override
    public Iterator<Square> iterator() { return new MinefieldIterator(field); }

    public GroupIterator groupIterator(Point center) { return new GroupIterator(field, center); }

    public void createField() {
        this.field = new Square[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                field[x][y] = new Square(new Point(x,y));
            }
        }
    }

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
            GroupIterator groupIterator = groupIterator(square.position);
            while (groupIterator.hasNext()) {
                square.bombsAround += groupIterator.next().bombsAround == -1 ? 1 : 0;
            }
            /*
            for (int y = Math.max(iterator.yPos-1,0); y <= Math.min(iterator.yPos+1,height-1); y++) {
                for (int x = Math.max(iterator.xPos-1,0); x <= Math.min(iterator.xPos+1,width-1); x++) {
                    square.bombsAround += field[x][y].bombsAround == -1 ? 1 : 0;
                }
            }
             */
        }
        /*
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (field[x][y].bombsAround == -1) {
                    continue;
                }
                field[x][y].bombsAround = 0;
                for (int j = Math.max(y-1,0); j <= Math.min(y+1,height-1); j++) {
                    for (int i = Math.max(x-1,0); i <= Math.min(x+1,height-1); i++) {
                        field[x][y].bombsAround += field[i][j].bombsAround == -1 ? 1 : 0;
                    }
                }
            }
        }
         */
        this.initialized = true;
    }


    public boolean openSquare(Point coordinates) {
        Square square = getSquare(coordinates);
        square.isOpen = true;
        if (openAroundEmpty && square.bombsAround == 0) {
            GroupIterator iterator = groupIterator(coordinates);
            while (iterator.hasNext()) {
                Square groupSquare = iterator.next();
                if (!groupSquare.isOpen) {
                    openSquare(groupSquare.position);
                }
            }
            /*
            for (int y = Math.max(coordinates.y - 1, 0); y <= Math.min(coordinates.y + 1, height - 1); y++) {
                for (int x = Math.max(coordinates.x - 1, 0); x <= Math.min(coordinates.x + 1, width - 1); x++) {
                    if (!field[x][y].isOpen) {
                        openSquare(new Point(x, y));
                    }
                }
            }
             */
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
        /*
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (field[x][y].bombsAround > -1 && !field[x][y].isOpen) {
                    return false;
                }
            }
        }
        */
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
