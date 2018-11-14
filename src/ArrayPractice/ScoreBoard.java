package ArrayPractice;
// (practice website)       https://www.geeksforgeeks.org/sorting-algorithms/

public final class ScoreBoard {
    private static int CAPACITY = 10;

    private GameEntry[] gameEntries;
    private int gameCapacity;

    public ScoreBoard() {
        gameEntries = new GameEntry[CAPACITY];
        gameCapacity = 0;
    }

    /*
        how to sort a primitive array using the build in java sort. This will not work on objects such as this.
        Arrays.sort(tokens);
     */

    /**
     * Attempt to add a new score to the collection (if it is high enough)
     *
     * @param entry the entry that you want to add to the array.
     */
    public void add(GameEntry entry) {
        if (gameCapacity < CAPACITY) {
            gameEntries[gameCapacity++] = entry;
        } else if (gameEntries[gameCapacity - 1].getScore() < entry.getScore()) {
            gameEntries[gameCapacity - 1] = entry;
        } else {
            System.out.println("Sadly your score will not make it on the score board... :(");
        }
    }

    /**
     * Array swap is possible since we change the array index and not the actual address of a object (not possible in java).
     *
     * @param gameEntries this is the array that will have the index position objects swapped.
     * @param index       this is position of the item you want to swap inside the array.
     * @param indexToSwap this is the position of the second item that you want to swap inside the array.
     */
    private static void swapArray(GameEntry[] gameEntries, int index, int indexToSwap) {
        GameEntry temp = gameEntries[index];
        gameEntries[index] = gameEntries[indexToSwap];
        gameEntries[indexToSwap] = temp;
    }

    // TODO: binary Search, Linear Search
    // TODO: Create Merge Sort, Quick Sort


    /**
     * selection sort will sort the array by using the selection sort algorithm.
     * this means that it will look throughout the array for the smallest array and then swap the index with the min pos.
     */
    public void selectionSort() {
        for (int index = 0; index < gameCapacity - 1; index++) {
            int minPos = index;
            for (int index2 = index; index2 < gameCapacity; index2++) {
                if (gameEntries[minPos].getScore() > gameEntries[index2].getScore()) {
                    minPos = index2;
                }
            }
            if (minPos != index) {
                swapArray(gameEntries, minPos, index);
            }
        }
    }

    /**
     * the bubbleSort will sort in Bubble Sort method
     * by repeatedly swapping the adjacent elements if they are in wrong order.
     * This can be made recursively by making the outer loop recursive and calling inside it n - 1; (not tne best thing)
     */
    public void bubbleSort() {
        for (int index = 0; index < gameCapacity - 1; index++) {
            for (int index2 = 0; index2 < gameCapacity - index - 1; index2++) {
                if (gameEntries[index2].getScore() > gameEntries[index2 + 1].getScore()) {
                    swapArray(gameEntries, index2, index2 + 1);
                }
            }
        }
    }

    /**
     * insertion sort will sort the array using it's own method.
     * 1) Create an empty sorted (or result) list
     * 2) Traverse the given list, do following for every node.
     * ......a) Insert current node in sorted way in sorted or result list.
     * 3) Change head of given linked list to head of sorted (or result) list.
     */
    public void insertionSort () {
        for (int index = 1; index < gameCapacity - 1; index++){
            for(int index2 = index; (index2 > 0) && (gameEntries[index2 - 1].getScore() > gameEntries[index2].getScore()); index2-- ){
                swapArray(gameEntries, index, index2);
            }
        }
    }

    public void mergeSort(){

    }

    public void mergeSortRecursion(GameEntry[] gameEntries, int left, int right){

        int arrayLength = left + (right - left/2);
        int mid = arrayLength /2;
        mergeSortRecursion(gameEntries, 0, mid);
        mergeSortRecursion(gameEntries, mid + 1, arrayLength);

        merge(gameEntries, left, mid, right);

    }

    private void merge(GameEntry[] gameEntries, int left, int mid, int right) {

        // Find the size of the sub array.
        int sizeLeft = mid - left - 1;
        int sizeRight = right - mid;

        //Create temp array.
        GameEntry[] leftArray = new GameEntry[sizeLeft];
        GameEntry[] rightArray = new GameEntry[sizeRight];

        //Copy the data from the old array into the new one.

        for(int index = 0; index < sizeLeft; index++){

        }


    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("(NAME, SCORE)\n");
        for (int i = 0; i < gameCapacity; i++) {
            stringBuilder.append(gameEntries[i].toString());
        }
        return stringBuilder.toString();
    }

}
