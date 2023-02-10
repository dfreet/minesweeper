import javax.swing.*;
import java.awt.Point;
import java.util.Random;

public class Minefield {
    Square[][] field;
    int width;
    int height;
    int totalBombs;
    int totalFlags;
    Timer timer;

    public Minefield (int width, int height, int bombs) {
        this.width = width;
        this.height = height;
        this.totalBombs = Math.min(bombs, width * height);
        this.totalFlags = 0;
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
            if (field[x][y].bombsAround == -1) {
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
    }

    @Override
    public String toString() {
        String strField = "";
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < height; x++) {
                strField += field[x][y].toString() + " ";
            }
            strField += "\n";
        }
        return strField;
    }
}
