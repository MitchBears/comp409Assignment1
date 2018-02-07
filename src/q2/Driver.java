package q2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Driver {

    public static void main (String args[]) {
        Node root = buildBinarySearchTree();
        Thread traverseOne = new TreeTraversal(root);
        Thread traverseTwo = new TreeTraversal(root);
        Thread modify = new ModifyTree(root);

        traverseOne.start();
        traverseTwo.start();
        modify.start();

        try {
            traverseOne.join();
            traverseTwo.join();
            modify.join();
            System.out.println("A: " + traverseOne.toString());
            System.out.println("B: " + traverseTwo.toString());
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Node buildBinarySearchTree() {
        ArrayList<Float> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add(randomFloat(0, 150));
        }
        Collections.sort(list);
        System.out.println(list.toString());
        Node root = new Node(list.get(7));
        Node three = new Node(list.get(3));
        Node eleven = new Node(list.get(11));
        Node one = new Node(list.get(1));
        Node five = new Node(list.get(5));
        Node nine = new Node(list.get(9));
        Node thirteen = new Node(list.get(13));
        Node zero = new Node(list.get(0));
        Node two = new Node(list.get(2));
        Node four = new Node(list.get(4));
        Node six = new Node(list.get(6));
        Node eight = new Node(list.get(8));
        Node ten = new Node(list.get(10));
        Node twelve = new Node(list.get(12));
        Node fourteen = new Node(list.get(14));
        root.addLeftChild(three);
        root.addRightChild(eleven);
        three.addLeftChild(one);
        three.addRightChild(five);
        eleven.addLeftChild(nine);
        eleven.addRightChild(thirteen);
        one.addLeftChild(zero);
        one.addRightChild(two);
        nine.addLeftChild(eight);
        nine.addRightChild(ten);
        five.addLeftChild(four);
        five.addRightChild(six);
        thirteen.addLeftChild(twelve);
        thirteen.addRightChild(fourteen);
        return root;

    }

    public static int randomInt(int origin, int bound) {
        return ThreadLocalRandom.current().nextInt(origin, bound + 1);
    }

    public static float randomFloat(float origin, float bound) {
        Random rand = new Random();
        float result = rand.nextFloat() * (bound - origin) + origin;
        return result;
    }
}
