/**
 * 
 */
package dp;

/**
 * @author Cristina Padro-Juarbe
 *
 */
public class Interleave {
	private boolean isInterleaved = true;
	private String xyPairs = "";
	private boolean table[][];
	private String X;
	private String Y;
	
	public Interleave(String s, String x, String y, int n) {
		if(n - (x.length() + y.length()) > 0) {	
			this.X = null;
			this.Y = null;

			
			System.out.println("s: " + s + "\tLength: " + s.length());
			System.out.println("x: " + x + "\tLength: " + x.length());
			System.out.println("y: " + y + "\tLength: " + y.length());
			System.out.println();

			createPairString(x, y, n);
		}
	}

	public void createPairString(String x, String y, int n) {	
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < n; j++) {
				if(i % 2 == 0) {
					this.xyPairs += x.charAt(j % x.length());
				} else {
					this.xyPairs += y.charAt(j % y.length());
				}
			}
		}
		
		System.out.println("XY: " + this.xyPairs + "\n");
	}
	
	public boolean isInterleaved(String s, String x, String y, int n) {
		this.isInterleaved = false;
		int i = 0;		
		int cycle = 0;
		
		while(!this.isInterleaved && (n + i + 1) <= this.xyPairs.length()) {				
			if(i % n == 0) {
				cycle++;
			}
			
			System.out.println("Cycle: " + cycle + "\tIteration: " + i);
			
			findXY(s, i, cycle, n);
			this.isInterleaved = isPairInterleaved(s, n);
			i++;
			
			System.out.println("\nIs 's' an interleaving of 'x' and 'y'? " + ((this.isInterleaved) ? "Yes" : "False"));
			System.out.println("-------------\n");
		}
		
		return this.isInterleaved;
	}
	
	public boolean isPairInterleaved(String s, int n) {
		if(this.X.length() + this.Y.length() != n) {
			return false;
		} else {
			this.table = new boolean[this.X.length() + 1][this.Y.length() + 1];
		}
		
		String separator = "| ";
		
        for(int i = 0; i <= this.X.length(); i++) {
            for(int j = 0; j <= this.Y.length(); j++) {
            	
                if(i == 0 && j == 0) {
                    this.table[i][j] = true;
                } else if(i == 0) {
                	this.table[i][j] = (this.table[i][j - 1] && s.charAt(i + j - 1) == this.Y.charAt(j - 1));
                } else if(j == 0) {
                	this.table[i][j] = (this.table[i - 1][j] && s.charAt(i + j - 1) == this.X.charAt(i - 1));
                } else{
                	this.table[i][j] = ((this.table[i - 1][j] && s.charAt(i + j - 1) == this.X.charAt(i - 1)) || 
                			(this.table[i][j - 1] && s.charAt(i + j - 1) == this.Y.charAt(j - 1)));
                }
                
                separator += (this.table[i][j] ? "T" : "F") + " ";
            }
            
            System.out.println(separator + "|");
            separator = "| ";
        }
        
		return this.table[this.X.length()][this.Y.length()];
	}
	
	public void findXY(String s, int i, int cycle, int n) {		
		if(cycle == 1) {
			this.X = this.xyPairs.substring(i, n);
			this.Y = this.xyPairs.substring(n, n + i);
				
			System.out.println("X: " + this.X + "\tLength: " + this.X.length());
			System.out.println("Y: " + this.Y + "\tLength: " + this.Y.length());	
			System.out.println("s: " + s + "\tLength: " + s.length());
			
//			System.out.println("X (" + i + ", " + n + "): " + this.X + "\tLength: " + this.X.length());
//			System.out.println("Y (" + n + ", " + (n + i) + "): " + this.Y + "\tLength: " + this.Y.length());		
		} else {
			this.X = this.xyPairs.substring(n * cycle, n + i);  
			this.Y = this.xyPairs.substring(i, n * cycle);   
				
			System.out.println("X: " + this.X + "\tLength: " + this.X.length());
			System.out.println("Y: " + this.Y + "\tLength: " + this.Y.length());
			System.out.println("s: " + s + "\tLength: " + s.length());
			
//			System.out.println("X (" + (n * cycle) + ", " + (n + i) + "): " + this.X + "\tLength: " + this.X.length());
//			System.out.println("Y (" + i + ", " + (n * cycle) + "): " + this.Y + "\tLength: " + this.Y.length());	
		}
		
		System.out.println();
	}
	
	public String getXYPairs() {
		return this.xyPairs;
	}
	
	public String getX() {
		return this.X;
	}
	
	public String getY() {
		return this.Y;
	}
	
}
