package baller.client.gui;

import javax.swing.*;
import java.awt.*;

public class WaitPanel extends JPanel {

    public WaitPanel() {
        setPreferredSize(new Dimension(500, 400));
    }


    @Override
    public void paint(Graphics graphics) {
        graphics.drawString("Waiting...", 100, 100);

    }
}
