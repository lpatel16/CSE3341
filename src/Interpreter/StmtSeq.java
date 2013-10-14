package Interpreter;

import java.util.ArrayList;

import Tokenizer.Tokenizer;

import Tokenizer.Tokenizer.tokenID;

/**
 * @author gibsonr
 *
 */
public class StmtSeq implements CoreSequence{
	
	private ArrayList<Statement> stmts;
	private Tokenizer t;
	
	
	public StmtSeq(){
		stmts= new ArrayList<Statement>();
		t = Tokenizer.instance();
	}
	
	public Boolean isStatementToken(Integer token){
		return ((token == tokenID.IDENTIFIER.ordinal()) ||
			(token == tokenID.IF.ordinal()) ||
			(token == tokenID.READ.ordinal()) ||
			(token == tokenID.WRITE.ordinal()) ||
			(token == tokenID.WHILE.ordinal()));
	}
	
	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#parse()
	 */
	@Override
	public void parse(){
		Statement stmt;
		
		if (!isStatementToken(t.getToken())) {
			throw new IllegalArgumentException("Error parsing program: STMTSEQ::Expected 'stmt' token.");
		}
		
		//get all declarations
		while (isStatementToken(t.getToken())) {
			stmt = new Statement();
			stmt.parse();
			stmts.add(stmt);
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
		for (int i = 0; i < stmts.size(); i++){
			stmts.get(i).print(indents);
		}
		

	}
	
	/* (non-Javadoc)
	 * @see Interpreter.CoreSequence#execute()
	 */
	@Override
	public void execute(){
		for (int i = 0; i < stmts.size(); i++){
			stmts.get(i).execute();
		}
	}
}
