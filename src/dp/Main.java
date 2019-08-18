package dp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Main class for the Interleaving Strings algorithm.
 * By default, the results are printed to a file. To print results to the terminal change traceRunToConsole to true. 
 * 
 * @author Cristina Padro-Juarbe
 *
 */
public class Main {
	final static boolean traceRunToConsole = false;
	final static String filepath = "./dp/output/";
	final static String filepathTests = "./dp/InputTests.txt";
	static PrintWriter printWriter;
	static int workMain = 0;
	
	public static void main(String[] args) {
		System.out.println("This program decides if signal 's' is an interleaving of two string sequences 'x' and 'y'.\n");
		
		int counter = 0;
		int tests = 0;
		String[] xArray = null;
		String[] yArray = null;
		String[] sArray = null;
		
		// no work is recorder when reading tests from input file
		Scanner in = null;
		try {
			in = new Scanner(new File(filepathTests));
			
			while(in.hasNextLine()) {
				String[] line = new String[2]; 
				String nl = in.nextLine();
				
				if(!nl.isEmpty() || !nl.isBlank()) {
					line = nl.split(":");
				
					if(line[0].equals("test")) {
						tests = Integer.parseInt(line[1].trim());
						xArray = new String[tests];
						yArray = new String[tests];
						sArray = new String[tests];	
					} else if(line[0].equals("x")) {
						xArray[counter] = line[1].trim();
					} else if(line[0].equals("y")) {
						yArray[counter] = line[1].trim();
					} else if(line[0].equals("s")) {
						sArray[counter++] = line[1].trim();
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				in.close();
		}
		
		int i = 0;
		
		// work is recorded for individual tests only
		while(i < tests) {
			String x = xArray[i];
			String y = yArray[i];
			String s = sArray[i];
			int n = s.length();
			
			workMain++;			//work recorded for calling the run() method
			run(s, x, y, n, i);
			workMain = 0;		// reset work done for each test
			i++;
		}
		
		System.out.println("\nAll test results are stored in " + filepath);
	}

	/**
	 * Helper method to read the tests from a file, where each unique test determines if s is an interleaving of x and y.
	 * 
	 * @param s the signal we are listening to
	 * @param x the signal emitted from ship A
	 * @param y the signal emitted from ship B
	 * @param n the length of signal s
	 */
	private static void run(String s, String x, String y, int n, int test) {
		Interleave mytest = null;
		
		// print output to a file
		if (!traceRunToConsole) {
			try {
				printWriter = new PrintWriter(new FileWriter(filepath + "test=" + (test + 1) + " n=" + n + ".txt"));
				
				workMain++;		// work recorded for calling the Interleave constructor class
				mytest = new Interleave(s, x, y, n, printWriter);
				 
				workMain++;		// work recorded for calling the isInterleaved() method
				boolean isInterleaved = mytest.isInterleaved(s, x, y, n, printWriter);
				
				// print result table if s is interleaved by X and Y
				// no work is recorded for IO operations
				if(isInterleaved) {
					mytest.printResultTable(s, n, printWriter);
				}
				
				workMain++;			// work recorded for calling the getWork() method
				workMain++;			// work recorded for returning from the run() method
				printWriter.println("Total work done: " + (workMain + mytest.getWork()) + "\n");			
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (printWriter != null)
					printWriter.close();
			}
		} else {
			// print output to the terminal
			
			workMain++;		// work recorded for calling the Interleave constructor class
			mytest = new Interleave(s, x, y, n, null);
			
			workMain++;		// work recorded for calling the isInterleaved() method
			boolean isInterleaved = mytest.isInterleaved(s, x, y, n, null);
			
			// print result table if s is interleaved by X and Y
			// no work is recorded for IO operations
			if(isInterleaved) {
				mytest.printResultTable(s, n, null);
			}
			
			workMain++;			// work recorded for calling the getWork() method
			workMain++;			// work recorded for returning from the run() method
			System.out.println("Total work done: " + (workMain + mytest.getWork()) + "\n");
		}	
		
		System.out.println("Test " + (test + 1) + " done!");		
	}
	
}
