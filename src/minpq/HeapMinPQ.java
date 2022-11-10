package minpq;

import java.util.Comparator;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * {@link PriorityQueue} implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class HeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    /**
     * {@link PriorityQueue} storing {@link PriorityNode} objects representing each item-priority pair.
     */
    private final PriorityQueue<PriorityNode<T>> pq;
    /**
     * Constructs an empty instance.
     */
    public HeapMinPQ() {
        pq = new PriorityQueue<>(Comparator.comparingDouble(PriorityNode::priority));
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        // TODO: Replace with your code
        PriorityNode<T> node = new PriorityNode(item, priority);
        pq.add(node);
    }

    @Override
    public boolean contains(T item) {
        // TODO: Replace with your code
        for(PriorityNode<T> n: pq){
            if(n.item().equals(item)){
                return true;
            }
        }

        return false;
    }

    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // TODO: Replace with your code
        return pq.peek().item();
    }

    @Override
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // TODO: Replace with your code
        return pq.poll().item();
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }

        PriorityNode<T> newNode = new PriorityNode<>(item, priority);

        for(PriorityNode<T> n:pq) {
            if (n.item().equals(item)) {
                pq.remove(n);
                break;
            }
        }
        pq.add(newNode);
    }

    @Override
    public int size() {
        // TODO: Replace with your code
        return pq.size();
    }
}
