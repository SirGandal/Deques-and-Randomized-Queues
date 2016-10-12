import java.util.Iterator;

/*
 * Performance requirements.
 * Your deque implementation must support each deque operation in constant
 * worst-case time. 
 * A deque containing n items must use at most 48n + 192 bytes of memory. 
 * and use space proportional to the number of items currently in the deque. 
 * Additionally, your iterator implementation must support each operation 
 * (including construction) in constant worst-case time.
 */

// These performance requirements force use to use a linked list since we 
// know that every operation requires constant time in the worst case

public class Deque<Item> implements Iterable<Item> {

  private class Node {
    private Item item;
    private Node previous;
    private Node next;
  }

  private Node first = null;
  private Node last = null;
  private int size = 0;

  // construct an empty deque
  public Deque() {
    first = null;
    last = null;
  }

  // is the deque empty?
  public boolean isEmpty() {
    return first == null || last == null;
  }

  // return the number of items on the deque
  public int size() {
    return size;
  }

  // add the item to the front
  public void addFirst(Item item) {

    if (item == null) {
      throw new java.lang.NullPointerException();
    }

    Node oldfirst = first;
    first = new Node();
    first.item = item;
    first.next = oldfirst;
    first.previous = null;

    if (isEmpty()) {
      last = first;
    } else if (oldfirst != null) {
      oldfirst.previous = first;
    }

    size++;

    if (size == 1) {
      last = first;
    }
  }

  // add the item to the end
  public void addLast(Item item) {

    if (item == null) {
      throw new java.lang.NullPointerException();
    }

    Node oldlast = last;
    last = new Node();
    last.item = item;
    last.previous = oldlast;
    last.next = null;

    if (isEmpty()) {
      first = last;
    } else if (oldlast != null) {
      oldlast.next = last;
    }

    size++;

    if (size == 1) {
      first = last;
    }
  }

  // remove and return the item from the front
  public Item removeFirst() {

    if (isEmpty()) {
      throw new java.util.NoSuchElementException();
    }

    Item item = first.item;
    first = first.next;
    if (first != null) {
      first.previous = null;
    }

    if (isEmpty()) {
      last = null;
      first = null;
    }

    size--;
    return item;
  }

  // remove and return the item from the end
  public Item removeLast() {

    if (isEmpty()) {
      throw new java.util.NoSuchElementException();
    }

    Item item = last.item;
    last = last.previous;
    if (last != null) {
      last.next = null;
    }

    if (isEmpty()) {
      last = null;
      first = null;
    }

    size--;
    return item;
  }

  private class DequeIterator implements Iterator<Item> {
    private Node current = first;

    public boolean hasNext() {
      return current != null;
    }

    public Item next() {

      if (!hasNext()) {
        throw new java.util.NoSuchElementException();
      }

      Item item = current.item;
      current = current.next;
      return item;
    }

    public void remove() {
      throw new java.lang.UnsupportedOperationException();
    }
  }

  // return an iterator over items in order from front to end
  public Iterator<Item> iterator() {
    return new DequeIterator();
  }

  public static void main(String[] args) {
    Deque<Integer> deck = new Deque<Integer>();

    for (int i : deck) {
      System.out.println(i);
    }
  }
}
