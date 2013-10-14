/**
 * 
 */
package Interpreter;

import Tokenizer.Tokenizer;
import Tokenizer.Tokenizer.tokenID;;

/**
 * @author gibsonr
 *
 */
public class If implements CoreSequence{
	
	private Cond cond;
	private StmtSeq ifStmts;
	private StmtSeq elseStmts;
	private Tokenizer t;
	
	public If(){
		cond = null;
		ifStmts = null; elseStmts = null;
		t = Tokenizer.instance();
	}
	
	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#parse()
	 */
	public void parse(){
		//remove if
		if (t.getToken() != tokenID.IF.ordinal()){
			throw new IllegalArgumentException("Error parsing program: IF:: Expecting 'if' token.");
		}
		t.skipToken();
		
		cond = new Cond();
		cond.parse();
		
		//remove then
		if (t.getToken() != tokenID.THEN.ordinal()){
			throw new IllegalArgumentException("Error parsing program: IF:: Expecting 'then' token.");
		}
		t.skipToken();
		
		ifStmts = new StmtSeq();
		ifStmts.parse();
	
		if (t.getToken() == tokenID.ELSE.ordinal()){
			//remove ELSE
			t.skipToken();
			elseStmts = new StmtSeq();
			elseStmts.parse();
			}
		
		//remove end
		if (t.getToken() != tokenID.END.ordinal()){
			throw new IllegalArgumentException("Error parsing program: IF:: Expecting 'end' token.");
		}
		t.skipToken();
		
		//remove semicolon
		if (t.getToken() != tokenID.SEMICOLON.ordinal()){
			throw new IllegalArgumentException("Error parsing program: IF:: Expecting 'semicolon' token.");
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
		
		//print if portion
		System.out.print("if ");
		cond.print(0);
		System.out.println(" then");
		indents++;
		ifStmts.print(indents);
		indents--;
		
		//print else
		if (elseStmts!= null){
			System.out.println(indent+"else");
			indents++;
			elseStmts.print(indents);
			indents--;
		}
		//close off if
		System.out.println(indent+"end;");
	}

	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#execute()
	 */
	@Override
	public void execute(){
		cond.execute();
		if (cond.getVal()){
			ifStmts.execute();
		} else if (elseStmts != null){
			elseStmts.execute();	
		}
	}
}
