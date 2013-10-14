/**
 * 
 */
package Interpreter;

import Tokenizer.Tokenizer;
import Tokenizer.Tokenizer.tokenID;

/**
 * @author gibsonr
 *
 */
public class Assign implements CoreSequence{
	private Tokenizer t; 
	private ID id;
	private Exp exp;
	
	
	public Assign(){
		t = Tokenizer.instance();
		id = null; exp = null; 
	}
	
	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#parse()
	 */
	@Override
	public void parse() throws IllegalArgumentException {
		//check to make sure IDENTIFIER was sent as current token
		if (t.getToken()!= tokenID.IDENTIFIER.ordinal()){
			throw new IllegalArgumentException("Error parsing program: ASSIGN:: Expected 'IDENTIFIER' token.");
		}
		//parse id the assignment sequence
		id = ID.parseForStmtSeq();
		
		//remove '=' sign
		if (t.getToken()!= tokenID.ASSIGN.ordinal()){
			throw new IllegalArgumentException("Error parsing program: ASSIGN:: Expected 'ASSIGN' token.");
		}
		t.skipToken();
		
		//parse the expression
		exp = new Exp();
		exp.parse();
		
		//remove semicolon
		if (t.getToken()!= tokenID.SEMICOLON.ordinal()){
			throw new IllegalArgumentException("Error parsing program: ASSIGN:: Expected 'semicolon' token.");
		}
		t.skipToken();
		
		//assign value of exp to the ID
		id.initialize();
	}
	
	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#execute()
	 */
	@Override
	public void execute(){
		exp.execute();
		id.setVal(exp.getVal());
	}
	
	private String setIndents(Integer indents){
		String indent = new String("");
		for (int i = 0; i < indents; ++i){
			indent += "\t";
		}
		return indent;
	}

	
	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#print(java.lang.Integer)
	 */
	@Override
	public void print(Integer indents) {
		String indent = setIndents(indents);
		
		//print id = expression;
		System.out.print(indent);
		id.print();
		System.out.print(" = ");
		exp.print(0);
		System.out.println(";");
	}
}
