package Lab4;

import java.io.File;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.AbstractMap;
import java.util.Arrays;

public class WordCountHeap {

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

	@SuppressWarnings("Duplicates")
	private static void mergeSort(String[] S) {
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

	private static void constructHeap(String[] a){
        int n = a.length;
        for (int k = n/2; k >= 1; k--)
            sink(a, k,n);
	}

    private static void heapSort(String[] pq) {
        int n = pq.length;
        constructHeap(pq);
        while (n > 1) {
            exch(pq, 1, n);
            n--;
            sink(pq, 1,n);
        }
    }
    
    private static void sink(String[] pq, int k, int n) {
	    //your code goes here
		while (2*k <= n){
			int parent = 2*k;
			if(parent < n && less(pq, parent, parent + 1)) parent++;
			if (!less(pq, k, parent)) break;
			exch(pq, k, parent);
			k = parent;
		}
    }

/** Note that less and exch are defined to offset the 1-based array **/
    private static boolean less(String[] pq, int i, int j) {
        return pq[i-1].compareTo( pq[j-1])<0;
    }

    private static void exch(String[] pq, int i, int j) {
        String swap = pq[i-1];
        pq[i-1] = pq[j-1];
        pq[j-1] = swap;
    }

	private static void swap(String[] array, int i, int j) {
		String tmp = array[i];
		array[i] = array[j];
		array[j] = tmp;
	}

	@SuppressWarnings("Duplicates")
	private static Entry<String, Integer> count_ARRAY_SORT(String[] tokens, String sortMethod) {
		int CAPACITY = 1000000;
		String[] words = new String[CAPACITY];
		int[] counts = new int[CAPACITY];
		switch (sortMethod) {
			case "HEAP":
				heapSort(tokens);
				break;
			case "MERGE":
				mergeSort(tokens);
				break;
			default:
				System.out.println(sortMethod + " sorting method does not exist.");
				break;
		}

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

		//get the max count **/
		int maxCount = 0;
		String maxWord = "";
		for (int i = 0; i < CAPACITY & words[i] != null; i++) {
			if (counts[i] > maxCount) {
				maxWord = words[i];
				maxCount = counts[i];
			}
		}
		HeapInt h = new HeapInt(counts);
		for (int i=0; i<20; i++)
			System.out.println(h.removeMax());

		return new AbstractMap.SimpleEntry<>(maxWord, maxCount);
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

	@SuppressWarnings("Duplicates")
	public static void main(String[] args) throws Exception {
		String PATH = "dblp";
		String[] METHODS = { "HEAP","MERGE"};
		String[] DATASETS = { "200", "500", "1k", "5k", "10k", "100k", "1m", "" };

		String[] tokens;
		// run the experiments on different data sets
		for (int j = 0; j < 8; j++) {
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

	static class HeapInt {
		private int [] heap ;
		int n =0;
		// Heap constructor
		public HeapInt (int [] a){
			n = a.length ;
			for (int k = n/2; k >= 1; k--){
				heapify(a ,n,k);
			}
			heap =a;
		}
		public int removeMax () {
			heapify(heap, n, 0);
			int max = heap[0];
			//heap[0] = heap[n -1];
			exch(heap, 1, n);
			n--;
			heapify(heap, n,max);
			return max;
		}
		void heapify(int arr[], int n, int i)
		{
			int largest = i; // Initialize largest as root
			int l = 2*i + 1; // left = 2*i + 1
			int r = 2*i + 2; // right = 2*i + 2

			// If left child is larger than root
			if (l < n && arr[l] > arr[largest]){
				largest = l;
			}


			// If right child is larger than largest so far
			if (r < n && arr[r] > arr[largest]){
				largest = r;
			}


			// If largest is not root
			if (largest != i)
			{
				int swap = arr[i];
				arr[i] = arr[largest];
				arr[largest] = swap;

				// Recursively heapify the affected sub-tree
				heapify(arr, n, largest);
			}
		}
		private void exch(int[] pq, int i, int j) {
			int swap = pq[i-1];
			pq[i-1] = pq[j-1];
			pq[j-1] = swap;
		}
	}
}
