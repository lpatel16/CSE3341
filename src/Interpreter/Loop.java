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
public class Loop implements CoreSequence{
	
	private Cond cond;
	private StmtSeq stmts;
	private Tokenizer t;
	
	
	public Loop(){
		cond = null; stmts = null;
		t = Tokenizer.instance();
	}
	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#parse()
	 */
	@Override
	public void parse(){
		//check for WHILE token
		if (t.getToken()!= tokenID.WHILE.ordinal()){
			throw new IllegalArgumentException("Error parsing program: LOOP:: Expected 'WHILE' token.");
		}
		t.skipToken();
		
		//parse condition
		cond = new Cond();
		cond.parse();
	
		//remove for LOOP token
		if (t.getToken()!= tokenID.LOOP.ordinal()){
			throw new IllegalArgumentException("Error parsing program: LOOP:: Expected 'LOOP' token.");
		}
		t.skipToken();
		
		stmts = new StmtSeq();
		stmts.parse();
		
		//remove for END token
		if (t.getToken()!= tokenID.END.ordinal()){
			throw new IllegalArgumentException("Error parsing program: LOOP:: Expected 'END' token.");
		}
		t.skipToken();
		
		//remove for SEMICOLON token
		if (t.getToken()!= tokenID.SEMICOLON.ordinal()){
			throw new IllegalArgumentException("Error parsing program: LOOP:: Expected 'SEMICOLON' token.");
		}
		t.skipToken();
		
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
		
		System.out.print("while ");
		cond.print(0);
		System.out.println("loop");

		indents++;
		stmts.print(indents);
		indents--;

		System.out.println(indent + "end;");
	}
	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#execute()
	 */
	@Override
	public void execute(){
		cond.execute();
		while (cond.getVal()){
			stmts.execute();
			cond.execute();
		}
	}
}
