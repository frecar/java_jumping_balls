package baller.client.gui;

import baller.client.net.Network;
import baller.client.net.PositionPuller;
import baller.client.net.PositionPusher;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

public class Main {

    public static void main(String... args) throws Exception {



        Network network = new Network();
        network.setUp();

        PositionPuller puller = new PositionPuller(network.getReader());
        PositionPusher pusher = new PositionPusher(network.getPusher());
        final GamePanel gamePanel = new GamePanel(puller, pusher);

        List<Square> squares = network.waitForStartUp();
        gamePanel.setSquares(squares);
        gamePanel.setUserSquare(network.getClientID());

        JFrame frame = new JFrame("Baller Test");
   //     frame.setContentPane(new WaitPanel());
        frame.setContentPane(gamePanel);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                gamePanel.handleKeyPress();
            }
        });
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.validate();

    }
}
