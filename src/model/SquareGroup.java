package model;

import java.awt.*;
import java.util.Iterator;

public class SquareGroup implements Iterable<Square> {
    Square[][] field;
    Point center;


    public SquareGroup(Square[][] field, Point center) {
        this.field = field;
        this.center = center;
    }

    @Override
    public Iterator<Square> iterator() {
        return new GroupIterator(field, center);
    }
}
