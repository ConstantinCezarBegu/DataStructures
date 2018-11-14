package midPractice;

import java.util.LinkedList;

public class stack <T> {

    private LinkedList<T> listOfStack;

    public stack(){
        listOfStack = new LinkedList<>();
    }

    public void push(T item) {
        listOfStack.addFirst(item);
    }

    public T pop() {
        T temp = listOfStack.getFirst();
        listOfStack.removeFirst();
        return temp;
    }

    public String toString () {
        StringBuffer str = new StringBuffer();
        for (T item:
             listOfStack) {
            str.append(item.toString() + "\n");
        }
        return str.toString();
    }
}
