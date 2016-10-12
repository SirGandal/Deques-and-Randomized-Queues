import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

/*
 * Performance requirements.
 * Your randomized queue implementation must support each randomized queue operation 
 * (besides creating an iterator) in constant amortized time. 
 * That is, any sequence of m randomized queue operations 
 * (starting from an empty queue)
 * should take at most cm steps in the worst case, for some constant c. 
 * A randomized queue containing n items must use at most 48n + 192 bytes of memory. 
 * Additionally, your iterator implementation must support operations next() and 
 * hasNext() in constant worst-case time; and construction in linear time; 
 * you may (and will need to) use a linear amount of extra memory per iterator.
 */

// The performance requirements allow us to use arrays instead of linked list
// since we know that every operation requires constant amortized time in the 
// worst case

public class RandomizedQueue<Item> implements Iterable<Item> {

  private Item[] q;
  private int head = 0;
  private int tail = 0;

  // construct an empty randomized queue
  public RandomizedQueue() {
    q = (Item[]) new Object[1];
  }

  // is the queue empty?
  public boolean isEmpty() {
    return size() == 0;
  }

  // return the number of items on the queue
  public int size() {
    return tail - head;
  }

  // add the item
  public void enqueue(Item item) {

    if (item == null) {
      throw new java.lang.NullPointerException();
    }

    // the queue is all filled with items, we have to resize
    if (tail == q.length) {
      resize(2 * q.length);
    }

    q[tail] = item;

    // tail points to the next empty position
    tail++;
  }

  private void resize(int capacity) {
    Item[] copy = (Item[]) new Object[capacity];

    int tmpTail = 0;
    for (int i = 0; i < capacity; i++) {
      if (i < size()) {
        copy[i] = q[i + head];
        tmpTail = i + 1;
      } else {
        copy[i] = null;
      }
    }

    head = 0;
    tail = tmpTail;
    q = copy;
  }

  // remove and return a random item
  public Item dequeue() {

    if (isEmpty()) {
      throw new java.util.NoSuchElementException();
    }

    Item item = q[head];
    q[head] = null;
    head++;

    // the queue is almost empty, we have to shrink
    if (size() > 0 && size() <= q.length / 4) {
      resize(q.length / 2);
    }

    return item;
  }

  // return (but do not remove) a random item
  public Item sample() {

    if (isEmpty()) {
      throw new java.util.NoSuchElementException();
    }

    return q[StdRandom.uniform(head, tail)];
  }

  private class RandomizedQueueIterator implements Iterator<Item> {

    private int[] usedIndexes = new int[size()];
    private int usedIndexesCount = 0;

    public RandomizedQueueIterator() {
      for (int i = 0; i < usedIndexes.length; i++) {
        usedIndexes[i] = 0;
      }
    }

    public boolean hasNext() {
      return usedIndexes.length != usedIndexesCount;
    }

    public Item next() {
      if (!hasNext()) {
        throw new java.util.NoSuchElementException();
      }

      int randomIndex = -1;
      do {
        randomIndex = StdRandom.uniform(head, tail);
      } while (randomIndex == -1 || usedIndexes[randomIndex - head] == 1);

      usedIndexes[randomIndex - head] = 1;
      usedIndexesCount++;

      return q[randomIndex];
    }

    public void remove() {
      throw new java.lang.UnsupportedOperationException();
    }
  }

  // return an independent iterator over items in random order
  public Iterator<Item> iterator() {
    return new RandomizedQueueIterator();
  }

  // unit testing
  public static void main(String[] args) {

    RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

    for (int i : rq) {
      System.out.println(i);
    }
  }
}
