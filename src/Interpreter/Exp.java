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
public class Exp implements CoreSequence {

	private Integer val;
	private Trm trm;
	private Exp exp;
	private String sign;
	private Tokenizer t; 
	
	
	public Exp(){
		val = null; sign = null;
		trm = null; exp = null;
		t = Tokenizer.instance();
	}
	
	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#parse()
	 */
	@Override
	public void parse() {
		//parse term
		trm = new Trm();
		trm.parse();
		val = trm.getVal();
		
		//check for + or - op
		if (t.getToken() == tokenID.ADD.ordinal()){
			sign = "+";
			t.skipToken();
			exp = new Exp();
			exp.parse();
			
		} else if (t.getToken() == tokenID.SUBTRACT.ordinal()){
			sign = "-";
			t.skipToken();
			exp = new Exp();
			exp.parse();
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
		
		trm.print(0);
		
		if (sign != null){
			System.out.print(" " + sign + " ");
			exp.print(0);
		}		
	}

	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#execute()
	 */
	@Override
	public void execute() {
		trm.execute();
		val = trm.getVal();
		if (sign != null){
			if (sign.equals("+")) {
				exp.execute();
				val += exp.getVal();
			} else if (sign.equals("-")) {
				exp.execute();
				val -= exp.getVal();
			}
		}
	}
	
	public Integer getVal(){
		return val;
	}

}
