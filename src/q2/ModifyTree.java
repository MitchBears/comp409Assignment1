package q2;

public class ModifyTree extends Thread {
    private Node root;

    ModifyTree(Node newRoot) {
        root = newRoot;
    }

    @Override
    public void run() {
        Node currentNode = root;
        float min = 0;
        float max = 200;
        long startTime = System.currentTimeMillis();
        //1 is right, 2 is left
        int direction = 0;
        int delete = 0;
        int create = 0;
        //Run for five seconds. Note, we can't ensure that a thread stops after 5 seconds using Thread.interrupt, therefore
        //this is the most accurate way possible to ensure that a thread stops after 5 seconds of runtime.
        while(System.currentTimeMillis() - startTime < 5000) {
            direction = Driver.randomInt(1, 2);
            //Go right
            if (direction == 1){
                //If there is a right child, calculate whether or not the child needs to be deleted.
                if (currentNode.getRightChild() != null) {
                    delete = Driver.randomInt(1, 10);
                    if (delete == 1) {
                        currentNode.deleteRightChild();
                        currentNode = root;
                        min = 0;
                        max = 200;
                        try {
                            Thread.sleep(Driver.randomInt(1, 5));
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        min = currentNode.getValue();
                        currentNode = currentNode.getRightChild();
                    }
                }
                else { //Right is null, calculate whether or not a new right child will be created and proceed accordingly.
                    create = Driver.randomInt(1, 10);
                    if (create <= 4) {
                        min = currentNode.getValue();
                        Node newNode = new Node(Driver.randomFloat(min, max));
                        currentNode.addRightChild(newNode);
                        currentNode = root;
                        min = 0;
                        max = 200;
                        try {
                            Thread.sleep(Driver.randomInt(1, 5));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            else { //Go left
                delete = Driver.randomInt(1, 10);
                //Delete the left subtree, if it exists
                if (currentNode.getLeftChild() != null) {
                    if (delete == 1) {
                        currentNode.deleteLeftChild();
                        currentNode = root;
                        //restore max and min variables back to the default values because we're traversing from the
                        //root again.
                        min = 0;
                        max = 200;
                        try {
                            sleep(Driver.randomInt(1, 5));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        max = currentNode.getValue();
                        currentNode = currentNode.getLeftChild();
                    }
                }
                else {
                    create = Driver.randomInt(1, 10);
                    // create a new node
                    if (create <= 4) {
                        max = currentNode.getValue();
                        Node newNode = new Node(Driver.randomFloat(min, max));
                        currentNode.addLeftChild(newNode);
                        currentNode = root;
                        //restore max and min variables back to the default values because we're traversing from the
                        //root again.
                        max = 200;
                        min = 0;
                        try {
                            Thread.sleep(Driver.randomInt(1, 5));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
