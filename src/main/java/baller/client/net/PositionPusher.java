package baller.client.net;

import baller.client.gui.Square;

import java.awt.*;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class PositionPusher {

    private static final Logger log = Logger.getLogger(PositionPusher.class.getName());

    private final PrintWriter writer;

    public PositionPusher(PrintWriter writer) {
        this.writer = writer;
    }

    public void updateSquare(Square square, Point newPos) {
        String update = constructUpdateString(square, newPos);
        log.info("writing update: " + update);
        writer.println(update);
    }

    private String constructUpdateString(Square square, Point pos) {
        String res = "" + square.getId();
        res += ":" + pos.getX();
        res += ":" + pos.getY();
        return res;
    }
}
