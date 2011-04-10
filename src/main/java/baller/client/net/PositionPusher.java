package baller.client.net;

import baller.client.gui.Square;

import java.awt.*;
import java.io.PrintWriter;

public class PositionPusher {

    private final int clientId;
    private final PrintWriter writer;

    public PositionPusher(int clientId, PrintWriter writer) {
        this.clientId = clientId;
        this.writer = writer;
    }

    public void updateSquare(Square square, Point newPos) {
        String update = constructUpdateString(square, newPos);
        writer.println(update);
    }

    private String constructUpdateString(Square square, Point pos) {
        String res = "" + square.getId();
        res += ":" + pos.getX();
        res += ":" + pos.getY();
        res += "|";
        return res;
    }
}
