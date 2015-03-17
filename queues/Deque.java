public class Deque<Item> implements Iterable<Item> {
  private int N;

  private Node first;

  private Node last;

  // helper linked list
  private class Node {
    private Item item;

    private Node next;

    private Node prev;
  }

  /*
   * construct an empty deque
   */
  public Deque() {
    first = null;
    last = null;
    N = 0;
  }

  /*
   * is the deque empty?
   */
  public boolean isEmpty() {
    return N == 0;
  }

  /*
   * @return: the number of items on the deque
   */
  public int size() {
    return N;
  }

  /*
   * add the item to the front
   */
  public void addFirst(Item item) {
    if (item == null)
      throw new NullPointerException();
    Node oldfirst = first;
    first = new Node();
    first.item = item;
    first.prev = null;
    if (isEmpty()) {
      last = first;
      first.next = null;
    }
    else {
      oldfirst.prev = first;
      first.next = oldfirst;
    }
    N++;
  }

  /*
   * add the item to the end
   */
  public void addLast(Item item) {
    if (item == null)
      throw new NullPointerException();
    Node oldlast = last;
    last = new Node();
    last.item = item;
    last.next = null;
    if (isEmpty()) {
      first = last;
      last.prev = null;
    }
    else {
      oldlast.next = last;
      last.prev = oldlast;
    }
    N++;
  }

  /*
   * remove and return the item from the front
   */
  public Item removeFirst() {
    if (isEmpty())
      throw new java.util.NoSuchElementException();
    // save item to return
    Item item = first.item;
    // delete first node
    first = first.next;
    N--;
    // avoid loitering
    if (isEmpty()) {
      first = null;
      last = null;
    }
    else
      first.prev = null;
    // return item from the front
    return item;
  }

  /*
   * remove and return the item from the end
   */
  public Item removeLast() {
    if (isEmpty())
      throw new java.util.NoSuchElementException();
    // save item to return
    Item item = last.item;
    // delete node at the end
    last = last.prev;
    N--;
    // avoid Loitering
    if (isEmpty()) {
      first = null;
      last = null;
    }
    else
      last.next = null;
    // return item at the end
    return item;
  }

  /*
   * // return an iterator over items in order from front to end
   */
  public java.util.Iterator<Item> iterator() {
    return new ListIterator();
  }

  private class ListIterator implements java.util.Iterator<Item> {
    private Node current = first;

    @Override
    public boolean hasNext() {
      return current != null;
    }

    @Override
    public Item next() {
      if (!hasNext())
        throw new java.util.NoSuchElementException();
      Item item = current.item;
      current = current.next;
      return item;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }

  }

  /*
   * unit testing
   */
  public static void main(String[] args) {
    Deque<String> deque = new Deque<String>();
    while (!StdIn.isEmpty()) {
      String s = StdIn.readString();
      if (!s.equals("-")) {
        StdOut.println("deque size before add = " + deque.size());
        deque.addFirst(s);
        StdOut.println("deque size after add = " + deque.size());
      }
      else if (!deque.isEmpty()) {
        StdOut.println("removed item: " + deque.removeLast() + " ");
        StdOut.println("deque size after delete = " + deque.size());
      }
    }
    StdOut.println("(" + deque.size() + " left on the deque)");
  }
}
