package org.cyk.utility.random;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.text.CharacterPredicate;
import org.apache.commons.text.RandomStringGenerator;
import org.cyk.utility.helper.AbstractHelper;

@ApplicationScoped
public class RandomHelperImpl extends AbstractHelper implements RandomHelper, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String getAlphanumeric(Integer length){
		return GENERATOR_ALPHABETIC_NUMERIC.generate(length);
	}
	
	@Override
	public String getAlphabetic(Integer length){
		return GENERATOR_ALPHABETIC.generate(length);
	}
	
	@Override
	public Number getNumeric(Integer length){
		return new BigDecimal(GENERATOR_NUMERIC.generate(length));
	}
	
	/**/
	
	public static final CharacterPredicate CHARACTER_PREDICATE_ALPHABETIC = new CharacterPredicate() {
		@Override
		public boolean test(int codePoint) {
			return Character.isLetter(codePoint);
		}
	};

	public static final CharacterPredicate CHARACTER_PREDICATE_NUMERIC = new CharacterPredicate() {
		@Override
		public boolean test(int codePoint) {
			return Character.isDigit(codePoint);
		}
	};
	
	public static final RandomStringGenerator GENERATOR_ALPHABETIC = new RandomStringGenerator.Builder().withinRange('A', 'z')
			.filteredBy(CHARACTER_PREDICATE_ALPHABETIC).build();
	
	public static final RandomStringGenerator GENERATOR_NUMERIC = new RandomStringGenerator.Builder().withinRange('0', '9').build();
	
	public static final RandomStringGenerator GENERATOR_ALPHABETIC_NUMERIC = new RandomStringGenerator.Builder().withinRange('0', 'z')
			.filteredBy(CHARACTER_PREDICATE_ALPHABETIC,CHARACTER_PREDICATE_NUMERIC).build();
	
}
