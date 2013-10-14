package Interpreter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Tokenizer.Tokenizer;
import Tokenizer.Tokenizer.tokenID;

/**
 * 
 */

/**
 * @author gibsonr
 *
 */
public class Program implements CoreSequence{
		private DecSeq ds; 
		private StmtSeq ss; 
		private Tokenizer t;
		static private ArrayList<Integer> inputs;
		static private Integer currentInput;

	
	public Program(File inFile, File inputFile) throws FileNotFoundException {
		ds = null; ss = null;
		currentInput = 0;
		Tokenizer.connectToInputStream(inFile);
		t = Tokenizer.instance();
		
		parseInputs(inputFile);
	}
	
	private static void parseInputs(File inFile) throws FileNotFoundException{
		Scanner scanner = new Scanner(inFile);
		inputs = new ArrayList<Integer>();
		while (scanner.hasNext()){
			inputs.add(scanner.nextInt());
		}		
	}
	
	public  static Integer getNextInput(){
		return inputs.get(currentInput++);
	}

	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#parse()
	 */
	@Override
	public void parse() {
		
		//check to make sure program was sent as current token
		if (t.getToken()!= tokenID.PROGRAM.ordinal()){
			throw new IllegalArgumentException("Error parsing program: PROG:: Expected 'program' token.");
		}
		t.skipToken();
		//parse the declaration sequence
		ds = new DecSeq();
		ds.parse();
		
		//check valid sequence
		if (t.getToken()!= tokenID.BEGIN.ordinal()){
			throw new IllegalArgumentException("Error parsing program: PROG:: Expected 'begin' token.");
		}
		//skip over 'begin'
		t.skipToken();
		
		//parse the statement sequence
		ss = new StmtSeq();
		ss.parse();
		
		//check valid sequence
		if (t.getToken()!= tokenID.END.ordinal()){
			throw new IllegalArgumentException("Error parsing program: PROG:: Expected 'end' token.");
		}
		//skip over end
		t.skipToken();
	}
	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#print()
	 */
	
	private String setIndents(Integer indents){
		String indent = new String("");
		for (int i = 0; i < indents; ++i){
			indent += "\t";
		}
		return indent;
	}
	@Override
	public void print(Integer indents){
		String indent = setIndents(indents);
		
		//print program
		System.out.print(indent + "program ");
		
		//print ds on same line
		ds.print(0);
		System.out.println();
		
		//print begin and first statement
		indents++;
		indent = setIndents(indents);
		System.out.println(indent + "begin ");
		
		//print statements
		indents++;
		indent = setIndents(indents);
		ss.print(indents);
		
		//print end
		indents--;
		indent = setIndents(indents);
		System.out.println(indent + "end");
		
	}
	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#execute()
	 */
	@Override
	public void execute(){
		ss.execute();
		
	}
}
