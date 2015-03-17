public class Subset {
  public static void main(String[] args) {
    RandomizedQueue<String> rq = new RandomizedQueue<String>();
    int k = Integer.valueOf(args[0]);
    while (!StdIn.isEmpty()) {
      String item = StdIn.readString();
      rq.enqueue(item);
    }
    while (k > 0) {
      StdOut.println(rq.dequeue());
      k--;
    }
  }
}
