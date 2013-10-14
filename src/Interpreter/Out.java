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
public class Out implements CoreSequence {

	private IDList idList;
	private Tokenizer t;
	
	
	public Out(){
		idList = null;
		t = Tokenizer.instance();
	}
	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#parse()
	 */
	@Override
	public void parse() {
		//remove read
		if (t.getToken() != tokenID.WRITE.ordinal()){
			throw new IllegalArgumentException("Error parsing program: OUT:: Expecting 'write' token.");
		}
		t.skipToken();
		
		//parse ids
		idList = new IDList();
		idList.parseForIO();
		
		//remove ;
		if (t.getToken() != tokenID.SEMICOLON.ordinal()){
			throw new IllegalArgumentException("Error parsing program: OUT:: Expecting 'semicolon' token.");
		}
		t.skipToken();
		
	}

	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#execute()
	 */
	@Override
	public void execute() {
		idList.executeWrite();
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
		System.out.print(indent);
	
		System.out.print("write ");
		idList.print(0);
		System.out.println(";");
	}

}
