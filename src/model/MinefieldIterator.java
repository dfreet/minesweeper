package model;

import java.util.Iterator;

public class MinefieldIterator implements Iterator<Square> {
    Square[][] field;
    int xPos;
    int yPos;
    int width;
    int height;
    public MinefieldIterator(Square[][] field) {
        this.field = field;
        width = field.length;
        height = field[0].length;
        xPos = 0;
        yPos = 0;
    }

    @Override
    public boolean hasNext() { return yPos < height && xPos < width; }

    @Override
    public Square next() {
        Square current = field[xPos][yPos];
        if (xPos < width - 1) {
            xPos++;
        } else {
            xPos = 0;
            yPos++;
        }
        return current;
    }
}
