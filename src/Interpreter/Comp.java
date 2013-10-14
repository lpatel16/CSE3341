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
public class Comp implements CoreSequence{
	
	private Boolean val;
	private Op op1;
	private Op op2;
	private String sign;
	private Tokenizer t;
	
	public Comp(){
		op1 = null;		op2 = null;
		sign = null;	val = null;
		t = Tokenizer.instance();
	}
	
	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#parse()
	 */
	@Override
	public void parse() {

		if (t.getToken() == tokenID.LEFTPAR.ordinal()){
			//remove left parenthesis
			t.skipToken();
			op1 = new Op();
			op1.parse();
	
			if (t.getToken() == tokenID.EQUAL.ordinal()){
				sign = "==";
				t.skipToken();
				op2 = new Op();
				op2.parse();
			} else if (t.getToken() == tokenID.NOTEQUAL.ordinal()){
				sign ="!=";
				t.skipToken();
				op2 = new Op();
				op2.parse();
			} else if (t.getToken() == tokenID.LESSTHAN.ordinal()){
				sign = "<";
				t.skipToken();
				op2 = new Op();
				op2.parse();
			} else if (t.getToken() == tokenID.LESSTHANOREQUALTO.ordinal()){
				sign = "<=";
				t.skipToken();
				op2 = new Op();
				op2.parse();
			} else if (t.getToken() == tokenID.GREATERTHAN.ordinal()){
				sign = ">";
				t.skipToken();
				op2 = new Op();
				op2.parse();
			} else if (t.getToken() == tokenID.GREATERTHANOREQUALTO.ordinal()){
				sign = ">=";
				t.skipToken();
				op2 = new Op();
				op2.parse();
			} else {
				throw new IllegalArgumentException("Error parsing program: OP:: Expected '==','!=','<','<=','>', or '>=' token."); 
			}
			//remove right parenthesis
			if (t.getToken() != tokenID.RIGHTPAR.ordinal()){
				throw new IllegalArgumentException("Error parsing program: OP:: Expected 'RIGHTPAR' token."); 
			}
			t.skipToken();

		} else {
			throw new IllegalArgumentException("Error parsing program: COMP:: Expected 'LEFTPAR' token."); 	
		}

	}
	
	public Boolean getVal(){
		return val;
	}

	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#execute()
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		op1.execute();
		op2.execute();
		if (sign.equals("==")){
			val = op1.getVal() == op2.getVal();
		} else if (sign.equals("!=")){
			val = op1.getVal() != op2.getVal();
		} else if (sign.equals("<")){
			val = op1.getVal() < op2.getVal();
		} else if (sign.equals("<=")){
			val = op1.getVal() <= op2.getVal();
		} else if (sign.equals(">")){
			val = op1.getVal() > op2.getVal();
		} else if (sign.equals(">=")){
			val = op1.getVal() >= op2.getVal();
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
	
		System.out.print("(");
		op1.print(0);
		System.out.print(" " + sign + " ");
		op2.print(0);
		System.out.print(")");
		
	}


}
