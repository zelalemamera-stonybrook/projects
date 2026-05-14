
/**
 * This algorithm takes in as its input a list of subsets of a universal set U,
 * and searches through this list efficiently to produce the smallest subset
 * whose union equals u. several methods are used to implement a backtracking algorithm with pruning.
 * and finally, the main method reads in files which represent the input and controls the flow of the program.
 */
public class FindMinimalSetCover {
	static boolean debug = false;
	static int currentMinimalCoverSize = 1000000;
	public static int sizeOfUniversalset;
	
	public static void setCoverAlgorithm(int[][] sm, int[] universalSet, SetCoverSolution a, int n, int k) {
		int[][] subsetTable;

		subsetTable = makeSubsetTable(universalSet, sm);
		
		int[][] sortedSm;
		sortedSm = sortSubsets(sm, subsetTable, universalSet, n);
		System.out.println("Sorted list: ");
		for(int i = 1; i < sortedSm.length; i++) {
			print(sortedSm[i]);
		}
		subsetTable = makeSubsetTable(universalSet, sortedSm);
		
		backtrack(a, k, sortedSm, n, subsetTable, universalSet);
		}
///
	public static void backtrack(SetCoverSolution a, int k, int[][]sm, int n, int[][] subsetTable, int[] universalSet) {
		int[] c;
		int nc;
		if(a.subsets[0] + 1 >= currentMinimalCoverSize)
			if(a.subsets[0] + 1 == currentMinimalCoverSize)
				if(checkSolution(a)) {
					currentMinimalCoverSize = a.subsets[0];
					process(a, sm);
					return;
				}
				else
					return;
			else
				return;
		if(checkSolution(a)) {
			currentMinimalCoverSize = a.subsets[0];
			process(a, sm);
			return;
		}
		if(k == n) 
			return;
		else {
			k = k + 1;
			nc = 0;
			c = constructCandidates(a, k, nc, sm, subsetTable, universalSet);
			for(int i = 0; i < c.length; i++) {
					if(c[i] == 1) {
						a.subsets[k] = c[i];
						a.subsets[0]++;
						a.addElements(sm, k);
						if(debug) {
						System.out.println("Set added");
						print(sm[k]);
						System.out.println("currently a has: " + a.elements[0] + " elements");
						System.out.println("and " + a.subsets[0] + " sets");
						}
						backtrack(a, k, sm, n, subsetTable, universalSet);
						a.subsets[k] = c[i];
						a.subsets[0]--;
						a.removeElements(sm, k);
						if(debug) {
						System.out.println("Set removed");
						print(sm[k]);
						System.out.println("currently a has: " + a.elements[0] + " elements");
						System.out.println("and " + a.subsets[0] + " sets");
						}
					}
					else {
						a.subsets[k] = c[i];
						backtrack(a, k, sm, n, subsetTable, universalSet);	
				}
			}
		}
		
	}
///
	public static int[][] sortSubsets(int[][] sm, int[][] subsetTable, int[] universalSet, int n){
		int smallest = 1; int index = 1; int[][] sortedSm;
		sortedSm = new int[sm.length][];
		while(smallest <= n) {
			for(int i = 1; i < universalSet.length; i++) {
				if(universalSet[i] == smallest) {
					for(int j = 0; j < subsetTable[i].length; j++) {
						if(sm[subsetTable[i][j]] != null) {
							sortedSm[index] = sm[subsetTable[i][j]];
							sm[subsetTable[i][j]] = null;
							index++;
						}
					}
				}
			}
			int min = 1000;
			for(int i = 1; i < universalSet.length; i++) {
				if(universalSet[i] < min && universalSet[i] > smallest)
					min = universalSet[i];
			}
			smallest = min;
		}
		return sortedSm;
	}
///
	public static int[][] makeSubsetTable(int[] universalSet, int[][] sm){
		int[][] subsetTable;
		subsetTable = new int[universalSet.length][];
		for(int i = 1; i < sm.length; i++) {
			for(int j = 0; j < sm[i].length; j++) {
				universalSet[sm[i][j]]++;
			}
		}
		for(int i = 1; i < subsetTable.length; i++) {
			subsetTable[i] = new int[universalSet[i]];
		}
		for(int i = 1; i <sm.length; i++) {
			for(int j = 0; j < sm[i].length; j++) {
				for(int k = 0; k < subsetTable[sm[i][j]].length; k++) {
					if(subsetTable[sm[i][j]][k] == 0) {
						subsetTable[sm[i][j]][k] = i;
						break;
					}
				}
			}
		}
		return subsetTable;
	}
///
	public static int[] constructCandidates(SetCoverSolution a, int k, int nc, int[][] sm, int[][] subsetTable, int[] universalSet) {
		int[] c; boolean essential;
		
		for(int j = 0; j < sm[k].length; j++) {
			if(a.elements[sm[k][j]] == 0) {
				essential = true;
				for(int l = 0; l < subsetTable[sm[k][j]].length; l++) {
					if(k < subsetTable[sm[k][j]][l]) {
						essential = false;
						break;
					}
				}
				if(essential) {
					c = new int[1];
					c[0] = 1;
					return c;
				}
			}
		}
		essential = false;
		for(int j = 0; j < sm[k].length; j++) {
			if(a.elements[sm[k][j]] == 0) {
				essential = true;
			}
		}
		if(!essential) {
			c = new int[1];
			c[0] = 0;
			return c;
		}
		c = new int[2];
		c[0] = 1;
		c[1] = 0;
		return c;
		
		
	}
///
	public static boolean checkSolution(SetCoverSolution a) {
		return a.elements[0] == FindMinimalSetCover.sizeOfUniversalset;
	}
///
	public static void process(SetCoverSolution a, int[][] sm) {
		System.out.println("Solution found: " + a.subsets[0] + " size");
		for(int i = 1; i < a.subsets.length; i ++) {
			if(a.subsets[i] == 1) {
				print(sm[i]);
			}
		}
	}
///
	public static void print(int[] s) {
		if(s == null) System.out.println("{ }");
		else {
		System.out.print("{");
		for(int i = 0; i < s.length - 1; i++) {
			System.out.print(s[i] + ", ");
		}
		System.out.print(s[s.length - 1]);
		System.out.print("}\n");
		}
	}
///
	public static void main(String[] args) {
		int r = 9;
		java.io.File file = new java.io.File("/Users/zelalem/Documents/CSE 373/HW4_files/file" + r + ".txt");
		java.util.Scanner sc;
		try {
		sc = new java.util.Scanner(file);
		}
		catch(java.io.FileNotFoundException e) {
			System.out.print("File Not found");
			return;
		}
		int sizeOfUniversalSet = sc.nextInt();
		FindMinimalSetCover.sizeOfUniversalset = sizeOfUniversalSet;
		int[] universalSet = new int[sizeOfUniversalSet + 1];
		sc.nextLine();
		int n = sc.nextInt();
		sc.nextLine();
		int[][] sm = new int[n + 1][];
		SetCoverSolution a = new SetCoverSolution(n, sizeOfUniversalSet);
		int i = 1;
		while(i <= n) {
			String g; String[] gp;
			g = sc.nextLine();
			gp = g.split("\s");
			sm[i] = new int[gp.length];
			if(g.isBlank()) {
				sm[i] = null;
			}else {
			for(int j = 0; j < gp.length; j++) {
				sm[i][j] = Integer.parseInt(gp[j]);
			}}
			i++;
	}
		System.out.println("total subsets: ");
		for(int k = 1; k < sm.length; k++) {
			print(sm[k]);
		}
		setCoverAlgorithm(sm, universalSet, a, n, 0);
		sc.close();
	}	
///
}
