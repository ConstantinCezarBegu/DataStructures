package Test;

public class Test {
    public static void main(String[] args){
        for(int i = 0; i < 7; i++){
            System.out.println(fibRecursive(i));
        }

    }

    public static int fibRecursive (int number) {
        if(number == 0 || number == 1) return 1;
        else System.out.println("ok");
        return fibRecursive(number - 1) + fibRecursive(number - 2);
    }
}
