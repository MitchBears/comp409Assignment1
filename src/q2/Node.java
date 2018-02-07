package q2;

public class Node {

    private float value;
    private volatile Node parent;
    private volatile Node leftChild;
    private volatile Node rightChild;

    Node(float newValue) {
        value = newValue;
    }

    public void addLeftChild(Node newLeftChild) {
        leftChild = newLeftChild;
        leftChild.parent = this;
    }

    public void addRightChild(Node newRightChild) {
        rightChild = newRightChild;
        rightChild.parent = this;
    }

    public void deleteRightChild() {
        if (rightChild != null) {
            rightChild = null;
        }
    }

    public void deleteLeftChild() {
        if (leftChild != null) {
            leftChild = null;
        }
    }

    public float getValue() {
        return value;
    }

    public Node getParent() {
        return parent;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

}
