package q3;

public class q3 {

    public static int aInt = 0;
    public static volatile int bInt = 0;
    public static int cInt = 0;
    public static volatile int dInt = 0;
    public static int eInt = 0;

    public static void main(String [] args) {
        long start;
        long end;
        long averageTime;

        //A
        averageTime = 0;
        for (int i = 0; i < 7; i++) {
            start = System.currentTimeMillis();
            for (int j = 0; j < Integer.MAX_VALUE/5; j++) {
                aInt++;
            }
            end = System.currentTimeMillis();
            if (i > 0) {
                averageTime += (end - start);
            }
            aInt = 0;
        }

        averageTime = averageTime / 6;
        System.out.println("A performance: " + averageTime);

        //B
        averageTime = 0;
        for (int i = 0; i < 7; i++) {
            start = System.currentTimeMillis();
            for (int j = 0; j < Integer.MAX_VALUE/5; j++) {
                bInt++;
            }
            end = System.currentTimeMillis();
            if (i > 0) {
                averageTime += (end - start);
            }
            bInt = 0;
        }

        averageTime = averageTime / 6;
        System.out.println("B performance: " + averageTime);

        //C
        averageTime = 0;
        Object syncBlock = new Object();
        for (int i = 0; i < 7; i++) {
            start = System.currentTimeMillis();
            for (int j = 0; j < Integer.MAX_VALUE/5; j++) {
                synchronized(syncBlock) {
                    cInt++;
                }
            }
            end = System.currentTimeMillis();
            if (i > 0) {
                averageTime += (end - start);
            }
            cInt = 0;
        }

        averageTime = averageTime / 6;
        System.out.println("C performance: " + averageTime);

        //D
        averageTime = 0;
        Runnable incrementVolatileInt = new Runnable() {
            @Override
            public void run() {
                while (dInt < Integer.MAX_VALUE / 5) {
                    synchronized (q3.class) {
                        dInt++;
                    }
                }
            }
        };

        for (int i = 0; i < 7; i++) {
            Thread threadOne = new Thread(incrementVolatileInt);
            Thread threadTwo = new Thread(incrementVolatileInt);
            start = System.currentTimeMillis();
            threadOne.start();
            threadTwo.start();
            try {
                threadOne.join();
                threadTwo.join();
                end = System.currentTimeMillis();
                if (i > 0) {
                    averageTime += (end - start);
                }

            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
            dInt = 0;
        }

        averageTime = averageTime / 6;
        System.out.println("D performance: " + averageTime);

        //E
        averageTime = 0;
        Runnable incrementStaticint = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < Integer.MAX_VALUE / 10; i++) { // x == 5, so each one does half;
                    eInt++;
                }
            }
        };
        for (int i = 0; i < 7; i++) {
            Thread threadOne = new Thread(incrementStaticint);
            Thread threadTwo = new Thread(incrementStaticint);
            start = System.currentTimeMillis();
            threadOne.start();
            threadTwo.start();
            try {
                threadOne.join();
                threadTwo.join();
                end = System.currentTimeMillis();
                if (i > 0) {
                    averageTime += (end - start);
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        averageTime = averageTime / 6;
        System.out.println("E performance: " + averageTime);
    }

}
