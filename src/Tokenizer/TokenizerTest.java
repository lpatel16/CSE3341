package Tokenizer;
import java.io.File;


/**
 * @author gibsonr
 * 
 * Takes a file and outputs the Core Tokens contained in the file.
 *
 */
public class TokenizerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File file = new File (args[0]);
		Tokenizer tokenizer;
		Integer token = 0;
		if (file.exists()) {
			Tokenizer.connectToInputStream(file);
			tokenizer = Tokenizer.instance();
			//get all tokens until EOF
			while ((token = tokenizer.getToken()) != 33) {
				if (token == 31) { 		// token is an Integer
					System.out.println(token + " : value of " + tokenizer.getIntVal());	
				} else if (token == 32) { 	// token is an Identifier
					System.out.println(token + " : identified by " + tokenizer.idName());	
				} else {
					System.out.println(token);
				}
				tokenizer.skipToken();
			}
			//print EOF token
			System.out.println(token);
		} else {
			System.out.println("Error: file does not exist");
		}
	}
}
