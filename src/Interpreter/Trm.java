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
public class Trm implements CoreSequence{

	private Integer val;
	private Op op;
	private Trm trm;
	private Tokenizer t;
	
	
	public Trm(){
		val = null;
		op = null; trm = null;
		t = Tokenizer.instance();
	}
	
	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#parse()
	 */
	@Override
	public void parse() {
		op = new Op();
		op.parse();
		val = op.getVal();
		
		//check for *
		if (t.getToken() == tokenID.MULTIPLY.ordinal()){
			t.skipToken();
			trm.parse();
			val *= trm.getVal();
		}
	}

	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#execute()
	 */
	@Override
	public void execute() {
		op.execute();
		val = op.getVal();
		if (trm != null){
			trm.execute();
			val *=trm.getVal();
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
		
		op.print(0);
		
		if (trm != null){
			System.out.print(" ");
			trm.print(0);
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
