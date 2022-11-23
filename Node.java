import java.util.ArrayList;

/**
 * ListNode represents a list node
 * Each node contains an int as data and a reference to the next node in the list.
 */
public class Node {
    protected String data;      // Node's data (a String)
    protected Node next = null;            // The next Node

    Node(String data) {
        // Constructor
        this.data = data;
    }

    public String getData() {
        // return data stored in this node
        return data;
    }

    public Node getNext() {
        // get next node
        return next;
    }

    public void setNext(Node next) {
        // set the next Node
        this.next = next;
    }
}
