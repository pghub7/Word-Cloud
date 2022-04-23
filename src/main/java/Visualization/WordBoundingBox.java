package Visualization;

import java.awt.*;

public class WordBoundingBox {
    public double x;
    public double y;
    public double width;
    public double height;
    public Point topLeft;
    public Point bottomRight;
    public String word;

    public WordBoundingBox(double x, double y, double width, double height, String word) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.word = word;
        topLeft = new Point((int) x, (int) y);
        bottomRight = new Point((int) (x + width), (int) (y + height));
    }

    public boolean collides(WordBoundingBox b) {
        return this.topLeft.x <= b.bottomRight.x &&
                this.bottomRight.x >= b.topLeft.x &&
                this.topLeft.y <= b.bottomRight.y &&
                this.bottomRight.y >= b.topLeft.y;
    }

    public void updatePosition(double x, double y) {
        this.x = x;
        this.y = y;
        this.topLeft.setLocation(x, y);
        this.bottomRight.setLocation(x + this.width, y + this.height);
    }
}
