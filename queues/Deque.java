import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node head;
    private Node tail;
    private int size;
    
    // construct an empty deque
    public Deque() {
        head = new Node();
        tail = head;
        size = 0;
    }
    
    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // insert the item at the front
    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException();
        
        Node node = new Node(item);
        node.item = item;
        if (isEmpty()) {
            tail = node;
        } else {
            head.next.prev = node;
            node.next = head.next;
        }
        head.next = node;
        node.prev = head;
        size++;
    }
    
    // insert the item at the end
    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException();

        Node node = new Node(item);
        tail.next = node;
        node.prev = tail;
        tail = node;
        size++;
    }

    // delete and return the item at the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();

        Node node = head.next;
        head.next = node.next;
        if (node.next != null)
            node.next.prev = head;
        size--;
        if (isEmpty())
            tail = head;
        return node.item;
    }

    // delete and return the item at the end
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();

        Node node = tail;
        tail.prev.next = null;
        tail = tail.prev;
        size--;
        return node.item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator(head);
    }
    
    private class Node {
        private Node prev;
        private Node next;
        private Item item;

        Node(Item item) {
            this.item = item;
        }

        Node() {
            this(null);
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node node;
        
        DequeIterator(Node node) {
            this.node = node;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();

            node = node.next;
            return node.item;
        }

        public boolean hasNext() {
            return node.next != null;
        }
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        deque.addFirst("1");
        deque.addLast("2");
        deque.addFirst("3");
        for (String str : deque) {
            System.out.println(str);
        }
    }
}
