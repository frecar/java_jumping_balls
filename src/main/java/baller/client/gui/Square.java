package baller.client.gui;

import java.awt.*;

public class Square {

    static final int size = 50;
    static final Color color = Color.red;

    private final int id;
    private Point position;

    public Square(int id) {
        this.id = id;
        position = new Point(50, 50);
    }

    public void setPosition(Point pos) {
        position.setLocation(pos);
    }

    public void paintSquare(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillRect(position.x, position.y, size, size);
    }

    public Point getPosition() {
        return position;
    }
}
