/***
 * implements a dynamic program which takes string x and y and finds the minimal number of edits required to 
 * convert string x into y, subsequently, the algorithm returns the sequence of minimal moves which result in the
 * conversion. 
 */
public class StringMatchDynamic {
/***
 * the main method tests the program
 */
	public static void main(String[] args) {
		String x = args[0];
		String y = args[1];
		System.out.println(y);
		System.out.println(x);
		stringMatch(x,y);
	}
/**
 * the dynamic algorithm for string match
 */
	public static void stringMatch(String x, String y) {
		int n, m; int[][] cell1; 
		String[][] cell2;
		n = x.length();
		m = y.length();
		cell1 = new int[n+1][m+1];
		cell2 = new String[n+1][m+1];
		cell1[0][0] = 0;
		for(int i = 1; i <=n; i++) {
			cell1[i][0] = cell1[i - 1][0] + 1;
		}
		for(int i = 1; i <=m; i++) {
			cell1[0][i] = cell1[0][i - 1] + 1;
		}
		for(int i = 1; i <= n; i++) {
			for(int j = 1; j <= m; j ++) {
				int match, sub, insert, delete, min;
				int[] costs = new int[3];
				match = cell1[i - 1][j - 1];
				sub = match + 1;
				insert = 1 + cell1[i][j - 1];
				delete = 1 + cell1[i - 1][j];
				min = 1000000;
				costs[1] = insert;
				costs[2] = delete;
				if(x.charAt(i - 1) == y.charAt(j - 1)) {
					costs[0] = match;
					for(int k = 0; k < 3; k++) {
						if (costs[k] < min) {
							min = costs[k];
							if(k == 0) {
								cell1[i][j] = cell1[i - 1][j - 1];							
								cell2[i][j] = (i - 1)+","+(j - 1)+","+0;
							}
							else if(k == 1) {
								cell1[i][j] = 1 + cell1[i][j - 1];
								cell2[i][j] = i+","+(j-1);
							}
							else {
								cell1[i][j] = 1 + cell1[i-1][j];
								cell2[i][j] = (i - 1)+","+j;
							}
						}
					}
				}
				else {
					costs[0] = sub;
					for(int k = 0; k < 3; k++) {
						if (costs[k] < min) {
							min = costs[k];
							if(k == 0) {
								cell1[i][j] = 1 + cell1[i - 1][j - 1];
								cell2[i][j] = (i - 1)+","+(j-1)+","+1;
							}
							else if(k == 1) {
								cell1[i][j] = 1 + cell1[i][j - 1];
								cell2[i][j] = i+","+(j-1);
							}
							else {
								cell1[i][j] = 1 + cell1[i-1][j];
								cell2[i][j] = (i - 1)+","+j;
							}
				}
			}	
		}		
	}
}
	System.out.println("minimal cost: " + cell1[n][m]);
	System.out.println("Sequence of minimal moves: ");
	int i, j;
	i = n; j = m;
	while(i != 0 || j != 0) {
		int k, l;
		String[] s = cell2[i][j].split(",");
		k = Integer.parseInt(s[0]); l = Integer.parseInt(s[1]);
		if(k < i && l < j) {
			if(Integer.parseInt(s[2]) == 0)
				System.out.println("Match");
			else
				System.out.println("Substitute");
		}
		else if (k == i)
			System.out.println("insert");
		else
			System.out.println("Delete");
		i = k; j = l;
		
	}
	}
}
