/**
 * 
 */
package Interpreter;

import java.util.ArrayList;

import Tokenizer.Tokenizer;

/**
 * @author gibsonr
 *
 */
public class ID {
	private String name;
	private Integer val;
	private Boolean initialized;
	private static Tokenizer t = Tokenizer.instance();
	private static ArrayList<ID> eIds = new ArrayList<ID>(20);
	private static int idCount = 0;
	
	
	
	private ID (String n) {
		name = n;
		initialized = false;
	}

	/**
	 * @param s Name of ID to check against
	 * @return	If unique then
	 * 				return true
	 * 			else 
	 * 				return false
	 */
	static private Boolean isUnique(String s){
		for (int i = 0; i < idCount; ++i){
			if (eIds.get(i).name.equals(s)){
				return false;
			}
			
		}
		return true;
	}
	
	/**
	 * @param s	Name of the ID to find
	 * @return 	if found then
	 * 					 return = index of ID
	 * 				else
	 * 					 return = null;
	 */
	static private Integer indexOf(String s){
		for (int i = 0; i < idCount; ++i){
			if (eIds.get(i).name.equals(s)){
				return i;
			}
			
		}
		return null;
	}
	/**  
	 *	Parse a terminal ID for a DecSeq Object 
	 * @return 
	 */
	static ID parseForDecSeq() throws IllegalArgumentException{
		//verify uniqueness of ID
		if (isUnique(t.idName())) {		
			//create instance of ID and add to the list of IDs
			ID nId = new ID(t.idName());
			eIds.add(nId);
			idCount++;
			t.skipToken();
			return nId;
		} else {
			throw new IllegalArgumentException("Error parsing program: ID:: ID must be unique");
		}
		
	}
	
	/**
	 * Parse a terminal ID for a StmtSeq Object
	 */
	@SuppressWarnings("null")
	static ID parseForStmtSeq() {
		
		//verify existence of ID
		Integer i = indexOf(t.idName());
		if (i != null) {		
			t.skipToken();
			//return the instance that was found
			return eIds.get(i);
		} else {
			throw new IllegalArgumentException("Error parsing program: ID:: ID undeclared");
		}
	}		
	
	public void initialize(){
		initialized = true;
	}
	
	public Integer getVal() {
		return val;
	}
	
	public void setVal(Integer val) {
		this.val = val;
		initialized = true;
	}
	
	public Boolean isInitialized(){
		return initialized;
	}
	/**
	 * 
	 */
	public void print() {
		System.out.print(name);		
	}

	
	
}
