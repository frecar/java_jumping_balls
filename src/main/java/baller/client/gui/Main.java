package baller.client.gui;

import baller.client.net.PositionPuller;
import baller.client.net.PositionPusher;

import javax.swing.*;

public class Main {

    public static void main(String... args) {

        PositionPuller puller = new PositionPuller();
        PositionPusher pusher = new PositionPusher();

        GamePanel gamePanel = new GamePanel(puller, pusher, puller.getSquars());
        JFrame frame = new JFrame("Baller Test");
        frame.setContentPane(gamePanel);

        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
