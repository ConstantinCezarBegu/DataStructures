package Lab1;

import java.io.File;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.function.Function;

/* this is the scores that I got:

Disclaimer: This test was done on a I7 7700HQ

time	        3064	of:2117     (Old array list)
time	         579	of:2117     (Improved array list)
time	      230240	of:2117     (Old linked list)
time	        1424	of:2117     (Improved array list)

 */

public class WordCountLinkedList254 {
	public static Entry<String, Integer> count_ARRAY(String[] tokens) {
		int CAPACITY = 10000;
		String[] words = new String[CAPACITY];
		int[] counts = new int[CAPACITY];
		for (int j = 0; j < tokens.length; j++) {
			String token = tokens[j];
			for (int i = 0; i < CAPACITY; i++) {
				if (words[i] == null) {
					words[i] = token;
					counts[i] = 1;
					break;
				} else if (words[i].equals(token))
					counts[i] = counts[i] + 1;
			}
		}

		int maxCount = 0;
		String maxWord = "";
		for (int i = 0; i < CAPACITY - 1 & words[i] != null; i++) {
			if (counts[i] > maxCount) {
				maxWord = words[i];
				maxCount = counts[i];
			}
		}
		return new AbstractMap.SimpleEntry<String, Integer>(maxWord, maxCount);
	}

    public static Entry<String, Integer> count_ARRAY_IMPROVED(String[] tokens) {
        int CAPACITY = 10000;
        int maxCount = 0;
        String maxWord = "";

        String[] words = new String[CAPACITY];
        int[] counts = new int[CAPACITY];
        for (String token : tokens) {
            for (int i = 0; i < CAPACITY; i++) {
                if (words[i] == null || words[i].equals(token)) {
                    words[i] = token;
                    counts[i]++;
                    if (counts[i] > maxCount) { //this gives a negligible increase in performance due to the fact that we don't need to iterate through another list secretly (it is negligible due to the fact that there is not a lot of new words).
                        maxWord = words[i];
                        maxCount = counts[i];
                    }
                    //performance increase is due to the missing break statement in the original count_ARRAY
                    break;
                }
            }
        }
        return new AbstractMap.SimpleEntry<String, Integer>(maxWord, maxCount);
    }

	public static Entry<String, Integer> count_LINKED_LIST(String[] tokens) {
		LinkedList<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>();
		for (int j = 0; j < tokens.length; j++) {
			String word = tokens[j];
			boolean found = false;
			for (int i = 0; i < list.size(); i++) {
				Entry<String, Integer> e = list.get(i);

				if (word.equals(e.getKey())) {
					e.setValue(e.getValue() + 1);
					list.set(i, e);
					found = true;
					break;
				}
			}
			if (!found)
				list.add(new AbstractMap.SimpleEntry<String, Integer>(word, 1));
		}

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

    public static Entry<String, Integer> count_LINKED_LIST_IMPROVED(String[] tokens) {
        LinkedList<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>();
        int maxCount = 0;
        String maxWord = "";
        for (String word : tokens) {
            ListIterator<Entry<String, Integer>> listIterator = list.listIterator(); //The performance increase is due to the fact that Iterators do not need to start at the beginning of the list to get the item at a certain position.
            boolean found = false;                                                   // This happens when using .get therefore for each item iterated it will iterate through the whole list from the beginning to get the item. This is unnecessary process wasted.
            for (int i = 0; i < list.size(); i++) {
                Entry<String, Integer> e = listIterator.next();
                if (word.equals(e.getKey())) {
                    e.setValue(e.getValue() + 1);
                    list.set(i, e);
                    found = true;
                    int count = e.getValue();
                    if (count > maxCount) {  //this gives a negligible increase in performance due to the fact that we don't need to iterate through another list secretly (it is negligible due to the fact that there is not a lot of new words).
                        maxWord = e.getKey();
                        maxCount = count;
                    }
                    break;
                }
            }
            if (!found)
                list.add(new AbstractMap.SimpleEntry<String, Integer>(word, 1));
        }

        return new AbstractMap.SimpleEntry<String, Integer>(maxWord, maxCount);
    }

	static String[] readText(String PATH) throws Exception {
		Scanner doc = new Scanner(new File(PATH)).useDelimiter("[^a-zA-Z]+");
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
	    timeReader(WordCountLinkedList254::count_ARRAY);
        timeReader(WordCountLinkedList254::count_ARRAY_IMPROVED);

	    timeReader(WordCountLinkedList254::count_LINKED_LIST);
	    timeReader(WordCountLinkedList254::count_LINKED_LIST_IMPROVED);
	}

	//this lambda function is made to easily test the functions individually without code reuse.
	private static void timeReader(Function<String[], Entry<String, Integer>> function) throws Exception{
        String PATH = "dblp5k.txt";
        String[] tokens = readText(PATH);
        long startTime = System.currentTimeMillis();
        Entry<String, Integer> entry = function.apply(tokens);
        long endTime = System.currentTimeMillis();
        String time = String.format("%12d", endTime - startTime);
        System.out.println("time\t" + time + "\t" + entry.getKey() + ":" + entry.getValue());
    }

}
