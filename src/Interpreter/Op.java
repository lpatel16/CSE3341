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
public class Op implements CoreSequence{

	private Integer val;
	private ID id;
	private Exp exp;
	private Tokenizer t;
	
	
	public Op(){
		val = null;
		id = null; 	exp = null;
		t = Tokenizer.instance();
	}
	
	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#parse()
	 */
	@Override
	public void parse() {
		if (t.getToken() == tokenID.INTEGER.ordinal()){
			val = t.getIntVal();
			t.skipToken();
		} else if (t.getToken() == tokenID.LEFTPAR.ordinal()){
			//remove left parenthesis
			t.skipToken();
			exp = new Exp();
			exp.parse();
			
			//remove right parenthesis
			if (t.getToken() != tokenID.RIGHTPAR.ordinal()){
				throw new IllegalArgumentException("Error parsing program: OP:: Expected 'RIGHTPAR' token."); 
			}
			t.skipToken();
		} else if (t.getToken() == tokenID.IDENTIFIER.ordinal()){
			id = ID.parseForStmtSeq();
			val = id.getVal();
		} else {
			throw new IllegalArgumentException("Error parsing program: OP:: Expected 'INTEGER', 'LEFTPAR', or 'IDENTIFIER' token."); 
		}
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
		
		if (id != null) {
			id.print();
		} else if (exp != null) {
			System.out.print("(");
			exp.print(0);
			System.out.print(")");
		} else {
			System.out.print(val);
		}
		
	}

	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#execute()
	 */
	@Override
	public void execute() {
		if (id != null){
			val = id.getVal();
		} else if (exp != null){
			exp.execute();
			val = exp.getVal();
		}
	}

	/**
	 * @return the val
	 */
	public Integer getVal() {
		return val;
	}

	/**
	 * @param val the val to set
	 */
	public void setVal(Integer val) {
		this.val = val;
	}

}
