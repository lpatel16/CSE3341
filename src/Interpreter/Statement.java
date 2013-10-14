package Interpreter;

import Tokenizer.Tokenizer;
import Tokenizer.Tokenizer.tokenID;

/**
 * 
 */

/**
 * @author gibsonr
 *
 */
public class Statement implements CoreSequence {
	
	private Tokenizer t;
	private CoreSequence stmt;
	
	public Statement (){
		t = Tokenizer.instance();
		stmt = null;
	}

	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#parse()
	 */
	@Override
	public void parse(){
		Integer token = t.getToken();
		if (token == tokenID.IDENTIFIER.ordinal()){
			stmt = new Assign();
			stmt.parse();
		} else if (token == tokenID.IF.ordinal()) {
			stmt = new If();
			stmt.parse();
		} else if (token == tokenID.READ.ordinal()) {
			stmt = new In();
			stmt.parse();
		} else if (token == tokenID.WRITE.ordinal()) {
			stmt = new Out();
			stmt.parse();
		} else if (token == tokenID.WHILE.ordinal()) {
			stmt = new Loop();
			stmt.parse();
		} else {
			throw new IllegalArgumentException("Error parsing program: Expected 'stmt' token.");
		}				
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#print(java.lang.Integer)
	 */
	@Override
	public void print(Integer indents) {
		stmt.print(indents);		
	}
	
	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#execute()
	 */
	@Override
	public void execute(){
		stmt.execute();
	}
}
