package dp;

import java.util.Scanner;

/**
 * 
 * @author crist
 *
 */
public class Main {
	final static int MAX_LENGTH_ARRAY = 30;
	final static String filepath = "src/quicksort/QuickSort";
	static PrintWriter printWriter = null;
	

	public static void main(String[] args) {
		System.out.println("This program decides if signal 's' is an interleaving of two string sequences 'x' and 'y'.\n");
		
		Scanner in = new Scanner(System.in);
		System.out.println("Enter sequence x: ");
		String x = in.next();
		System.out.println();
		
		System.out.println("Enter sequence y: ");
		String y = in.next();
		System.out.println();
		
		System.out.println("Enter signal s: ");
		String s = in.next();	
		System.out.println();
		int n = s.length();		
		
		while(n - (x.length() + y.length()) < 0) {
			System.out.println("The sum of the lengths of 'x' and 'y' cannot be longer than the length of 's' (length: " + n + ").");
			System.out.println("Enter sequence x: ");
			x = in.next();
			System.out.println();
			
			System.out.println("Enter sequence y: ");
			y = in.next();
			System.out.println();
		}
		
		in.close();
		
		boolean trace_run_console = size <= MAX_LENGTH_ARRAY ? true : false;
		
		Interleave obj = new Interleave(s, x, y, n);
		obj.isInterleaved(s, x, y, n);	
		System.out.println("Done!");
	}

}
