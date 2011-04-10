package baller.client.gui;

import baller.client.net.PositionPuller;
import baller.client.net.PositionPusher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GamePanel extends JPanel implements Runnable {

    private static final Logger log = Logger.getLogger(GamePanel.class.getName());

    private static final int panelWidth = 500;
    private static final int panelHeight = 400;

    private Image bufferImage;
    private Graphics bufferGraphics;


    private boolean running;
    private static final int sleepTime = 1000;
    private final Thread gameThread;

    private final List<Square> squares;
    private Square userSquare;

    private final PositionPuller positionPuller;
    private final PositionPusher positionPusher;

    public GamePanel(PositionPuller positionPuller, PositionPusher positionPusher, List<Square> squares) {
        this.positionPusher = positionPusher;
        this.positionPuller = positionPuller;
        gameThread = new Thread(this);
        this.squares = new ArrayList<Square>(squares);

        setPreferredSize(new Dimension(panelWidth, panelHeight));

        setUpKeyListener();
        running = false;
    }

    private void setUpKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                handleKeyPress();
            }
        });
    }

    private void handleKeyPress() {
        Point pos = userSquare.getPosition();
        Point newPos = new Point(pos.x+1, pos.y);
        positionPusher.updateSquare(userSquare, newPos);
    }

    public void setUserSquare(Square square) {
        this.userSquare = square;
    }

    public void addNotify() {
        startUp();
        super.addNotify();
    }

    private void startUp() {
        gameThread.start();
    }

    public void run() {

        running = true;
        while (running) {
            gameUpdate();
            gameRender();
            paintScreen();

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.info(e.toString());
            }
        }

    }

    private void gameUpdate() {

        for (Square square : squares) {
            Point pos = positionPuller.getSquarePosition(square);
            square.setPosition(pos);
        }

    }

    private void gameRender() {
        if(bufferImage == null) {
            bufferImage = createImage(panelWidth, panelHeight);
            if(bufferImage == null) {
                return;
            }
            else {
                bufferGraphics = bufferImage.getGraphics();
            }
        }

        bufferGraphics.setColor(Color.white);
        bufferGraphics.fillRect(0, 0, panelWidth, panelHeight);

        for (Square square : squares) {
            square.paintSquare(bufferGraphics);
        }

    }

    private void paintScreen() {
        Graphics g = this.getGraphics();
        if(g != null && bufferImage != null) {
            g.drawImage(bufferImage, 0, 0, null);
        }
        Toolkit.getDefaultToolkit().sync();

    }

}
