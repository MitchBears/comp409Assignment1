import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.Color;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.*;

public class q1 {

    // The image constructed
    public static BufferedImage img;

    // Image dimensions; you can modify these for bigger/smaller images
    public static int width = 1920;
    public static int height = 1080;

    public static int r;
    public static int c;
    public static boolean multithreaded;

    public static void main(String[] args) {
        try {
            if (args.length<3)
                throw new Exception("Missing arguments, only "+args.length+" were specified!");
            // arg 0 is the max radius
            r = Integer.parseInt(args[0]);
            // arg 1 is count
            c = Integer.parseInt(args[1]);
            // arg 2 is a boolean
            multithreaded = Boolean.parseBoolean(args[2]);

            // create an image and initialize it to all 0's
            img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
            //reset occurs at the beginning of every iteration of circle drawing, in order to refresh the image.
            for (int i=0;i<width;i++) {
                for (int j=0;j<height;j++) {
                    img.setRGB(i,j,0);
                }
            }

            // YOU NEED TO ADD CODE HERE AT LEAST!
            Drawing drawing = new Drawing(width, height);
            long average = 0;
            long start;
            long end;
            long timeElapsed;
            for(int i = 0; i < 6; i++) {
                if (multithreaded) {
                    KeepCount count = new KeepCount(c);
                    Thread one = generateThread(1, count, drawing);
                    Thread two = generateThread(2, count, drawing);

                    start = System.currentTimeMillis();
                    //Start threads
                    one.start();
                    two.start();

                    //Wait for threads to stop
                    one.join();
                    two.join();
                    img.setRGB(0, 0, width, height, flatten(drawing.pixels), 0, width);
                    end = System.currentTimeMillis();

                    timeElapsed = end - start;

                    if (i > 0) {
                        average += timeElapsed;
                    }

                }
                else {
                    start = System.currentTimeMillis();
                    singleThreadedDraw(drawing);
                    img.setRGB(0, 0, width, height, flatten(drawing.pixels), 0, width);
                    end = System.currentTimeMillis();

                    timeElapsed = end - start;

                    if (i > 0) {
                        average += timeElapsed;
                    }
                }
            }
            System.out.println("Done");

            average = average/5;

            if (multithreaded) {
                System.out.println("Multithreaded execution average for r: " + r + " c: " + c);
                System.out.println(average);
            }
            else {
                System.out.println("Singlethreaded execution average for r: " + r + " c: " + c);
                System.out.println(average);
            }


            // Write out the image
            File outputfile = new File("outputimage.png");
            ImageIO.write(img, "png", outputfile);

        } catch (Exception e) {
            System.out.println("ERROR " +e);
            e.printStackTrace();
        }
    }

    public static int [] flatten(int [][] incoming) {
        int [] newArray = new int[width*height];
        int j = 0;
        for (int i = 0; i < incoming.length; i++){
            System.arraycopy(incoming[i], 0, newArray, j, incoming[i].length);
            j += incoming[i].length;
        }
        return newArray;
    }

    public static void reset() {

    }

    public static Thread generateThread(int threadNumber, KeepCount count, Drawing drawing) {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                while(count.takeOne()) {
                    int centerX = random.nextInt(width);
                    int centerY = random.nextInt(height);
                    int radius = random.nextInt(r);
                    Color currentColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
                    Circle newCircle = new Circle(radius, centerX, centerY, currentColor);
                    if (threadNumber == 1) {
                        drawing.addCircleOne(newCircle);
                    }
                    else if (threadNumber == 2) {
                        drawing.addCircleTwo(newCircle);
                    }
                }
            }
        });
    }

    public static void singleThreadedDraw(Drawing drawing) {
        Random random = new Random();
        for (int i = 0; i < c; i++) {
            int centerX = random.nextInt(width);
            int centerY = random.nextInt(height);
            int radius = random.nextInt(r);
            Color currentColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            Circle newCircle = new Circle(radius, centerX, centerY, currentColor);
            drawing.draw(newCircle);
        }
    }
}
