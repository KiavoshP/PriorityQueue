import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MaxHeap.
 *
 * @author Kiavosh Peynabard
 * @version 1.0
 * @userid kpeynabard3
 * @GTID 903353136
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class MaxHeap<T extends Comparable<? super T>> {

    /*
     * The initial capacity of the MaxHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MaxHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MaxHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * Consider how to most efficiently determine if the list contains null data.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null; "
                    + "Please provide a real data");
        }
        backingArray = (T[]) new Comparable[(data.size() * 2) + 1];
        for (T currData : data) {
            if (currData == null) {
                throw new IllegalArgumentException("Max heap should be a complete tree; "
                        + "Hence, data should not be empty");
            }
            backingArray[size + 1] = currData;
            size++;
        }
        buildHeap();
    }
    /**
     * make sure that order property of heap is intact
     */
    private void buildHeap() {
        for (int indx = size / 2; indx > 0; indx--) {
            downHeap(indx);
        }
    }

    /**
     * Expand the backing array to two times of backing array.length
     *
     * Do not shrink the backing array.
     *
     * Replace any unused spots in the array with null.
     *
     * @param thatSize the length of the current backing array
     */
    private void expand(int thatSize) {
        T[] newBacking = (T[]) new Comparable[thatSize * 2];
        for (int indx = 1; indx <= size; indx++) {
            newBacking[indx] = backingArray[indx];
        }
        backingArray = newBacking;
    }


    /**
     * Adds the data to the heap.
     *
     * If sufficient space is not available in the backing array (the backing
     * array is full except for index 0), resize it to double the current
     * length.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Max heap should be a complete tree; "
                    + "Hence, data should not be empty");
        }
        if (backingArray.length == size + 1) {
            expand(backingArray.length);
        }
        if (size == 0) {
            backingArray[size + 1] = data;
        } else {
            backingArray[size + 1] = data;
            heapUp((size + 1) / 2, size + 1);
        }
        size++;
    }
    /**
     * heap up from current index and use prev index to do so
     *
     * @param currIndx the index to start the heap up from
     * @param prevIndx the index of old location (parent)
     */
    private void heapUp(int currIndx, int prevIndx) {
        if (backingArray[currIndx].compareTo(backingArray[prevIndx]) < 0) {
            T holder = backingArray[currIndx];
            backingArray[currIndx] = backingArray[prevIndx];
            backingArray[prevIndx] = holder;
        } else if (backingArray[currIndx].compareTo(backingArray[prevIndx]) >= 0) {
            return;
        }
        if (currIndx == 1) {
            return;
        } else {
            heapUp(currIndx / 2, currIndx);
        }

    }

    /**
     * Removes and returns the root of the heap.
     *
     * Do not shrink the backing array.
     *
     * Replace any unused spots in the array with null.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty. No element can be remove");
        }
        T dataHolder = backingArray[1];
        if (size == 1) {
            backingArray[1] = null;
        } else {
            backingArray[1] = backingArray[size];
            downHeap(1);
            backingArray[size] = null;
        }
        size--;
        return dataHolder;

    }
    /**
     * it will down heap from the given index.
     *
     *
     * @param currIndx the index to start down heaping from (parent)
     */
    private void downHeap(int currIndx) {
        T lChild = null;
        T rChild = null;
        T childToCompare = null;
        int indxHolder = 0;
        if (2 * currIndx <= size) {
            lChild = backingArray[2 * currIndx];
        }
        if (1 + (2 * currIndx) <= size) {
            rChild = backingArray[1 + (2 * currIndx)];
        }
        if (lChild != null && rChild != null) {
            if (lChild.compareTo(rChild) > 0) {
                childToCompare = lChild;
                indxHolder = 2 * currIndx;
            } else {
                childToCompare = rChild;
                indxHolder = 1 + (2 * currIndx);
            }
        } else if (lChild != null) {
            childToCompare = lChild;
            indxHolder = (2 * currIndx);
        } else {
            return;
        }
        if (backingArray[currIndx].compareTo(childToCompare) < 0) {
            T dataHolder = backingArray[currIndx];
            backingArray[currIndx] = backingArray[indxHolder];
            backingArray[indxHolder] = dataHolder;
            downHeap(indxHolder);
        } else {
            return;
        }
    }

    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMax() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty. No max exist");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
