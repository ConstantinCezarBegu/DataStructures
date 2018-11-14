package Lab3;

import static java.lang.System.exit;

public class Lab3 {

    public static void main(String[] args) {
        testArray("(9 * [3 * {[(3 + 3)/5] * 7}])"); //balanced
        testArray("{3 ∗ (2 + [3 − [4/[6/9]]]})");
        testArray(" ((3 ∗ (9 − (4 ∗ (6 − 5))))");
        testArray(""); //balanced
    }

    /**
     * Will call the functions needed to test the array if balanced.
     *
     * @param str the string that you want to test.
     */
    private static void testArray(String str) {
        printIfCorrect(isBalanced(str.toCharArray()));
    }

    /**
     * Will determine if the array is balanced.
     *
     * @param string the array that you want to test converted as a array of char.
     * @return boolean indicating that balanced.
     */
    private static boolean isBalanced(char[] string) {
        Stack<Character> check = new Stack<Character>(string.length);
        for (char aString : string) {
            if (openBracket(aString)) check.push(aString);
            else if (closedBracket(aString)) {
                if (comparingBracket(check.pop()) != aString) return false;
            } else if (!checkInput(aString)) {
                System.out.println("Invalid characters in the string.");
                exit(1);
            }
        }
        return check.isEmpty();
    }

    /**
     * Print statement to show user that it is balanced.
     *
     * @param ans Diffrent message according the the boolean.
     */
    private static void printIfCorrect(boolean ans) {
        if (ans) System.out.println("This is a balanced operation.");
        else System.out.println("This is NOT a balanced operation.");
    }

    /**
     * Determines if the character is a open bracket.
     *
     * @param c the character to determine.
     * @return true if it is a open bracket.
     */
    private static boolean openBracket(char c) {
        return (c == '{') || (c == '[') || (c == '(');
    }

    /**
     * Finds if the character is a closed bracket.
     *
     * @param c the character to determine.
     * @return true if it is a closed bracket.
     */
    private static boolean closedBracket(char c) {
        return (c == '}') || (c == ']') || (c == ')');
    }

    /**
     * Converts open bracket to closed bracket for easy comparision.
     *
     * @param c the open bracket to convert.
     * @return the converted character.
     */
    private static char comparingBracket(char c) {
        if (c == '{') return '}';
        else if (c == '[') return ']';
        else return ')';
    }

    /**
     * Checks if the characters (except brackets) are valid inputs.
     *
     * @param c Character to check.
     * @return boolean indicating if the character is allowed.
     */
    private static boolean checkInput(char c) {
        return (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8'
                || c == '9' || c == '+' || c == '-' || c == '*' || c == '/' || c == ' ' || c == '∗' || c == '−');
    }

    /*
        The algorithm is in O(n).
        This is due that it goes through the whole array and validates it.
        The for loop creates the n effect.
        the rest is multiple O(1) operations that can be taken in a a new constant c.
        Since the n is the biggest operation cost we can conclude that it is O(n).
        If need for substitution proof just ask. It will be done on the spot.
     */
}
