package Interpreter;

import java.util.ArrayList;

import Tokenizer.Tokenizer;
import Tokenizer.Tokenizer.tokenID;

/**
 * 
 */

/**
 * @author gibsonr
 *
 */
public class DecSeq implements CoreSequence {
	private ArrayList <Declaration> seq;
	private Tokenizer t;
	
	public DecSeq(){
		seq = new ArrayList<Declaration>();
		t = Tokenizer.instance();
	}
	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#parse()
	 */
	@Override
	public void parse(){
		Declaration dec;
		//check valid sequence
		if (t.getToken()!= tokenID.INT.ordinal()) {
			throw new IllegalArgumentException("Error parsing program: DECSEQ::Expected 'int' token.");
		}
		
		//get all declarations
		while (t.getToken()== tokenID.INT.ordinal()) {
			dec = new Declaration();
			dec.parse();
			seq.add(dec);
		}	
	}
	
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
		
		//print indent + declarations
		for (int i = 0; i < seq.size(); i++){
			System.out.print(indent);
			seq.get(i).print(0);
		}
		

	}
	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#execute()
	 */
	@Override
	public void execute(){
		
	}
}
