import java.awt.Point;

public class Square {
    final Point position;
    int bombsAround; // -2 for undefined, -1 for bomb, 0-8 for surrounding bombs
    boolean isOpen;
    boolean isFlagged;

    public Square(Point position) {
        this.position = position;
        this.bombsAround = -2;
        this.isOpen = false;
        this.isFlagged = false;
    }

    @Override
    public String toString() {
        if (bombsAround < 0) {
            return bombsAround == -2 ? "-" : "*";
        }
        return String.valueOf(bombsAround);
    }
}
