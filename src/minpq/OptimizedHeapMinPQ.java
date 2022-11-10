package minpq;

import java.util.*;

/**
 * Optimized binary heap implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class OptimizedHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the heap of item-priority pairs.
     */
    private final List<PriorityNode<T>> items;
    /**
     * {@link Map} of each item to its associated index in the {@code items} heap.
     */
    private final Map<T, Integer> itemToIndex;

    /**
     * Constructs an empty instance.
     */
    public OptimizedHeapMinPQ() {
        items = new ArrayList<>();
        itemToIndex = new HashMap<>();
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }

        PriorityNode<T> newNode = new PriorityNode(item, priority);
        items.add(newNode);
        itemToIndex.put(newNode.item(), items.indexOf(newNode));
    }

    @Override
    public boolean contains(T item) {
        // TODO: Replace with your code
        if(itemToIndex.containsKey(item)) return true;
        return false;
    }

    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // TODO: Replace with your code
        double minPriority = items.get(0).priority();
        int minIdx = 0;

        for(int i = 0; i < items.size(); i++){
            if(items.get(i).priority() < minPriority){
                minIdx = i;
                minPriority = items.get(i).priority();
            }
        }

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

        PriorityNode<T> toRemove = items.get(minIdx);

        items.remove(minIdx);
        itemToIndex.remove(toRemove.item());

        return toRemove.item();
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }

        for(int i = 0; i < items.size(); i++) {
            if (items.get(i).item().equals(item)) {
                items.get(i).setPriority(priority);
                break;
            }
        }
    }

    @Override
    public int size() {
        // TODO: Replace with your code
        return items.size();
    }
}
