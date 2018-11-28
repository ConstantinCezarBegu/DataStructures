package Lab5;

import java.io.File;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.AbstractMap;
import java.util.LinkedList;

public class WordCountHash {

	static void constructHeap(String[] a) {
		int n = a.length;
		for (int k = n / 2; k >= 1; k--)
			sink(a, k, n);
	}

	private static void heapSort(String[] pq) {
		int n = pq.length;
		constructHeap(pq);
		while (n > 1) {
			exch(pq, 1, n);
			n--;
			sink(pq, 1, n);
		}
	}

	private static void sink(String[] pq, int k, int n) {
		while (2 * k <= n) {
			int j = 2 * k;
			if (j < n && less(pq, j, j + 1))
				j++;
			if (!less(pq, k, j))
				break;
			exch(pq, k, j);
			k = j;
		}
	}

	private static boolean less(String[] pq, int i, int j) {
		return pq[i - 1].compareTo(pq[j - 1]) < 0;
	}

	private static void exch(String[] pq, int i, int j) {
		String swap = pq[i - 1];
		pq[i - 1] = pq[j - 1];
		pq[j - 1] = swap;
	}

	private static Entry<String, Integer> maxList(LinkedList<Entry<String, Integer>> list) {
		int maxCount = 0;
		String maxWord = "";
		for (int i = 0; i < list.size(); i++) {
			int count = list.get(i).getValue();
			if (count > maxCount) {
				maxWord = list.get(i).getKey();
				maxCount = count;
			}
		}
		return new AbstractMap.SimpleEntry<String, Integer>(maxWord, maxCount);
	}

	public static Entry<String, Integer> count_ARRAY_SORT(String[] tokens, String method) {
		int CAPACITY = 1000000;
		String[] words = new String[CAPACITY];
		int[] counts = new int[CAPACITY];
		if (method.equals("HEAP")) {
			heapSort(tokens);

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

		} else if (method.equals("HASH")) {
			HashMap254<String, Integer> map = new HashMap254<String, Integer>();
		//	Hashtable<String, Integer> map = new Hashtable<String, Integer>();
			int len = tokens.length;
			for (int i = 0; i < len; i++) {
				String token = tokens[i];
				Integer freq = map.get(token);
				if (freq == null)
					map.put(token, 1);
				else
					map.put(token, freq + 1);
			}

			int max = 0;
			String maxWord="";
			for (String k : map.keys())
				if (map.get(k) > max){
					max = map.get(k);
					maxWord=k;
				}			
			return new AbstractMap.SimpleEntry<String, Integer>(maxWord, max);
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

	static String[] readText(String PATH) throws Exception {
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
		String PATH = "";
		String[] METHODS = { "HEAP", "HASH" };
		String[] DATASETS = { "dblp10k","dblp100k","hackedString200kwords"};

		String[] tokens;
		// run the experiments on different data sets
		for (int j = 0; j < 3; j++) {
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