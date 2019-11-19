package org.cyk.utility.__kernel__.random;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.text.CharacterPredicate;
import org.apache.commons.text.RandomStringGenerator;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierHelper;
import org.cyk.utility.__kernel__.string.StringHelper;

public interface RandomHelper {

	public static String getAlphanumeric(Integer length){
		return GENERATOR_ALPHABETIC_NUMERIC.generate(length);
	}
	
	public static String getAlphabetic(Integer length){
		return GENERATOR_ALPHABETIC.generate(length);
	}
	
	public static Number getNumeric(Integer length){
		return new BigDecimal(GENERATOR_NUMERIC.generate(length));
	}
	
	public static <T> T get(List<T> list){
		if(CollectionHelper.isEmpty(list))
			return null;
		return list.get(ThreadLocalRandom.current().nextInt(0, list.size()));
	}
	
	public static String getFirstName(){
		return get(HUMAN_FIRST_NAMES);
	}
	
	public static String getMaleLastNames(Integer count){
		if(count == null || count < 1)
			return null;
		Set<String> names = new LinkedHashSet<>();
		for(Integer index = 0 ; index < count ; index = index + 1)
			names.add(get(HUMAN_MALE_LAST_NAMES));
		return StringHelper.concatenate(names, ConstantCharacter.SPACE.toString());
	}
	
	public static String getMaleLastName(){
		return getMaleLastNames(1);
	}
	
	public static String getLastNames(){
		return getMaleLastNames(ThreadLocalRandom.current().nextInt(1,2));
	}
	
	public static String getMaleNames(){
		return getFirstName()+" "+getMaleLastName();
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

	List<String> HUMAN_FIRST_NAMES = new ArrayList<>(StringHelper.getLines(UniformResourceIdentifierHelper.getContentAsString(RandomHelper.class.getResource("name/human/first.txt"))));
	List<String> HUMAN_MALE_LAST_NAMES = new ArrayList<>(StringHelper.getLines(UniformResourceIdentifierHelper.getContentAsString(RandomHelper.class.getResource("name/human/last_male.txt"))));
	List<String> HUMAN_FEMALE_LAST_NAMES = new ArrayList<>(StringHelper.getLines(UniformResourceIdentifierHelper.getContentAsString(RandomHelper.class.getResource("name/human/last_female.txt"))));
}