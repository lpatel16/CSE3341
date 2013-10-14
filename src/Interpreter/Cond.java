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
public class Cond implements CoreSequence{

	private Boolean val;
	private String sign;
	private Comp comp;
	private Cond cond1;
	private Cond cond2;
	private Tokenizer t;
	
	
	public Cond(){
		val = null;		sign = null;
		comp = null;	cond1 = null;
		cond2 = null;
		t = Tokenizer.instance();
	}
	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#parse()
	 */
	@Override
	public void parse() {
		if (t.getToken() == tokenID.NOTEQUAL.ordinal()){
			//remove !
			t.skipToken();
			cond2.parse();
			
		} else if (t.getToken() == tokenID.LEFTBRACE.ordinal()){
			//remove brace
			t.skipToken();
			cond1 = new Cond();
			cond1.parse();
			
			if (t.getToken() == tokenID.OR.ordinal()){
				sign = "||";
				cond2 = new Cond();
				cond2.parse();
				
			} else if (t.getToken() == tokenID.AND.ordinal()){
				sign = "&&";
				cond2 = new Cond();
				cond2.parse();
				
			}

			
			//remove rightbrace
			if (t.getToken() != tokenID.RIGHTBRACE.ordinal()){
				throw new IllegalArgumentException("Error parsing program: COND:: Expected 'RIGHTBRACE' token."); 
			}
			t.skipToken();
		
		} else {
			comp = new Comp();
			comp.parse();
			val = comp.getVal();
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
		//comp = alt 1
		if (comp != null){
			comp.print(0);
		//cond 2 && !cond1 = alt2
		} else if (cond1 == null){
			System.out.print("!");
			cond2.print(0);
		//cond 1 = alt 3 || 4 
		} else {
			System.out.print("[");
			cond1.print(0);
			System.out.print(" " + sign + " ");
			cond2.print(0);
			System.out.print("]");
		}
	}


	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#execute()
	 */
	@Override
	public void execute() {
		if (comp != null){
			comp.execute();
			val = comp.getVal();
		} else if (cond1 == null){
			cond2.execute();
			val = !cond2.getVal();
		//cond 1 = alt 3 || 4 
		} else {
			cond1.execute();
			cond2.execute();
			if (sign.equals("||")){
				val = cond1.getVal() || cond2.getVal();	
			} else {		
				val = cond1.getVal() && cond2.getVal();
			}
		}
	}

	public Boolean getVal(){
		return val;
	}

}
