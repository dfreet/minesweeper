import java.awt.*;
import java.util.Random;

public class Minefield {
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
        this.initialized = true;
    }

    public boolean openSquare(Point coordinates) {
        field[coordinates.x][coordinates.y].isOpen = true;
        boolean gameOver = field[coordinates.x][coordinates.y].bombsAround == -1;
        if (openAroundEmpty && field[coordinates.x][coordinates.y].bombsAround == 0 && !gameOver) {
            for (int y = Math.max(coordinates.y - 1, 0); y <= Math.min(coordinates.y + 1, height - 1); y++) {
                for (int x = Math.max(coordinates.x - 1, 0); x <= Math.min(coordinates.x + 1, width - 1); x++) {
                    if (!field[x][y].isOpen) {
                        gameOver = openSquare(new Point(x, y));
                    }
                }
            }
        }
        return gameOver;
    }

    public void flagSquare(Point coordinates) {
        field[coordinates.x][coordinates.y].isFlagged = !field[coordinates.x][coordinates.y].isFlagged;
    }

    public String displayState() {
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

    public boolean gameWon() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (field[x][y].bombsAround > -1 && !field[x][y].isOpen) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder strField = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < height; x++) {
                strField.append(field[x][y]).append(" ");
            }
            strField.append("\n");
        }
        return strField.toString();
    }
}
