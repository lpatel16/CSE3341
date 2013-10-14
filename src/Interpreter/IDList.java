/**
 * 
 */
package Interpreter;

import java.util.ArrayList;
import Tokenizer.Tokenizer;
import Tokenizer.Tokenizer.tokenID;

/**
 * @author gibsonr
 *
 */
public class IDList {

	private ArrayList<ID> ids;
	private Integer count;
	private Tokenizer t; 
	
	
	public IDList (){
		count = 0;
		ids = new ArrayList<ID>();
		
		t = Tokenizer.instance();
	}
	
	public Integer getCount(){
		return count;
	}
	
	
	public void parseForDec() {
		
		//check valid sequence
		if (t.getToken()!= tokenID.IDENTIFIER.ordinal()){
			throw new IllegalArgumentException("Error parsing program: Expected 'IDENTIFIER' token.");
		}
		while (t.getToken() == tokenID.IDENTIFIER.ordinal()){
			//parse current token
			ids.add(ID.parseForDecSeq());
			count++;
			//check for ','
			if (t.getToken() == tokenID.COMMA.ordinal()){
				t.skipToken();
			}
		}
	}
	
	public void parseForIO() {
		
		//check valid sequence
		if (t.getToken()!= tokenID.IDENTIFIER.ordinal()){
			throw new IllegalArgumentException("Error parsing program: Expected 'IDENTIFIER' token.");
		}
		while (t.getToken() == tokenID.IDENTIFIER.ordinal()){
			//parse current token
			ids.add(ID.parseForStmtSeq());
			
			//check for ','
			if (t.getToken() == tokenID.COMMA.ordinal()){
				t.skipToken();
			}
		}
	}

	
	
	public void executeRead() {
		for (int i = 0; i < ids.size(); i++){
			ids.get(i).setVal(Program.getNextInput());
		}
		
	}
	
	public void executeWrite() {
		for (int i = 0; i < ids.size(); i++) {
			ids.get(i).print();
			System.out.println(" = " + ids.get(i).getVal());
		}
	}

	private String setIndents(Integer indents){
		String indent = "";
		for (int i = 0; i < indents; ++i){
			indent += "\t";
		}
		return indent;
	}
	

	public void print(Integer indents) {
		String indent = setIndents(indents);
		int i = 0;
		//align to indent
		System.out.print(indent);
		
		//print first id
		ids.get(i).print();
		i++;
		
		//add comma if more exist then next id
		while( i < ids.size()){
			System.out.print(", ");
			ids.get(i).print();
			i++;
		}
		
	}

}
