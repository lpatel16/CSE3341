/**
 * 
 */
package Interpreter;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author gibsonr
 *
 */
public class Interpreter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File inFile = new File (args[0]);
		File input = new File (args[1]);
		Program prog;
		try {
			prog = new Program(inFile, input);
			prog.parse();
			prog.print(0);
			prog.execute();
		} catch (FileNotFoundException e) {
			System.out.println("File not found: e = " + e.getMessage());
		}
	}

}
