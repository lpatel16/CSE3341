/**
 * 
 */
package Interpreter;
/**
 * @author gibsonr
 *
 */
/**
 * @author gibsonr
 * CoreSequence represents a sequence of Terminal and non-Terminal Symbols in the OSU
 * CSE Defined Core Programming Language
 * 
 */
public interface CoreSequence {
	
	/**
	 *  Parses Selected Sequence into Object
	 *  @requires Tokenizer.getToken() == first token of this Sequence 
	 *  @ensures Tokenizer.getToken() == first token of the next Sequence
	 * 
	 * 	@throws ParseException  Will notify of ill-formed a core sequence
	 */
	public void parse() throws IllegalArgumentException;
	
	/**
	 *	Pretty Prints Selected Sequence 
	 *
	 * @param indents number of indentations
	 */
	public void print(Integer indents);
	
	/**
	 * 	Executes Core Instruction Sequence
	 */
	public void execute();
}
