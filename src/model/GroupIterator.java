package model;

import java.awt.*;
import java.util.Iterator;

public class GroupIterator implements Iterator<Square> {
    Square[][] field;
    Point center;
    int xPos;
    int yPos;
    int width;
    int height;

    public GroupIterator(Square[][] field, Point center) {
        this.field = field;
        this.center = center;
        this.width = field.length;
        this.height = field[0].length;
        this.xPos = Math.max(center.x - 1, 0);
        this.yPos = Math.max(center.y - 1, 0);
    }

    @Override
    public boolean hasNext() {
        return yPos <= center.y + 1 && yPos < height && xPos <= center.x + 1 && xPos < width;
    }

    @Override
    public Square next() {
        Square current = field[xPos][yPos];
        if (xPos < center.x + 1 && xPos < width - 1) {
            xPos++;
        } else {
            xPos = Math.max(center.x - 1, 0);
            yPos++;
        }
        return current;
    }
}
