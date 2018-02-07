import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;

public class Drawing {

    BufferedImage img;
    volatile Circle circleOne;
    volatile Circle circleTwo;
    int width;
    int height;
    public int pixels [][];
    AtomicBoolean permissions[];

    Drawing(BufferedImage newImg, int newWidth, int newHeight) {
        permissions = new AtomicBoolean[2];
        permissions[0] = new AtomicBoolean(false);
        permissions[1] = new AtomicBoolean(false);
        img = newImg;
        width = newWidth;
        height = newHeight;
        pixels = new int[height][width];
    }

    public synchronized void addCircleOne(Circle newCircleOne) {
        circleOne = newCircleOne;
        while(!obtainPermission(0));
        draw(circleOne);
        release(0);
    }

    public synchronized void addCircleTwo(Circle newCircleTwo) {
        circleTwo = newCircleTwo;
        while(!obtainPermission(1));
        draw(circleTwo);
        release(1);
    }

    public synchronized boolean obtainPermission(int index) {
        if (permissions[Math.abs(index - 1)].get() && intersect(circleOne, circleTwo)) {
            return false;
        }
        permissions[index].set(true);
        return true;
    }

    public void release(int index) {
        permissions[index].set(false);
    }

    public boolean intersect(Circle circleOne, Circle circleTwo) {
        double xDistance = (circleOne.centerX - circleTwo.centerX);
        double yDistance = (circleOne.centerY - circleTwo.centerY);
        if(xDistance > width/2) {
            xDistance = width-xDistance;
        }
        if(yDistance > height/2) {
            yDistance = height - yDistance;
        }
        double totalDistance = Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
        return totalDistance - (circleOne.r + circleTwo.r) <= 0;
    }

    public void draw(Circle circle) {
        int r = circle.r;
        int centerX = circle.centerX;
        int centerY = circle.centerY;
        int xIndex = 0;
        int yIndex = 0;
        Color currentColor = circle.currentColor;
        for (int y=-r; y <= r; y++) {
            for (int x=-r; x <= r; x++) {
                if(x*x + y*y <= r*r + r) {
                    int xCoord = centerX + x;
                    int yCoord = centerY + y;
                    if (xCoord < 0) {
                        xCoord = width + (centerX + x);
                    }
                    else if (xCoord >= width) {
                        xCoord = (centerX + x) - width;
                    }
                    if (yCoord < 0) {
                        yCoord = height + (centerY + y);
                    }
                    else if (yCoord >= height) {
                        yCoord = (centerY + y) - height;
                    }
                    pixels[yCoord][xCoord] = currentColor.getRGB();
                }
            }
        }
    }
}
