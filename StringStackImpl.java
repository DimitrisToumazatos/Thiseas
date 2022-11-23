import java.io.PrintStream;
import java.util.NoSuchElementException;

public class StringStackImpl implements StringStack{

    Node top = null;         //  point to the stack's newest item
    int count = 0;          //  number of items in the stack

    @Override
    public boolean isEmpty() {
        return top == null;         // check if the stack is empty
    }

    @Override
    public void push(String item) {         // put a new item in the stack
        Node n = new Node(item);
        count++;                            // increase the number of items in stack (count) by 1
        if (isEmpty()){                     // case if the stack is empty
            top = n;
        } else {                            // case if there are items in the stack
            n.setNext(top);
            top = n;
        }
    }

    @Override
    public String pop() throws NoSuchElementException {
        // return the data of the newest item in the stack
        // and then remove it from the stack
        if (isEmpty()){                                 // case if the stack is empty
            throw new NoSuchElementException();         // throw exception because there are no items to remove
        }
                                                        // case if there is at least 1 item in the stack
        count--;                                        // decrease the count by 1

        Node temp = top;
        String out = top.getData();
        temp = top.getNext();
        top.setNext(null);
        top = temp;
        return out;
    }

    @Override
    public String peek() throws NoSuchElementException {
        // return the data of the newest item in the stack
        if (isEmpty()){                             // case if there are no items in the stack
            throw new NoSuchElementException();     // throw exception because there are no items to remove
        }
                                                    // case if there is at least 1 item in the stack
        return top.getData();
    }

    @Override
    public void printStack(PrintStream stream) {             // print the stack's items from newest to oldest
        if (isEmpty()) {                                     // case if the stack is empty
            System.out.println("The Stack is Empty.");
        } else {                                             // case if there is at least 1 item
            Node n = top;
            while (n != null) {
                stream.println(n.getData());
                n = n.getNext();
            }
        }
    }

    @Override
    public int size() {                                     // return the number of items in the stack
        return count;
    }
}
