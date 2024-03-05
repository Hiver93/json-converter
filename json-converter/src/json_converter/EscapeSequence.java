package json_converter;

public enum EscapeSequence {
    DOUBLE_QUOTE('"', "\\\""),
    BACKSLASH('\\', "\\\\"),
    SLASH('/', "\\/"),
    BACKSPACE('\b', "\\b"),
    FORM_FEED('\f', "\\f"),
    NEW_LINE('\n', "\\n"),
    CARRIAGE_RETURN('\r', "\\r"),
    TAB('\t', "\\t");

    private final char character;
    private final String string;
    private static final EscapeSequence[] ESCAPE_SEQUENCES = EscapeSequence.values();

    EscapeSequence(char character, String escapeSequence) {
        this.character = character;
        this.string = escapeSequence;
    }

    public char getCharacter() {
        return character;
    }

    public String getString() {
        return string;
    }
    
    static public EscapeSequence getEscapeSequenceByString(String sequence) {
    	for(EscapeSequence es : ESCAPE_SEQUENCES) {
    		if(sequence.equals(es.getString())) {
    			return es;
    		}
    	}
    	throw new RuntimeException("No escape sequence found for the string: " + sequence);
    }
    
    static public EscapeSequence getEscapeSequenceByChar(char c) {
    	for(EscapeSequence es : ESCAPE_SEQUENCES) {
    		if(c==es.getCharacter()) {
    			return es;
    		}
    	}
    	throw new RuntimeException("No escape sequence found for the character: " + c);
    }
    
    static public boolean isEscapeSequence(char c) {
    	for(EscapeSequence es : ESCAPE_SEQUENCES) {
    		if(c==es.getCharacter()) {
    			return true;
    		}
    	}
    	return false;
    }
    static public boolean isEscapeSequence(String str) {
    	for(EscapeSequence es : ESCAPE_SEQUENCES) {
    		if(str.equals(es.getString())) {
    			return true;
    		}
    	}
    	return false;
    }
}
