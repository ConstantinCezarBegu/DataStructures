package  Lab2;

import com.sun.jdi.request.DuplicateRequestException;

import java.io.File;
import java.util.*;
import java.util.Map.Entry;

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

	public static void insertionSort(String[] data) {
		int n = data.length;
		for (int k = 1; k < n; k++) {
			String cur = data[k];
			int j = k;
			while (j > 0 && data[j - 1].compareTo(cur) > 0) {
				data[j] = data[j - 1];
				j--;
			}
			data[j] = cur;
		}
	}

	/** Merge two strings. See the textbook for explanation. **/
	public static void merge(String[] S1, String[] S2, String[] S) {
		int i = 0, j = 0;
		while (i + j < S.length) {
			if (j == S2.length || (i < S1.length && S1[i].compareTo(S2[j]) < 0))
				S[i + j] = S1[i++];
			else
				S[i + j] = S2[j++];
		}
	}

	public static void mergeSort(String[] S) {
		int n = S.length;
		if (n < 2)
			return;
		int mid = n / 2;
		// partition the string into two strings
		String[] S1 = Arrays.copyOfRange(S, 0, mid);
		String[] S2 = Arrays.copyOfRange(S, mid, n);
		mergeSort(S1);
		mergeSort(S2);
		merge(S1, S2, S);
	}


	/*
algorithm quicksort(A, lo, hi) is
if lo < hi then
	p := partition(A, lo, hi)
	quicksort(A, lo, p)
	quicksort(A, p + 1, hi)

algorithm partition(A, lo, hi) is
    pivot := A[lo]
    i := lo - 1
    j := hi + 1
    loop forever
        do
            i := i + 1
        while A[i] < pivot

        do
            j := j - 1
        while A[j] > pivot

        if i >= j then
            return j

        swap A[i] with A[j]
	 */
	public static void quickSortInPlace(String[] S) {
		quickSortRecursion(S, 0 , S.length -1);
	}

	public static void quickSortRecursion (String[] S, int low, int high) {
		int i = low;
		int j = high;

		if (j - i >= 1) {
			String pivot = S[i];

			while (j > i) {
				while (S[i].compareTo(pivot) <= 0 && i < high && j > i){
					i++;
				}
				while (S[j].compareTo(pivot) >= 0 && j > low && j >= i){
					j--;
				}
				if (j > i) swap(S, i, j);
			}

			swap(S, low, j);
			quickSortRecursion(S, low, j - 1);
			quickSortRecursion(S, j + 1, high);
		}
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
			quickSortInPlace(tokens);
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
		String[] METHODS = { "MERGE", "INSERT", "SELECT" };
		String[] DATASETS = { "200", "500", "1k", "5k", "10k", "100k", "1m", "" };

		String[] tokens;
		// run the experiments on different data sets
		for (int j = 0; j < 5; j++) {
			// run the experiments using different methods
			System.out.println("Data is " + DATASETS[j]);
			for (int i = 0; i < 3; i++) {
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
