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
public class Declaration implements CoreSequence{
	private IDList idList;
	private Tokenizer t;
	
	public Declaration(){
		idList = new IDList();
		t = Tokenizer.instance();
	}
	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#parse()
	 */
	@Override
	public void parse() throws IllegalArgumentException{
		//check valid sequence
		if (t.getToken()!= tokenID.INT.ordinal()){
			throw new IllegalArgumentException("Error parsing program: Expected 'int' token.");
		}
		//skip over 'int'
		t.skipToken();
		
		//parse ids
		idList.parseForDec();
		
		//check for semicolon
		if (t.getToken()!= tokenID.SEMICOLON.ordinal()) {
			throw new IllegalArgumentException("Error parsing program: DECLARATION:: Expected 'SEMICOLON' token.");
		}
		//get next token
		t.skipToken();
	};
	
	private String setIndents(Integer indents){
		String indent = "";
		for (int i = 0; i < indents; ++i){
			indent += "\t";
		}
		return indent;
	}
	
	
	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#print()
	 */
	@Override
	public void print(Integer indents){
		String indent = setIndents(indents);
		
		//print int  
		System.out.print(indent + "int ");
		idList.print(0);
		//print semicolon
		System.out.print(";");
	}
	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#execute()
	 */
	@Override
	public void execute(){};
}
