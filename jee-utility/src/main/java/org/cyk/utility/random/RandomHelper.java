package org.cyk.utility.random;

import java.math.BigDecimal;

import org.apache.commons.text.CharacterPredicate;
import org.apache.commons.text.RandomStringGenerator;
import org.cyk.utility.helper.Helper;

public interface RandomHelper extends Helper {

	public static String getAlphanumeric(Integer length){
		return GENERATOR_ALPHABETIC_NUMERIC.generate(length);
	}
	
	public static String getAlphabetic(Integer length){
		return GENERATOR_ALPHABETIC.generate(length);
	}
	
	public static Number getNumeric(Integer length){
		return new BigDecimal(GENERATOR_NUMERIC.generate(length));
	}
	
	/**/
	
	CharacterPredicate CHARACTER_PREDICATE_ALPHABETIC = new CharacterPredicate() {
		@Override
		public boolean test(int codePoint) {
			return Character.isLetter(codePoint);
		}
	};

	CharacterPredicate CHARACTER_PREDICATE_NUMERIC = new CharacterPredicate() {
		@Override
		public boolean test(int codePoint) {
			return Character.isDigit(codePoint);
		}
	};
	
	RandomStringGenerator GENERATOR_ALPHABETIC = new RandomStringGenerator.Builder().withinRange('A', 'z').filteredBy(CHARACTER_PREDICATE_ALPHABETIC).build();
	
	RandomStringGenerator GENERATOR_NUMERIC = new RandomStringGenerator.Builder().withinRange('0', '9').build();
	
	RandomStringGenerator GENERATOR_ALPHABETIC_NUMERIC = new RandomStringGenerator.Builder().withinRange('0', 'z').filteredBy(CHARACTER_PREDICATE_ALPHABETIC,CHARACTER_PREDICATE_NUMERIC).build();

}
