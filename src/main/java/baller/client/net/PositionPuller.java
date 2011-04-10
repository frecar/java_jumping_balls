package baller.client.net;

import java.awt.*;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

import baller.client.gui.*;

public class PositionPuller implements Runnable{

    private final Map<Integer, Point> positions;
    private BufferedReader input;


    public PositionPuller(BufferedReader input) {
        positions = new HashMap<Integer, Point>();
        this.input = input;

    }

    public Point getSquarePosition(Square square) {
        return positions.get(square.getId());
    }

    @Override
    public void run() {

    }
}
