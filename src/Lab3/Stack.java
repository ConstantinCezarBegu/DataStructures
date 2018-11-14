package Lab3;

public class Stack<E> {
    private final int sizeArr;
    private E[] arrOfStack;
    private int size;

    /**
     * This is the constructor for the stack.
     *
     * @param sizeArr The max size of the stack before resizing.
     */
    @SuppressWarnings("unchecked")
    public Stack(int sizeArr) {
        this.arrOfStack = (E[]) new Object[sizeArr];
        this.sizeArr = sizeArr;
        this.size = 0;
    }

    /**
     * This is the default constructor of the array.
     */
    public Stack() {
        this(10);
    }

    /**
     * Allows you to add a item at the top of the stack.
     *
     * @param item the item that you want to add.
     */
    public void push(E item) {
        if (size == sizeArr - 1) resize(sizeArr * 2);
        arrOfStack[size++] = item;
    }

    /**
     * Will return and remove the top item of the array.
     *
     * @return top item of the array.
     */
    public E pop() {
        return (isEmpty()) ? null : arrOfStack[(size--) - 1];
    }

    /**
     * This will allow you to peek at the top of the array.
     *
     * @return returns the array without removing it.
     */
    public E top() {
        return (isEmpty()) ? null : arrOfStack[size - 1];
    }

    /**
     * Will return the size of the stack.
     *
     * @return returns how many items are in the stack.
     */
    public int size() {
        return this.size;
    }

    /**
     * Will find if the stack is empty.
     *
     * @return returns true if the array is empty.
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Resize the array so that new elements can be added.
     *
     * @param newSize the new size of the array.
     */
    @SuppressWarnings("unchecked")
    private void resize(int newSize) {
        E[] newArray = (E[]) new Object[newSize];

        if (size >= 0) {
            System.arraycopy(arrOfStack, 0, newArray, 0, size);
        }

        arrOfStack = newArray;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            stringBuilder.append(arrOfStack[i].toString());
        }
        return stringBuilder.toString();
    }
}
