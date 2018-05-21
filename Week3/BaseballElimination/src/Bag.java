import java.util.Iterator;

public class Bag<T> implements Iterable<T> {

    private Node head;
    private int sz;

    private class Node {
      public T val;
      public Node next;

      public Node(T val, Node next) {
        this.val = val;
        this.next = next;
      }
    }

    private class NodeIterator implements Iterator<T> {

      private Node cur;

      public NodeIterator() {
        this.cur = head;
      }

      @Override
      public boolean hasNext() {
        return this.cur != null;
      }

      @Override
      public T next() {
        if (!this.hasNext()) {
          throw new IllegalStateException("No more values.");
        }
        final T val = this.cur.val;
        this.cur = this.cur.next;
        return val;
      }
    }

    public Bag() {
      this.head = null;
      this.sz = 0;
    }

    public int size() {
      return this.sz;
    }

    public void add(T val) {
      this.head = new Node(val, this.head);
    }

    @Override
    public Iterator<T> iterator() {
      return new NodeIterator();
    }
}
