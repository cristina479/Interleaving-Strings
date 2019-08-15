package dp;

import java.io.PrintWriter;

/**
 * Class for the Interleaving String algorithm.
 * This class determines if two signals 'x' and 'y' are interleaved in a longer signal 's'.
 * 
 * To print trace runs, change PRINT_TRACE to true.
 * 
 * @author Cristina Padro-Juarbe
 *
 */
public class Interleave {
	private static final boolean PRINT_TRACE = true;			// Turn on/off traces for IO operations
	private boolean isInterleaved = true;						// Initialize default value for boolean
	private String xyPairs = "";								// String containing all possible pairs of sub-strings between X and Y such that |X|=|Y|= n
	private boolean table[][];									// Table that stores boolean value results from sub-problems when unraveling X and Y from s
	private String tableXY[][];									// Table that stores string values to reconstruct results from sub-problems when unraveling X and Y from s
	private String X;											// Repeats x until its length equals n 
	private String Y;											// Repeats y until its length equals n
	private long work = 0;										// Measures the work done by the algorithm

	/**
	 * The constructor of the Interleave class. It creates the xyPairs string from x and y if x and y have valid lengths.
	 * 
	 * @param s the signal we are listening to
	 * @param x the signal emitted from ship A
	 * @param y the signal emitted from ship B
	 * @param n the length of signal s
	 * @param pw the Print Writer object to print the string values to a file
	 */
	public Interleave(String s, String x, String y, int n, PrintWriter pw) {
		// Precondition of this problem: (x.length + y.length) <= n
		if(n - (x.length() + y.length()) >= 0) {	
			this.X = null;
			this.Y = null;

			// no work calculated for IO operations
			if (pw != null) {
				pw.println("=================================== INPUT ===================================\n");
				pw.println("x: " + x + "\tLength: " + x.length());
				pw.println("y: " + y + "\tLength: " + y.length());
				pw.println("s: " + s + "\tLength: " + n + "\r\n");
			} else {
				System.out.println("=================================== INPUT ===================================\n");
				System.out.println("x: " + x + "\tLength: " + x.length());
				System.out.println("y: " + y + "\tLength: " + y.length());
				System.out.println("s: " + s + "\tLength: " + n + "\n");
			}

			this.work++;		// work recorded for calling the createPairString() method
			createPairString(x, y, n, pw);
		}
		
		this.work++;			// work recorded for returning from the Interleave class constructor
	}

	/**
	 * This method constructs the value for string xyPairs, which is the string containing all possible pairs of sub-strings between X and Y such that |X|+|Y|= n.
	 * For example, if x = 101, y = 110, and n = 10:
	 * xyPairs = 101101101011011011011011011010  (Length: 3*n = 30)
	 *           |<-  X ->||<-  Y ->||<-  X ->|           
	 * 
	 * @param x the signal emitted from ship A
	 * @param y the signal emitted from ship B
	 * @param n the length of signal s
	 * @param pw the Print Writer object to print the string values to a file
	 */
	public void createPairString(String x, String y, int n, PrintWriter pw) {	
		// Construct the string value for xyPairs
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < n; j++) {
				if(i % 2 == 0) {
					this.xyPairs += x.charAt(j % x.length());
				} else {
					this.xyPairs += y.charAt(j % y.length());
				}
				this.work++;		// work recorded for every loop iteration in the inner loop
			}
			this.work++;			// work recorded for every loop iteration in the outer loop
		}
		this.work++;				// work recorded for breaking from the outer loop
		
		// no work calculated for IO operations
		if (pw != null) {
			pw.println("'XY' is a string constructed from 'x' and 'y' to obtain all possible pairs between 'x' and 'y' " + 
					"such that the sum of their lengths equals the length of 's'.");	
			pw.println("XY: " + this.xyPairs + "\r\n");
			pw.println("=================================== OUTPUT ===================================\r\n");
		} else {
			System.out.println("'XY' is a string constructed from 'x' and 'y' to obtain all possible pairs between 'x' and 'y' " + 
					"such that the sum of their lengths equals the length of 's'.");	
			System.out.println("XY: " + this.xyPairs + "\n");
			System.out.println("=================================== OUTPUT ===================================\n");			
		}
		
		this.work++;		// work recorded for returning from the createPairString() method
	}
	
	/**
	 * This method determines if signal s is an interleaving of X and Y by testing every possible combination between repetitions of x and y against string s.
	 * For example, if x = 101, y = 110, n = 10, i = 12, and xyPairs = 101101101011011011011011011010  (Length: 3*n = 30)
	 * Then, X = 10 and Y = 01101101, where |X|+|Y|= 2 + 8 = 10
	 * 
	 * Half-cycle: 1 when 0 <= i < n
	 * Half-cycle: 2 when n <= i < 2 * n
	 * 
	 * @param s the signal we are listening to
	 * @param x the signal emitted from ship A
	 * @param y the signal emitted from ship B
	 * @param n the length of signal s
	 * @param pw the Print Writer object to print the string values to a file
	 * @return boolean that determines if s is an interleaving of X and Y
	 */
	public boolean isInterleaved(String s, String x, String y, int n, PrintWriter pw) {
		this.isInterleaved = false;
		int i = 0;		
		int halfCycle = 0;
		
		// test all distinct pairs of X and Y against string s while s is not interleaved and i < 2*n
		while(!this.isInterleaved && i < (2 * n)) {				
			if(i % n == 0) {
				halfCycle++;
			}
			
			// no work calculated for IO operations
			if(PRINT_TRACE) {
				if (pw != null) {
					pw.println("Cycle: " + halfCycle + "/2\tIteration: " + i + "/" + (2 * n - 1));
				} else {
					System.out.println("Cycle: " + halfCycle + "/2\tIteration: " + i + "/" + (2 * n - 1));
				}
			}
			
			this.work++;							// work recorded for calling the findXY() method	
			// obtain values for X and Y
			findXY(s, i, halfCycle, n, pw);					
			this.work++;							// work recorded for calling the isPairInterleaved() method
			// determine if string s is an interleaving of X and Y
			this.isInterleaved = isPairInterleaved(s, n, pw);		
			i++;
			
			// no work calculated for IO operations
			if(PRINT_TRACE) {
				if (pw != null) {
					pw.println("\r\nIs 's' an interleaving of 'x' and 'y'? " + ((this.isInterleaved) ? "Yes" : "No"));
					pw.println("=================================\r\n");
				} else {
					System.out.println("\nIs 's' an interleaving of 'x' and 'y'? " + ((this.isInterleaved) ? "Yes" : "No"));
					System.out.println("=================================\n");
				}
			}
			
			this.work++;					// work recorded for every loop iteration
		}
		this.work++;						// work recorded for breaking from the loop
		this.work++;						// work recorded for returning from the isInterleaved() method
		
		return this.isInterleaved; 			// the boolean value that answers the question: Is s an interleaving of X and Y?
	}
	
	/**
	 * This method determines if string s is an interleaving of X and Y by constructing a table whose values in table[i][j] depend on previous values in table[][].
	 * A dynamic programming approach is used to obtain the answer, which is stored as a boolean value in table[i][j], where i + j = n. Table tableXY[][] stores 
	 * string values to reconstruct results from sub-problems when unraveling X and Y from s.
	 * 
	 * @param s the signal we are listening to
	 * @param n the length of signal s 
	 * @param pw the Print Writer object to print the string values to a file
	 * @return the value of table[i][j], where i + j = n
	 */
	public boolean isPairInterleaved(String s, int n, PrintWriter pw) {
		// Precondition of this problem: |X|+|Y|= n
		// Although we check for this precondition, this will never be true because of the way we construct xyPairs and the way we obtain values for X and Y in findXY().
		if(this.X.length() + this.Y.length() != n) {
			return false;
		} else {
			// initialize 2D arrays to store value results from sub-problems when unraveling X and Y from s
			this.table = new boolean[this.X.length() + 1][this.Y.length() + 1];
			this.tableXY = new String[this.X.length() + 1][this.Y.length() + 1];
		}
		
		// no work calculated for IO operations
		if(PRINT_TRACE) {
			if (pw != null) {
				pw.println("     Y ->");
				pw.println("     0");
			} else {
				System.out.println("     Y ->");
				System.out.println("     0");
			}
		}
		
		String separator = "X 0| ";
		
		// determine if string s is an interleaving of X and Y by constructing table[][] in a bottom-up fashion
        for(int i = 0; i <= this.X.length(); i++) {
            for(int j = 0; j <= this.Y.length(); j++) {
            	this.tableXY[i][j] = "_";
            	
                if(i == 0 && j == 0) {
                    this.table[i][j] = true;
                    this.tableXY[i][j] = "C";			//don't care
                } else if(i == 0) {
                	this.table[i][j] = (this.table[i][j - 1] && s.charAt(i + j - 1) == this.Y.charAt(j - 1));                	
                	this.tableXY[i][j] = (this.table[i][j - 1] && s.charAt(i + j - 1) == this.Y.charAt(j - 1)) ? "Y" : "_";
                } else if(j == 0) {
                	this.table[i][j] = (this.table[i - 1][j] && s.charAt(i + j - 1) == this.X.charAt(i - 1));
                	this.tableXY[i][j] = (this.table[i - 1][j] && s.charAt(i + j - 1) == this.X.charAt(i - 1) ? "X" : "_");
                } else {
                	this.table[i][j] = ((this.table[i - 1][j] && s.charAt(i + j - 1) == this.X.charAt(i - 1)) || 
                			(this.table[i][j - 1] && s.charAt(i + j - 1) == this.Y.charAt(j - 1)));
                	
                	if(this.table[i - 1][j] && s.charAt(i + j - 1) == this.X.charAt(i - 1)) {
                		this.tableXY[i][j] = "X";
                	}
                	
                	if(this.table[i][j - 1] && s.charAt(i + j - 1) == this.Y.charAt(j - 1)) {
                		if(this.tableXY[i][j].equals("X")) {
                			this.tableXY[i][j] = "C";   //don't care
                		} else {
                			this.tableXY[i][j] = "Y";
                		}                		
                	}
                }
                
                separator += (this.table[i][j] ? "T" : "F") + " ";
                this.work++;			// work recorded for every loop iteration in the inner loop
            }
            
            // no work calculated for IO operations
            if(PRINT_TRACE) {
				if (pw != null) {
					pw.println(separator + "|");
				} else {
					System.out.println(separator + "|");
				}
			}
            
            if(i == 1) {
            	separator = "|  | ";
            } else if(i == 2) {
                separator = "v  | ";
            } else {
            	separator = "   | ";
            }
            
            this.work++;					// work recorded for every loop iteration in the outer loop
        }
        this.work++;						// work recorded for breaking from the outer loop     
        this.work++;						// work recorded for returning from the isPairInterleaved() method
        
		return this.table[this.X.length()][this.Y.length()];		// the boolean value that answers the question: Is s an interleaving of X and Y?
	}
	
	/**
	 * Prints the results table (tableXY) when s is an interleaving of X and Y, and a string representation of tableXY.
	 * This method is considered an IO operation, thus no work will be recorded.
	 * 
	 * @param s the signal we are listening to
	 * @param n the length of signal s 
	 * @param pw the Print Writer object to print the string values to a file
	 */
	public void printResultTable(String s, int n, PrintWriter pw) {		
		// no work calculated for IO operations 
		if (pw != null) {
			pw.println("Result table. Draw a path from (" + this.X.length() + ", " + this.Y.length() + ") to (0,0) to separate s in X and Y.\r\n"
					+ "Then, staring from (0,0), separate s by taking into account all the characters in X and Y and their order.\r\nLetters 'C' in the result table are \"don't care\".\r\n");
			pw.println("X: " + this.X + "\tLength: " + this.X.length());
			pw.println("Y: " + this.Y + "\tLength: " + this.Y.length());
			pw.println("s: " + s + "\tLength: " + n + "\r\n");
			pw.println("     Y ->");
			pw.println("     0");
		} else {
			System.out.println("Result table. Draw a path from (" + this.X.length() + ", " + this.Y.length() + ") to (0,0) to separate s in X and Y.\n"
					+ "Then, staring from (0,0), separate s by taking into account all the characters in X and Y and their order.\r\nLetters 'C' in the result table are \"don't care\".\n");
			System.out.println("X: " + this.X + "\tLength: " + this.X.length());
			System.out.println("Y: " + this.Y + "\tLength: " + this.Y.length());
			System.out.println("s: " + s + "\tLength: " + n + "\n");
			System.out.println("     Y ->");
			System.out.println("     0");
		}

		String separator = "X 0| ";

		// construct the results table
		for(int i = 0; i <= this.X.length(); i++) {
			for(int j = 0; j <= this.Y.length(); j++) {            	
				separator += this.tableXY[i][j] + " ";        		
			}

			// no work calculated for IO operations
			if (pw != null) {
				pw.println(separator + "|");
			} else {
				System.out.println(separator + "|");
			}

			if(i == 1) {
				separator = "|  | ";
			} else if(i == 2) {
				separator = "v  | ";
			} else {
				separator = "   | ";
			}   		
		}

		boolean adjustLine = false;
		String line = "";

		// no work calculated for IO operations
		if (pw != null) {
			pw.println("\r\ns: " + s + "\tLength: " + n);
		} else {
			System.out.println("\r\ns: " + s + "\tLength: " + n);
		}

		// construct the string representation of the results table
		for(int i = 0; i <= this.X.length(); i++) {			
			line += "  ";			

			for(int j = 0; j <= this.Y.length(); j++) {   
				for(int m = 0; m < i; m++) {
					if(j == 0) {
						line += " ";
					}					
				}

				if(this.tableXY[i][j].equals("C") || this.tableXY[i][j].equals("X") || this.tableXY[i][j].equals("Y")) {
					if(!(i == 0 && j == 0)) {
						if(i == 0 && j == 1) {
							line += " ";
							adjustLine = true;
						}

						line += this.tableXY[i][j];
					}		
				} else if(this.tableXY[i][j].equals("_")) {
					line += " ";
				}	

				if(j == this.Y.length()) {   
					line += getSpaceString(n, j, i, adjustLine) + "|";
					adjustLine = false;
				} 
			}

			// no work calculated for IO operations
			if (pw != null) {
				pw.println(line);
			} else {
				System.out.println(line);
			}

			line = "";
		}

		// no work calculated for IO operations
		if (pw != null) {
			pw.println("\r\n=================================\r\n");
		} else {
			System.out.println("\n=================================\n");
		}
	}
	
	/**
	 * Prints a string with extra spaces and a delimiter '|' at the end for better visualization of the results table.
	 * This is a helper method of printResultTable(), thus no work will be recorded for this method.
	 * 
	 * @param n the length of signal s 
	 * @param j inner loop variable for Y
	 * @param i outer loop variable for X
	 * @param adjust boolean value to determine if an extra space before the delimiter '|' needs to be included in the return string
	 * @return a string with n - (i + j) spaces with the delimiter '|' concatenated at the end
	 */
	private String getSpaceString(int n, int j, int i, boolean adjust) {
		String space = "";
		for(int m = (j + i); m < n; m++) {
			space += " ";
		}
		
		if(!adjust && i == 0) {
			space += " ";
		}
		
		return space;
	}

	/**
	 * This method parses the xyPairs string to obtain new values of X and Y for the current iteration i
	 * For example, if x = 101, y = 110, n = 10, i = 12, and xyPairs = 101101101011011011011011011010  (Length: 3*n = 30)
	 *                                                                 |<-  X ->||<-  Y ->||<-  X ->|     
	 * Half-cycle: 1 when 0 <= i < n                                   |<-  half-cycle  ->|   
	 * Half-cycle: 2 when n <= i < 2 * n                                         |<-  half-cycle ->|    
	 * Then, X = 10 and Y = 01101101, where |X|+|Y|= 2 + 8 = 10
	 * 
	 * @param s the signal we are listening to
	 * @param i the current iteration
	 * @param halfCycle the counter to determine the current half-cycle done by i
	 * @param n the length of signal s
	 * @param pw the Print Writer object to print the string values to a file
	 */
	public void findXY(String s, int i, int halfCycle, int n, PrintWriter pw) {	
		// when 0 <= i < n
		if(halfCycle == 1) {
			this.X = this.xyPairs.substring(i, n);
			this.Y = this.xyPairs.substring(n, n + i);		
		} else {
			// n <= i < 2 * n
			this.X = this.xyPairs.substring(n * halfCycle, n + i);  
			this.Y = this.xyPairs.substring(i, n * halfCycle);   
		}
		
		// no work calculated for IO operations 
		if(PRINT_TRACE) {
			if (pw != null) {
				pw.println("X: " + this.X + "\tLength: " + this.X.length());
				pw.println("Y: " + this.Y + "\tLength: " + this.Y.length());
				pw.println("s: " + s + "\tLength: " + n + "\r\n");
			} else {
				System.out.println("X: " + this.X + "\tLength: " + this.X.length());
				System.out.println("Y: " + this.Y + "\tLength: " + this.Y.length());
				System.out.println("s: " + s + "\tLength: " + n + "\n");
			}
		}
		
		this.work++;			// work recorded for returning from the findXY() method
	}
	
	/**
	 * Work done by the Interleave class.
	 * 
	 * @return work done by the Interleave class
	 */
	public long getWork() {
		return ++this.work;		// work recorded for returning from the getWork() method
	}

}
