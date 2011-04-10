package baller.client.gui;

import baller.client.net.PositionPuller;
import baller.client.net.PositionPusher;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Main {

    public static void main(String... args) throws Exception {

/*        JFrame frame = new JFrame("Baller Test");
        frame.setContentPane(new WaitPanel());

        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PositionPuller puller = new PositionPuller();
        PositionPusher pusher = new PositionPusher();

        GamePanel gamePanel = new GamePanel(puller, pusher, puller.getSquares());
        frame.setContentPane(gamePanel);
        frame.validate(); */

        Socket socket = new Socket("localhost", 1234);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println(in.readLine());

    }
}
