import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

public class Circle {

    int r;
    int centerX;
    int centerY;
    Color currentColor;

    Circle(int newR, int newCenterX, int newCenterY, Color color) {
        r = newR;
        centerX = newCenterX;
        centerY = newCenterY;
        currentColor = color;
    }
}
