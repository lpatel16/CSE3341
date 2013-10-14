package Tokenizer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



/**
 *  @author gibsonr
 */
public class Tokenizer {
	//;:\\,=\\!\\[]\\(\\)&\\|\\+\\*<>
	private static String SPECIALSYMBOLS = "[;:,=\\-\\!\\[\\]\\(\\)\\&\\|\\+\\*<>]";
	private static String WSCHARS = ("\\s");
	private static String ALPHA_UPPER = ("[A-Z]");
	private static String DIGIT = ("[0-9]");
	
	private FileReader reader;
	private Character readAheadChar;
	private Integer currTokenID;
	private Integer intValue;
	private String idString;
	private Boolean valid;
	
	private static Tokenizer  inst = null;
	
	public static Tokenizer instance(){
			assert inst != null: "Tokenizer not connected to input.";
			return inst;
	}
	
	public static void connectToInputStream(File inFile){
		inst = new Tokenizer(inFile);
	}
	
	public static enum tokenID {
		ERROR,//0
		PROGRAM ,
		BEGIN,//2
		END,
		INT,//4
		IF,
		THEN,//6
		ELSE,
		WHILE,//8
		LOOP,
		READ,//10
		WRITE,
		SEMICOLON,//12
		COMMA,
		ASSIGN,//14
		EXCLAMATION,
		LEFTBRACE,//16
		RIGHTBRACE,
		AND,//18
		OR,
		LEFTPAR,//20
		RIGHTPAR,
		ADD,//22
		SUBTRACT,
		MULTIPLY,//24
		NOTEQUAL,
		EQUAL,//26
		LESSTHAN,
		GREATERTHAN,//28
		LESSTHANOREQUALTO,
		GREATERTHANOREQUALTO, //30
		INTEGER,
		IDENTIFIER, //32
		EOF
	}
	
	private Tokenizer(File inFile) {
		try {
			reader = new FileReader(inFile);
		} catch (FileNotFoundException e) {
			System.out.println("Error opening file: " + e.getMessage());
			System.exit(1);
		}
		this.readAheadChar = this.getNextNonWSChar();
		
		//empty file must have EOF...RIGHT?!?!
		if (this.readAheadChar == null) {
			this.valid = true;
			this.currTokenID = 33;
		} else {
			valid = this.skipToken();
		}
	}	
	
	private Character getNextNonWSChar() {
		int i = 0;
		Character c = null;
		
		do{
			try {
				i = reader.read();
				//reader return -1 if EOF
				if (i != -1){ 
					c = (char) i; 
				} else {
					c = null;
				}
			} catch (IOException e) {
				System.out.println("Error reading file: " + e.getMessage());
			}
		} while ((c != null) && (c.toString().matches("\\s")));
		
		return c;
	}
	
	private Character getNextChar() {
		int i = 0;
		Character c = null;
		
		try {
			i = reader.read();
			if (i != -1){ 
				c = (char) i; 
			}else {
				c = null;
			}
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}

		return c;
	}
	
	private Boolean tokenizeKeyword(String keyword)
	{
		Integer c = 0;
		Boolean result = true;
		
		//build token string
		for (int i = 1; (i < keyword.length()) && (c != -1); ++i) {
			try {
				c = this.reader.read();
			} catch (IOException e) {
				System.out.println("Error reading file: " + e.getMessage());
			}
			if (c != keyword.charAt(i)) {
				result = false;
			}	
		}

		if (result) {
			//get next rAC
			this.readAheadChar = this.getNextChar();
			if (this.readAheadChar != null){
				if (!this.readAheadChar.toString().matches(SPECIALSYMBOLS)){
					result = this.readAheadChar.toString().matches(WSCHARS);		
				}
			}
		}
		return result;
	}

	
	/**Produces the integer value of the token
	 *  
	 * @return integer value of the tokenID
	 */
	public Integer getToken() {
		return currTokenID;
	}
	/**
	 * Skips past the current and prepares the next token for getToken().
	 * 
	 * @requires that the EOF has not been reached
	 * 
	 * @returns true if the token was successfully skipped
	 * 			false if the token contained an error
	 */
	public Boolean skipToken() {
		valid = false;
		
		//dispose of currToken attributes
		currTokenID = 0;
		intValue = null;
		idString = "";
		
		if (this.readAheadChar == null){//EOF 33
			currTokenID = tokenID.EOF.ordinal();
			valid = true;
		} else {
			switch (this.readAheadChar) {
				case 'p'://1
					if (this.tokenizeKeyword("program")) {
						currTokenID = tokenID.PROGRAM.ordinal();
						valid = true;
					} 
					break;
				case 'b'://2
					if (this.tokenizeKeyword("begin")) {
						currTokenID = tokenID.BEGIN.ordinal();
						valid = true;
					} 
					break;
				case 'e': //end || else  3 || 7
					this.readAheadChar = this.getNextChar();
					if (this.readAheadChar == null) {
						break;
					}
					switch (this.readAheadChar){
						case 'n': 	//3
							if (this.tokenizeKeyword("nd")){
								currTokenID = tokenID.END.ordinal();
								valid = true;
							}
							break;
						case 'l': 	//7
							if (this.tokenizeKeyword("lse")) {
								currTokenID = tokenID.ELSE.ordinal();
								valid = true;
							}
							break;
					}
					break;
				case 'i'://int || if  4 || 5
					this.readAheadChar = this.getNextChar();
					if (this.readAheadChar == null) {
						break;
					}
					switch (this.readAheadChar){
						case 'n':	//4
							if (this.tokenizeKeyword("nt")){
								currTokenID = tokenID.INT.ordinal();
								valid = true;
							}
							break;
						case 'f':	//5
							if (this.tokenizeKeyword("f")) {
								currTokenID = tokenID.IF.ordinal();
								valid = true;
							}
							break;
					}
					break;
				case 't'://6
					if (this.tokenizeKeyword("then")) {
						currTokenID = tokenID.THEN.ordinal();
						valid = true;
					} 
					break;
				case 'w': //while || write  8 || 11 
					this.readAheadChar = this.getNextChar();
					if (this.readAheadChar == null) {
						break;
					}
					switch (this.readAheadChar){
						case 'h':	//8
							if (this.tokenizeKeyword("hile")){
								currTokenID = tokenID.WHILE.ordinal();
								valid = true;
							}
							break;
						case 'r':	//11
							if (this.tokenizeKeyword("rite")) {
								currTokenID = tokenID.WRITE.ordinal();
								valid = true;
							}
							break;
					}
					break;
				case 'l'://9
					if (this.tokenizeKeyword("loop")) {
						currTokenID = tokenID.LOOP.ordinal();
						valid = true;
					}
					break;
				case 'r'://10
					if (this.tokenizeKeyword("read")) {
						currTokenID = tokenID.READ.ordinal();
						valid = true;
					}
					break;
				case ';'://12
					currTokenID = tokenID.SEMICOLON.ordinal();
					valid = true;
					this.readAheadChar = this.getNextChar();
					break;
				case ','://13
					currTokenID = tokenID.COMMA.ordinal();
					valid = true;
					this.readAheadChar = this.getNextChar();
					break;
				case '='://assign || equal	14 || 26
					this.readAheadChar = this.getNextChar();
					if (this.readAheadChar == null) {
						valid = true;
						break;
					}
					switch (this.readAheadChar){
						case '=':	//26
							currTokenID = tokenID.NOTEQUAL.ordinal();
							valid = true;
							this.readAheadChar = this.getNextChar();
							break;
						default:	//14
							currTokenID = tokenID.ASSIGN.ordinal();
							valid = true;
							break;
					}
					break;
				case '['://16
					currTokenID = tokenID.LEFTBRACE.ordinal();
					valid = true;
					this.readAheadChar = this.getNextChar();
					break;
				case ']'://17
					currTokenID = tokenID.RIGHTBRACE.ordinal();
					valid = true;
					this.readAheadChar = this.getNextChar();
					break;
				case '&'://18
					this.readAheadChar = this.getNextChar();
					if (this.readAheadChar == null) {
						break;
					}
					if (this.readAheadChar == '&'){
						currTokenID = tokenID.AND.ordinal();
						valid = true;
						this.readAheadChar = this.getNextChar();
					}
					break;
				case '|'://19
					this.readAheadChar = this.getNextChar();
					if (this.readAheadChar == null) {
						break;
					}
					if (this.readAheadChar == '|'){
						currTokenID = tokenID.OR.ordinal();
						valid = true;
						this.readAheadChar = this.getNextChar();			
					}
					break;
				case '('://20
					currTokenID = tokenID.LEFTPAR.ordinal();
					valid = true;
					this.readAheadChar = this.getNextChar();
					break;
				case ')'://21
					currTokenID = tokenID.RIGHTPAR.ordinal();
					valid = true;this.readAheadChar = this.getNextChar();
					break;
				case '+'://22
					currTokenID = tokenID.ADD.ordinal();
					valid = true;
					this.readAheadChar = this.getNextChar();
					break;
				case '-'://23
					currTokenID = tokenID.SUBTRACT.ordinal();
					valid = true;
					this.readAheadChar = this.getNextChar();
					break;
				case '*'://24
					currTokenID = tokenID.MULTIPLY.ordinal();
					valid = true;
					this.readAheadChar = this.getNextChar();
					break;
				case '!'://exclamation || notequal	15 || 25
					this.readAheadChar = this.getNextChar();
					if (this.readAheadChar == null) {
						valid = true;
						break;
					}
					switch (this.readAheadChar){
						case '=':	//25
							currTokenID = tokenID.NOTEQUAL.ordinal();
							valid = true;
							this.readAheadChar = this.getNextChar();
							break;
						default:	//15
							currTokenID = tokenID.EXCLAMATION.ordinal();
							valid = true;
							break;
					}
					break;
				case '<'://lessthan || lessthanorequalto	27 || 29
					this.readAheadChar = this.getNextChar();
					if (this.readAheadChar == null) {
						valid = true;
						break;
					}
					switch (this.readAheadChar){
						case '=':	//29
							currTokenID = tokenID.LESSTHANOREQUALTO.ordinal();
							valid = true;
							this.readAheadChar = this.getNextChar();
							break;
						default:	//27
							currTokenID = tokenID.LESSTHAN.ordinal();
							valid = true;
							break;
					}
					break;
				case '>'://greaterthan || greaterthanorequalto	28 || 30
					this.readAheadChar = this.getNextChar();
					if (this.readAheadChar == null) {
						valid = true;
						break;
					}
					switch (this.readAheadChar){
						case '=':	//30
							currTokenID = tokenID.GREATERTHANOREQUALTO.ordinal();
							valid = true;
							this.readAheadChar = this.getNextChar();
							break;
						default:	//28
							currTokenID = tokenID.GREATERTHAN.ordinal();
							valid = true;break;
					}
					break;
				default://identifier || integer
					if (this.readAheadChar.toString().matches(ALPHA_UPPER)) { //32
						if(tokenizeIdentifier()){
							currTokenID = tokenID.IDENTIFIER.ordinal();
							valid = true;
							break;
						}
					} else {	//31
						if (tokenizeInteger()){
							currTokenID = tokenID.INTEGER.ordinal();
							valid = true;
							break;
						}
					}
					
			}
	
			if (!valid) {
				System.out.println("Invalid token found. Closing file");
				try {
					this.reader.close();
				} catch (IOException e) {
					System.out.println("Error closing file: " + e.getMessage());
				}
				System.exit(1);
			} else if (this.readAheadChar != null){
				if (this.readAheadChar.toString().matches(WSCHARS)) {
					this.readAheadChar = this.getNextNonWSChar();
				}
			}
		}
	return valid;
	}
	
	
	

	private Boolean tokenizeInteger() {
		Boolean result = true;
		String x = "";
		//build token string
		while ((this.readAheadChar != null) && 
				(this.readAheadChar.toString().matches(DIGIT))){
			x+=this.readAheadChar.toString();
			this.readAheadChar = this.getNextChar();
		}
		
		if ((this.readAheadChar == null) || 
			(!this.readAheadChar.toString().matches(WSCHARS)) ||
			(!this.readAheadChar.toString().matches(SPECIALSYMBOLS))) {
				this.intValue = Integer.valueOf(x);
		} else {
			result = false;
		}
			
		return result;
		
	}

	private Boolean tokenizeIdentifier() {
		Boolean result = true;
		//build id string
		while ((this.readAheadChar != null) && (this.readAheadChar.toString().matches(ALPHA_UPPER))){
			this.idString += this.readAheadChar.toString();
			this.readAheadChar = this.getNextChar();
		}
		//id has a numerical ending
		if (this.readAheadChar.toString().matches(DIGIT)){
			//build numerical ending also ensures the validity of the next rAChar
			if (this.tokenizeInteger()) {
				this.idString += this.intValue.toString();
			} else {
				result = false;
			}
		//check for EOF, WS or Special symbol after non-numerical ending
		} else if (this.readAheadChar != null){
			if (!this.readAheadChar.toString().matches(SPECIALSYMBOLS)){
				result = this.readAheadChar.toString().matches(WSCHARS);		
			}
		}
		return result;
		
	}

	
	/**
	 * 
	 * @returns integer x,
	 * 			if token == INTEGER then
	 * 				x = token.IntegerValue
	 * 			else 
	 * 				x = null
	 */
	public Integer getIntVal()
	{
		if (this.intValue == null) {
			System.out.println("Error: Current token is not an INTEGER");
			return null;
		} else {	 
			return intValue;
		}
	}
	/**
	 * 
	 * @returns string x,
	 * 			if token == String then
	 * 				x = token.StringValue
	 * 			else 
	 * 				x = null
	 */
	public String idName()
	{
		if (this.idString == "") {
			System.out.println("Error: Current token is not an IDENTIFIER");
			return null;
		} else { 
			return idString;
		}
	}
	
	
}
