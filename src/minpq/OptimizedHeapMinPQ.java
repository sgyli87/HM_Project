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
        items.add(null);
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
        swim(size());

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
/*        double minPriority = items.get(0).priority();
        int minIdx = 0;

        for(int i = 0; i < items.size(); i++){
            if(items.get(i).priority() < minPriority){
                minIdx = i;
                minPriority = items.get(i).priority();
            }
        } */

        return items.get(1).item();
    }

    @Override
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
/*        double minPriority = items.get(0).priority();

        int minIdx = 0;

        for(int i = 0; i < items.size(); i++){
            if(items.get(i).priority() < minPriority){
                minIdx = i;
                minPriority = items.get(i).priority();
            }
        }
*/
        PriorityNode<T> min = items.get(1);
        swap(1, size());
        items.remove(size());
        sink(1);
        itemToIndex.remove(min.item());
        return min.item();
/*        PriorityNode<T> toRemove = items.get(1);

        items.remove(1);
        itemToIndex.remove(toRemove.item());

        return toRemove.item(); */
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }

        for(int i = 1; i <= items.size(); i++) {
            if (items.get(i).item().equals(item)) {
                items.get(i).setPriority(priority);
                swim(i);
                sink(i);
                break;
            }
        }
    }

    @Override
    public int size() {
        // TODO: Replace with your code
        return items.size() - 1;
    }
    /** Returns the index of the given index's parent node. */
    private static int parent(int index) {
        return index / 2;
    }
    /** Returns the index of the given index's left child. */
    private static int left(int index) {
        return index * 2;
    }

    /** Returns the index of the given index's right child. */
    private static int right(int index) {
        return left(index) + 1;
    }

    /** Returns true if and only if the index is accessible. */
    private boolean accessible(int index) {
        return 1 <= index && index <= size();
    }

    /** Returns the index with the lower priority, or 0 if neither is accessible. */
    private int min(int index1, int index2) {
        if (!accessible(index1) && !accessible(index2)) {
            return 0;
        } else if (accessible(index1) && (!accessible(index2)
                || items.get(index1).priority() < (items.get(index2)).priority() )) {
            return index1;
        } else {
            return index2;
        }
    }
    /** Swap the nodes at the two indices. */
    private void swap(int index1, int index2) {
        PriorityNode<T> temp = items.get(index1);
        items.set(index1, items.get(index2));
        itemToIndex.put(items.get(index2).item(), index1);
        items.set(index2, temp);
        itemToIndex.put(temp.item(), index2);
    }
    /** Bubbles up the node currently at the given index. */
    private void swim(int index) {
        int parent = parent(index);
        while (accessible(parent) && items.get(index).priority() < (items.get(parent)).priority() ) {
            swap(index, parent);
            index = parent;
            parent = parent(index);
        }
    }

    /** Bubbles down the node currently at the given index. */
    private void sink(int index) {
        int child = min(left(index), right(index));
        while (accessible(child) && items.get(index).priority() > (items.get(child)).priority() ) {
            swap(index, child);
            index = child;
            child = min(left(index), right(index));
        }
    }


}
