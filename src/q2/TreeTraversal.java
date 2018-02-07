package q2;

import java.util.Stack;

public class TreeTraversal extends Thread {

    public StringBuilder data;
    private Node root;

    TreeTraversal(Node newRoot) {
        root = newRoot;
        data = new StringBuilder();
    }

    @Override
    public String toString() {
        return data.toString();
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();

        Node previous = null;
        Node current = root;
        while(System.currentTimeMillis() - startTime < 5000) {
          Node parent = current.getParent();
          Node left = current.getLeftChild();
          Node right = current.getRightChild();

          if (parent == previous) {
              if (left == null && right == null) {
                  data.append(" " + current.getValue());
                  if (current == root) {
                      data.append('\n');
                  }
                  else {
                      previous = current;
                      current = parent;
                  }
              }
              else if (left != null) {
                  previous = current;
                  current = left;
              }
              else {
                  previous = current;
                  current = right;
              }
          }
          else if(left == previous) {
              data.append(" " + current.getValue());
              if (right == null) {
                  if (current == root) {
                      data.append('\n');
                      previous = null;
                  }
                  else {
                      previous = current;
                      current = parent;
                  }
              }
              else {
                  previous = current;
                  current = right;
              }
          }
          else { //Right == previous
             if (current == root) {
                 data.append('\n');
                 previous = null;
             }
             else {
                 previous = current;
                 current = parent;
             }
          }
          try{
              Thread.sleep(Driver.randomInt(5, 20));
          }
          catch (InterruptedException e) {
              e.printStackTrace();
          }
        }
    }
}
