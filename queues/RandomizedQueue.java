public class RandomizedQueue<Item> implements Iterable<Item> {
  private Item[] q;
  private int N = 0;

  public RandomizedQueue() { // construct an empty randomized queue
    q = (Item[]) new Object[2];
  }

  public boolean isEmpty() { // is the queue empty?
    return N == 0;
  }

  public int size() { // return the number of items on the queue
    return N;
  }

  /*
   * resize the array
   */
  private void resize(int capacity) {
    Item[] copy = (Item[]) new Object[capacity];
    for (int i = 0; i < N; i++)
      copy[i] = q[i];
    q = copy;
  }

  public void enqueue(Item item) { // add the item
    if (item == null)
      throw new NullPointerException();
    if (N == q.length)
      resize(2 * q.length);
    q[N++] = item;
  }

  public Item dequeue() { // remove and return a random item
    if (isEmpty())
      throw new java.util.NoSuchElementException();
    // pick a random item between 0 and N-1
    int index = StdRandom.uniform(N);
    Item item = q[index];
    if (index != N - 1)
      q[index] = q[N - 1];
    // avoid loitering
    q[N - 1] = null;
    N--;
    // shrink the array if necessary (< 1/4)
    if (N > 0 && N == q.length / 4)
      resize(q.length / 2);
    return item;
  }

  public Item sample() { // return (but do not remove) a random item
    if (isEmpty())
      throw new java.util.NoSuchElementException();
    int index = StdRandom.uniform(N);
    return q[index];
  }

  public java.util.Iterator<Item> iterator() { 
  // return an independent iterator over items in random order
    return new ArrayIterator();
  }

  private class ArrayIterator implements java.util.Iterator<Item> {
    private Item[] tempItem = (Item[]) new Object[q.length];
    private int tempN = N;

    public ArrayIterator() {
      for (int i = 0; i < q.length; i++)
        tempItem[i] = q[i];
    }

    @Override
    public boolean hasNext() {
      return tempN != 0;
    }

    @Override
    public Item next() {
      if (!hasNext())
        throw new java.util.NoSuchElementException();
      int index = StdRandom.uniform(tempN);
      Item item = tempItem[index];
      if (index != tempN - 1)
        tempItem[index] = tempItem[tempN - 1];
      // avoid loitering
      tempItem[tempN - 1] = null;
      tempN--;
      return item;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }

  }

  // unit testing
  public static void main(String[] args) {
    RandomizedQueue<String> rq = new RandomizedQueue<String>();
    while (!StdIn.isEmpty()) {
      String item = StdIn.readString();
      if (!item.equals("-"))
        rq.enqueue(item);
      else if (!rq.isEmpty())
        StdOut.println("dequeued item: " + rq.dequeue());
    }
    StdOut.println("(" + rq.size() + " left on queue)");
  }
}
