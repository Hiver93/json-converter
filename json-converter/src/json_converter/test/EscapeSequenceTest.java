package json_converter.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import json_converter.EscapeSequence;

public class EscapeSequenceTest {
	private EscapeSequence[] escapeSequences = EscapeSequence.values(); 
	@Test
	public void getEscapeSequenceByCharTest() {
		for(EscapeSequence escapeSequence : escapeSequences) {
			assertEquals(escapeSequence, EscapeSequence.getEscapeSequenceByChar(escapeSequence.getCharacter()));
		}
		
		Exception e = assertThrows(RuntimeException.class, ()->EscapeSequence.getEscapeSequenceByChar('a'));
		assertEquals("No escape sequence found for the character: " + 'a', e.getMessage());
	}
	
	@Test
	public void getEscapeSequenceByStringTest() {
		for(EscapeSequence escapeSequence : escapeSequences) {
			assertEquals(escapeSequence, EscapeSequence.getEscapeSequenceByString(escapeSequence.getString()));
		}
		
		Exception e = assertThrows(RuntimeException.class, ()->EscapeSequence.getEscapeSequenceByString("q"));
		assertEquals("No escape sequence found for the string: " + "q", e.getMessage());
	}
	
	@Test
	public void isEscapeSequenceTest() {
		for(EscapeSequence escapeSequence : escapeSequences) {
			assertTrue(EscapeSequence.isEscapeSequence(escapeSequence.getCharacter()));
		}
		assertFalse(EscapeSequence.isEscapeSequence('a'));
		
		for(EscapeSequence escapeSequence : escapeSequences) {
			assertTrue(EscapeSequence.isEscapeSequence(escapeSequence.getString()));
		}
		assertFalse(EscapeSequence.isEscapeSequence("aa"));
	}
}
