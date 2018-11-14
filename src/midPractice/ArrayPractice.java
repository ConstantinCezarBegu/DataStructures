package midPractice;

import java.util.Stack;

public class ArrayPractice {
    public static void main(String[] args){
        int[] intArray = {9,7,8,6,4,5,3,1,2,0};
        insertionSort(intArray);
        printArray(intArray);

        testStack();
        testReverse();

        System.out.println(fibonacciRecursionBad(6));
        System.out.println(factorialRecursion(6));
    }

    private static void printArray(int[] intArray){
        String ans = "This is the array: [";
        for (int i = 0; i < intArray.length - 1; i++) {
            ans += (intArray[i] + ", ");
        }
        ans += intArray[intArray.length - 1] + "]";

        System.out.println(ans);
    }

    private static void swap (int[] intArray, int index1, int index2) {
        int temp = intArray[index1];
        intArray[index1] = intArray[index2];
        intArray[index2] = temp;
    }

    private static void selectionSort(int[] intArray){
        for(int i = 0; i < intArray.length - 1; i++){
            int min = i;

            for(int j = i; j < intArray.length; j ++){
                if(intArray[min] > intArray[j]){
                    min = j;
                }
            }

            if(min != i){
                swap(intArray, min, i);
            }
        }

        System.out.println("Success");
    }

    private static void insertionSort(int[] intArray){

        for(int i = 1; i < intArray.length; i ++){

            for(int j = i; (j > 0) && (intArray[j - 1] > intArray[j]); j--){
                swap(intArray, j, j - 1);
            }

        }
        System.out.println("Success");
    }

    private static void mergeSort(){
        //Todo complete the merge sort
    }

    //a2 = a1 + a0
    private static int fibonacciRecursionBad(int positionToFind){
        if(positionToFind == 1 || positionToFind == 0) return 1;
        else return fibonacciRecursionBad(positionToFind - 1) + fibonacciRecursionBad(positionToFind - 2);
    }

    //a!
    private static int factorialRecursion (int number) {
        if(number == 1) return 1;
        else return number * factorialRecursion(number - 1);
    }

    private static void testStack () {
        System.out.println("This is the stack");
        stack<Integer> stack = new stack<>();
        stack.push(5);
        stack.push(7);
        stack.push(1);
        stack.push(0);
        System.out.println("This was popped: " +    stack.pop());
        System.out.println(stack.toString());
    }

    private static void reverseBad (String[] arr) {
        Stack<String> reverseStack = new Stack<>();

        for (int i = 0; i < arr.length; i++) {
            reverseStack.push(arr[i]);
        }

        for(int i = 0; i < arr.length; i++){
            arr[i] = reverseStack.pop();
        }
    }

    private static void testReverse() {
        String[] arr = {"Cezar", "Zito", "Ahmad"};

        reverseBad(arr);

        for (String item:
             arr) {
            System.out.printf("%s, ", item);

        }
        System.out.printf("\n");
    }


    //Todo binarysearch

    //Todo fibonaccygood

    //Todo reversearray


}
