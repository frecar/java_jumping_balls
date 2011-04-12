package baller.client.gui;

import baller.client.net.PositionPuller;
import baller.client.net.PositionPusher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.logging.Logger;

public class GamePanel extends JPanel implements Runnable {

    private static final Logger log = Logger.getLogger(GamePanel.class.getName());

    private static final int panelWidth = 500;
    private static final int panelHeight = 400;

    private Image bufferImage;
    private Graphics bufferGraphics;


    private volatile boolean running;
    private static final int sleepTime = 40;
    private final Thread gameThread;

    private volatile List<Square> squares;
    private volatile Square userSquare;

    private final PositionPuller positionPuller;
    private final PositionPusher positionPusher;

    public GamePanel(PositionPuller positionPuller, PositionPusher positionPusher) {
        this.positionPusher = positionPusher;
        this.positionPuller = positionPuller;
        gameThread = new Thread(this);

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

    public void handleKeyPress() {
        if (!running) {
            return;
        }

        Point pos = userSquare.getPosition();
        Point newPos = new Point(pos.x+5, pos.y);
        positionPusher.updateSquare(userSquare, newPos);
    }

    public void setSquares(List<Square> squares) {
        this.squares = squares;
    }

    public void setUserSquare(int squareId) {
        for(Square square : squares) {
            if (square.getId() == squareId ) {
                this.userSquare = square;
                return;
            }
        }
    }

    public void addNotify() {
        log.info("GamePanel added, starting up");
        startUp();
        super.addNotify();
    }

    private void startUp() {
        gameThread.start();
        requestFocus();
    }

    public void run() {

        running = true;
        new Thread(positionPuller).start();
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
