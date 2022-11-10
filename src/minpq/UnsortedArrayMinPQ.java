package minpq;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Unsorted array (or {@link ArrayList}) implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class UnsortedArrayMinPQ<T> implements ExtrinsicMinPQ<T> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the item-priority pairs in no specific order.
     */
    private final List<PriorityNode<T>> items;

    /**
     * Constructs an empty instance.
     */
    public UnsortedArrayMinPQ() {
        items = new ArrayList<>();
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        PriorityNode<T> newNode = new PriorityNode(item, priority);
        items.add(newNode);
    }

    @Override
    public boolean contains(T item) {
        for(PriorityNode<T> n: items){
            if(n.item().equals(item)) return true;
        }
        return false;
    }

    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }

        double minPriority = items.get(0).priority();

        int minIdx = 0;

        for(int i = 0; i < items.size(); i++){
            if(items.get(i).priority() < minPriority){
                minIdx = i;
                minPriority = items.get(i).priority();
            }
        }
//        double minpr = items.get(minIdx).priority();
        return items.get(minIdx).item();
    }

    @Override
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }

        double minPriority = items.get(0).priority();

        int minIdx = 0;

        for(int i = 0; i < items.size(); i++){
            if(items.get(i).priority() < minPriority){
                minIdx = i;
                minPriority = items.get(i).priority();
            }
        }

        return items.remove(minIdx).item();

    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        for(int i = 0; i < items.size(); i++){
            if(items.get(i).item().equals(item)){
                items.remove(i);
                items.add(new PriorityNode<T>(item, priority));
                break;
            }
        }
    }

    @Override
    public int size() {
        return items.size();
    }
}
