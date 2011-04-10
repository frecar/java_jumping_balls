package baller.client.net;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import baller.client.gui.*;

public class PositionPuller {

    List<Square> squares;

    public PositionPuller() {
        squares = new ArrayList<Square>();
        Square s1 = new Square(1);
        Square s2 = new Square(2);
        squares.add(s1);
        squares.add(s2);
    }

    public List<Square> getSquars() {
        return squares;
    }


    public Point getSquarePosition(Square square) {
        Point pos = square.getPosition();
        pos.x = pos.x + 10;
        return pos;
    }
}
