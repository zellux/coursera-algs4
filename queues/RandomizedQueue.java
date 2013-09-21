import java.util.NoSuchElementException;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] items;
    private int MINIMAL_ARRAY_SIZE = 10;
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        items = (Item[]) new Object[MINIMAL_ARRAY_SIZE];
    }
    
    // is the queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    private void resize() {
        if (size == items.length) {
            Item[] olditems = items;
            items = (Item[]) new Object[items.length * 2];
            System.arraycopy(olditems, 0, items, 0, size);
        } else if (size < items.length / 2 && size >= MINIMAL_ARRAY_SIZE) {
            Item[] olditems = items;
            items = (Item[]) new Object[items.length / 2];
            System.arraycopy(olditems, 0, items, 0, size);
        }
    }

    // return the number of items on the queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException();
        items[size] = item;
        size++;
        resize();
    }
    
    // delete and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        int i = StdRandom.uniform(size);
        Item item = items[i];
        items[i] = items[size - 1];
        items[size - 1] = null;
        size--;
        resize();
        return item;
    }
    
    // return (but do not delete) a random item
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();
        return items[StdRandom.uniform(size)];
    }
    
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {
        private int[] indices;
        private int current;

        RandomizedIterator() {
            indices = new int[size];
            for (int i = 0; i < size; i++)
                indices[i] = i;
            StdRandom.shuffle(indices);
            current = -1;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            current++;
            return items[indices[current]];
        }

        public boolean hasNext() {
            return current != size - 1;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
