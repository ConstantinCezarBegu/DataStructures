package Lab2;

import java.io.File;
import java.util.*;
import java.util.Map.Entry;

/**
 * Data is 200
 * MERGE method	     time=           8. Most popular word is of:97
 * QUICK method	     time=           5. Most popular word is of:97
 * INSERT method	 time=          14. Most popular word is of:97
 * SELECT method	 time=          21. Most popular word is of:97
 * JAVA method	     time=           7. Most popular word is of:97
 * <p>
 * Data is 500
 * MERGE method	     time=           8. Most popular word is of:220
 * QUICK method	     time=           6. Most popular word is of:220
 * INSERT method	 time=          35. Most popular word is of:220
 * SELECT method	 time=          46. Most popular word is of:220
 * JAVA method	     time=           4. Most popular word is of:220
 * <p>
 * Data is 1k
 * MERGE method	     time=          10. Most popular word is of:429
 * QUICK method	     time=           6. Most popular word is of:429
 * INSERT method	 time=         101. Most popular word is of:429
 * SELECT method	 time=         192. Most popular word is of:429
 * JAVA method	     time=           7. Most popular word is of:429
 * <p>
 * Data is 5k
 * MERGE method	     time=          39. Most popular word is of:2117
 * QUICK method	     time=          19. Most popular word is of:2117
 * INSERT method	 time=        6901. Most popular word is of:2117
 * SELECT method	 time=       14871. Most popular word is of:2117
 * JAVA method	     time=          22. Most popular word is of:2117
 * <p>
 * Data is 10k
 * MERGE method	     time=          50. Most popular word is of:4308
 * QUICK method	     time=          45. Most popular word is of:4308
 * INSERT method	 time=       39668. Most popular word is of:4308
 * SELECT method	 time=       42959. Most popular word is of:4308
 * JAVA method	     time=         105. Most popular word is of:4308
 */
public class WordCountSort {

    public static void selectionSort(String[] data) {
        int n = data.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (data[minIndex].compareTo(data[j]) < 0) {
                    minIndex = j;
                }
            }
            if (i != minIndex)
                swap(data, minIndex, i);

        }
    }

    private static void insertionSort(String[] data) {
        insertionSort(data, 0, data.length - 1);
    }

    private static void insertionSort(String[] data, int lo, int hi) {
        for (int k = lo + 1; k <= hi; k++) {
            String cur = data[k];
            int j = k;
            while (j > lo && data[j - 1].compareTo(cur) > 0) {
                data[j] = data[j - 1];
                j--;
            }
            data[j] = cur;
        }
    }

    /**
     * Merge two strings. See the textbook for explanation.
     **/
    public static void merge(String[] S1, String[] S2, String[] S) {
        int i = 0, j = 0;
        while (i + j < S.length) {
            if (j == S2.length || (i < S1.length && S1[i].compareTo(S2[j]) < 0))
                S[i + j] = S1[i++];
            else
                S[i + j] = S2[j++];
        }
    }

    private static void mergeSort(String[] S) {
        int n = S.length;
        if (n < 2)
            return;
        int mid = n / 2;
        // partition the string into two strings
        String[] S1 = Arrays.copyOfRange(S, 0, mid);
        String[] S2 = Arrays.copyOfRange(S, mid, n);
        mergeSort(S1);                                        // separate the array /2 each time thus leading to log(n) callback
        mergeSort(S2);
        merge(S1, S2, S);
    }


    private static void quickSortRecursion(String[] data) {
        quickSortRecursion(data, 0, data.length - 1);
    }



    // https://en.wikipedia.org/wiki/Quicksort#Hoare_partition_scheme
    private static void quickSortRecursion(String[] data, int lo, int hi) {
        if (hi - lo < 8) {
            insertionSort(data, lo, hi);
            return;
        }
        if (lo < hi) {
            int p = partition(data, lo, hi);
            quickSortRecursion(data, lo, p);
            quickSortRecursion(data, p + 1, hi);
        }
    }

    private static int partition(String[] data, int lo, int hi) {
//        String pivot = data[lo];
        String pivot = medianOfThreePivot(data, lo, hi);
        int i = lo - 1;
        int j = hi + 1;
        for (; ; ) {
            do {
                i++;
            } while (data[i].compareTo(pivot) < 0);
            do {
                j--;
            } while (data[j].compareTo(pivot) > 0);
            if (i >= j) {
                return j;
            }
            swap(data, i, j);
        }
    }
    private static String medianOfThreePivot(String[] data, int lo, int hi) {
        int mid = (lo + hi) / 2;
//        int mid = lo + (hi - lo) / 2;
        if (data[mid].compareTo(data[lo]) < 0) {
            swap(data, lo, mid);
        }
        if (data[hi].compareTo(data[lo]) < 0) {
            swap(data, lo, hi);
        }
        if (data[mid].compareTo(data[hi]) < 0) {
            swap(data, mid, hi);
        }
        return data[hi];
    }

    private static void swap(String[] array, int i, int j) {
        String tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    public static Entry<String, Integer> count_ARRAY_SORT(String[] tokens, String sortMethod) {
        int CAPACITY = 1000000;
        String[] words = new String[CAPACITY];
        int[] counts = new int[CAPACITY];
        if (sortMethod.equals("SELECT"))
            selectionSort(tokens);
        else if (sortMethod.equals("INSERT"))
            insertionSort(tokens);
        else if (sortMethod.equals("MERGE"))
            mergeSort(tokens);
        else if (sortMethod.equals("JAVA"))
            Arrays.sort(tokens);
        else if (sortMethod.equals("QUICK"))
            quickSortRecursion(tokens);
        else
            System.out.println(sortMethod + " sorting method does not exist.");

        int j = 0, k = 0;
        int len = tokens.length;
        while (j < len - 1) {
            int duplicates = 1;
            while (j < len - 2 & tokens[j].equals(tokens[j + 1])) {
                j++;
                duplicates++;
            }

            words[k] = tokens[j];
            counts[k] = duplicates;
            j++;
            k++;
        }

        /** get the max count **/
        int maxCount = 0;
        String maxWord = "";
        for (int i = 0; i < CAPACITY & words[i] != null; i++) {
            if (counts[i] > maxCount) {
                maxWord = words[i];
                maxCount = counts[i];
            }
        }
        return new AbstractMap.SimpleEntry<String, Integer>(maxWord, maxCount);
    }


    @SuppressWarnings("Duplicates")
    private static String[] readText(String PATH) throws Exception {
        Scanner doc = new Scanner(new File(PATH)).useDelimiter("[^a-zA-Z]+");
        // tokenize text. any characters other than English letters(a-z and A-Z
        // ) are delimiters.
        int length = 0;
        while (doc.hasNext()) {
            doc.next();
            length++;
        }

        String[] tokens = new String[length];
        Scanner s = new Scanner(new File(PATH)).useDelimiter("[^a-zA-Z]+");
        length = 0;
        while (s.hasNext()) {
            tokens[length] = s.next().toLowerCase();
            length++;
        }
        doc.close();
        return tokens;
    }

    public static void main(String[] args) throws Exception {
        String PATH = "dblp";
        String[] METHODS = {"QUICK", "MERGE"};
        String[] DATASETS = {"200", "500", "1k", "5k", "10k", "100k", "1m", ""};

        String[] tokens;
        // run the experiments on different data sets
        for (int j = 0; j < 7; j++) {
            // run the experiments using different methods
            System.out.println("Data is " + DATASETS[j]);
            for (int i = 0; i < 2; i++) {
                tokens = readText(PATH + DATASETS[j] + ".txt");
                long startTime = System.currentTimeMillis();
                Entry<String, Integer> entry = count_ARRAY_SORT(tokens, METHODS[i]);
                long endTime = System.currentTimeMillis();
                String time = String.format("%12d", endTime - startTime);
                System.out.println(METHODS[i] + " method\t time=" + time + ". Most popular word is " + entry.getKey()
                        + ":" + entry.getValue());
            }
        }
    }
}
