package baller.client.net;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import baller.client.gui.*;

import javax.swing.text.Position;

public class PositionPuller implements Runnable {

    private static final Logger log = Logger.getLogger(PositionPuller.class.getName());

    private final Map<Integer, Point> positions;
    private final BufferedReader input;


    public PositionPuller(BufferedReader input) {
        positions = new HashMap<Integer, Point>();
        this.input = input;
    }

    private final Point defaultPoint = new Point(0,0);
    public Point getSquarePosition(Square square) {
        Point pos = positions.get(square.getId());
        if (pos != null) {
            return pos;
        }
        return defaultPoint;
    }

    @Override
    public void run() {
        while (true) {
            String positions;
            try {
                positions = input.readLine();
            } catch (IOException e) {
                log.info(e.toString());
                return;
            }
            updatePositions(positions);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

    }

    private void updatePositions(String positionUpdate) {
        String[] updateParts = positionUpdate.split("\\|");
        for (String update : updateParts) {
            handleUpdate(update);
        }
    }

    private void handleUpdate(String update) {
        String[] updateParts = update.split(":");
        int clientId = Integer.parseInt(updateParts[0]);
        int xPos = Integer.parseInt(updateParts[1]);
        int yPos = Integer.parseInt(updateParts[2]);
        Point p = new Point(xPos, yPos);
        positions.put(clientId, p);
    }
}
