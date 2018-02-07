import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;

public class Drawing {

    volatile Circle circleOne;
    volatile Circle circleTwo;
    int width;
    int height;
    public int pixels [][];
    AtomicBoolean flags[];
    volatile int turn = 0;

    Drawing(int newWidth, int newHeight) {
        flags = new AtomicBoolean[2];
        flags[0] = new AtomicBoolean(false);
        flags[1] = new AtomicBoolean(false);
        width = newWidth;
        height = newHeight;
        pixels = new int[height][width];
    }

    public void addCircleOne(Circle newCircleOne) {
        circleOne = newCircleOne;
        flags[0].set(true);
        turn = 0;
        while(turn == 0 && flags[1].get());
        draw(circleOne);
        flags[0].set(false);
    }

    public void addCircleTwo(Circle newCircleTwo) {
        circleTwo = newCircleTwo;
        flags[1].set(true);
        turn = 1;
        while(turn == 1 && flags[0].get());
        draw(circleTwo);
        flags[1].set(false);
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
