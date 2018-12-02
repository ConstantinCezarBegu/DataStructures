/** HashMap for 254. A simplified version of HashMap. 
 * Use separate chaining to resolve collisions. 
 */
package Lab5;

import java.util.LinkedList;
import java.util.Random; 

public class HashMap254<Key, Value> {
	private static final int INIT_CAPACITY = 4;
	private int n; // number of elements
	private int m; // hash table size
	private LinkedListForHash254<Key, Value>[] st; 
	// array of linked-list.
	
	public HashMap254() {
		this(INIT_CAPACITY);
	}

	// used in resize.
	public HashMap254(int m) {
		this.m = m;
		st = (LinkedListForHash254<Key, Value>[]) new LinkedListForHash254[m];
		for (int i = 0; i < m; i++)
			st[i] = new LinkedListForHash254<Key, Value>();
	}


	// resize the hash table to have the given number of chains,
	// rehashing all of the keys
	private void resize(int n) {
		
		HashMap254<Key, Value> temp = new HashMap254<Key, Value>(n);
		
		for (int i = 0; i < m; i++) {
			for (Key key : st[i].keys()) {
				temp.put(key, st[i].get(key));
			}
		}
		this.m = temp.m;
		this.n = temp.n;
		this.st = temp.st;
	}

	// hash value between 0 and m-1
	private int myhash(Key key) {
		int hash = 7;
		String k = (String) key; //here we assume keys are strings.
//        int base=31;
		int base=37;                                              //this works for the bonus point.
		for (int i = 0; i < k.length(); i++) {
			//calculate hashcode (h1) using polynomial method: 
			//hash=base^{n-1} key[0]+base^{n-2} key[1] +....+key[n-1]
//			hash=(int)Math.round((Math.pow(31,k.length()-i-1)))*k.charAt(i)+hash;				//bad hash
//			hash = (hash << 5) | (hash >>> 27);                    // 5-bit cyclic shift of the running sum
//			hash += (int) k.charAt(i);                            // add in next character
            hash = base * hash + k.charAt(i);           //good bonus point bash
		}
		//Implement h2 using division method
		hash=Math.abs(hash) % m;
		return hash;
	}

	public Value get(Key key) {
		int i = myhash(key);
		return st[i].get(key);
	}

	public void put(Key key, Value val) {
		// double table size if load factor m/n >0.1
		if (n >= 10 * m)
			resize(2 * m);
		// Put your code here. 
		// 1) get the hash value of the key i.
		// 2) then put (key, value) in the i-th linkedList implemented in LinkedListForHash254
		int i = myhash(key);
		if (st[i].get(key)==null) n++;
		st[i].put(key, val);


		// 3) you need to handle the case whether the key is already there. 
	}

	public void delete(Key key) {
		if (key == null)
			throw new IllegalArgumentException("argument to delete() is null");
		int i = myhash(key);
		if (st[i].get(key)!=null)
			n--;
		st[i].delete(key);

		// halve table size if average length of list <= 2
		if (m > INIT_CAPACITY && n <= 2 * m)
			resize(m / 2);
	}

	public LinkedList<Key> keys() {
		LinkedList<Key> queue = new LinkedList<Key>();
		for (int i = 0; i < m; i++) {
			for (Key key : st[i].keys())
				queue.add(key);
		}
		return queue;
	}
}

/*
Original time:

"C:\Program Files\Java\jdk-11\bin\java.exe" "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA 2018.2.6\lib\idea_rt.jar=64499:C:\Program Files\JetBrains\IntelliJ IDEA 2018.2.6\bin" -Dfile.encoding=UTF-8 -classpath C:\Users\const\IdeaProjects\DataStructures\out\production\DataStructures Lab5.WordCountHash
Data is dblp10k
HEAP method	 time=         141. Most popular word is of:4308
HASH method	 time=          97. Most popular word is of:4308
Data is dblp100k
HEAP method	 time=        1556. Most popular word is of:41755
HASH method	 time=         670. Most popular word is of:41755
Data is hackedString200kwords
HEAP method	 time=         149. Most popular word is aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa:5
HASH method	 time=         851. Most popular word is bbbbaabbbbbbbbbbbbaaaabbaaaaaaaa:5

Process finished with exit code 0









PART 2 upgrade:

"C:\Program Files\Java\jdk-11\bin\java.exe" "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA 2018.2.6\lib\idea_rt.jar=64504:C:\Program Files\JetBrains\IntelliJ IDEA 2018.2.6\bin" -Dfile.encoding=UTF-8 -classpath C:\Users\const\IdeaProjects\DataStructures\out\production\DataStructures Lab5.WordCountHash
Data is dblp10k
HEAP method	 time=         145. Most popular word is of:4308
HASH method	 time=         102. Most popular word is of:4308
Data is dblp100k
HEAP method	 time=        1488. Most popular word is of:41755
HASH method	 time=         588. Most popular word is of:41755
Data is hackedString200kwords
HEAP method	 time=         137. Most popular word is aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa:5
HASH method	 time=         215. Most popular word is bbbbbbbbbbbbbbaabbbbaabbaaaabbbb:5

Process finished with exit code 0











Bonus point upgrade:
"C:\Program Files\Java\jdk-11\bin\java.exe" "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA 2018.2.6\lib\idea_rt.jar=64514:C:\Program Files\JetBrains\IntelliJ IDEA 2018.2.6\bin" -Dfile.encoding=UTF-8 -classpath C:\Users\const\IdeaProjects\DataStructures\out\production\DataStructures Lab5.WordCountHash
Data is dblp10k
HEAP method	 time=         147. Most popular word is of:4308
HASH method	 time=          88. Most popular word is of:4308
Data is dblp100k
HEAP method	 time=        1462. Most popular word is of:41755
HASH method	 time=         347. Most popular word is of:41755
Data is hackedString200kwords
HEAP method	 time=         147. Most popular word is aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa:5
HASH method	 time=         157. Most popular word is bbaabbaaaaaaaaaaaaaaaaaabbaabbaa:5

Process finished with exit code 0

 */
