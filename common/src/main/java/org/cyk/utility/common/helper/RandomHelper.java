package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.commons.text.CharacterPredicate;
import org.apache.commons.text.RandomStringGenerator;
import org.cyk.utility.common.Builder;
import org.cyk.utility.common.Constant;

@Singleton @Named
public class RandomHelper extends AbstractHelper implements Serializable {
	static final long serialVersionUID = 1L;
	
	private static RandomHelper INSTANCE;
	public static enum StringCollection {COMPANY_NAME,COMPANY_LOGO,DOCUMENT_BACKGROUND,DOCUMENT_DRAFT_BACKGROUND,SIGNATURE_SPECIMEN,DOCUMENT_HEADER,DOCUMENT_FOOTER
		,FIRST_NAME,MIDDLE_NAME,SUR_NAME,MALE_LAST_NAME,FEMALE_LAST_NAME,MALE_HEAD_ONLY_PHOTO,FEMALE_HEAD_ONLY_PHOTO}
	
	private RandomDataGenerator randomApache = new RandomDataGenerator();
	private Map<StringCollection,List<String>> stringCollectionMap = new HashMap<>();
	
	public static RandomHelper getInstance() {
		if(INSTANCE == null){
			INSTANCE = new RandomHelper();
			INSTANCE.initialisation();
		}
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
		readLines(StringCollection.COMPANY_NAME, "/META-INF/generator/name/company.txt");
		readLines(StringCollection.FIRST_NAME, "/META-INF/generator/name/first.txt");
		readLines(StringCollection.MIDDLE_NAME, "/META-INF/generator/name/first.txt");
		readLines(StringCollection.SUR_NAME, "/META-INF/generator/name/first.txt");
		
		readPersonLines();
		
	}
	
	private void readLines(StringCollection set,String fileName,Boolean isDirectory){
		if(Boolean.TRUE.equals(isDirectory))
			fileName = fileName + "_list.txt";
		stringCollectionMap.put(set, FileHelper.getInstance().readLines(fileName));
	}
	
	private void readLines(StringCollection set,String fileName){
		readLines(set, fileName, StringUtils.endsWith(fileName, "/"));
	}
	
	private String getMaleOrFemale(Boolean isMale){
		return Boolean.TRUE.equals(isMale) ? "male" : "female";
	}
	
	private void readPersonLines(Boolean isMale){
		String male = getMaleOrFemale(isMale);
		readLines(StringCollection.valueOf(male.toUpperCase()+"_LAST_NAME"), "/META-INF/generator/name/"+male+"/last.txt");
		readLines(StringCollection.valueOf(male.toUpperCase()+"_HEAD_ONLY_PHOTO"), "/META-INF/generator/image/"+male+"/head/");
	}
	
	private void readPersonLines(){
		readPersonLines(Boolean.FALSE);
		readPersonLines(Boolean.TRUE);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> aClass){
		if(java.lang.String.class.equals(aClass))
			return (T) getString(5);
		ThrowableHelper.getInstance().throwNotYetImplemented();
		return null;
	}
	
	public String getString(Integer minimumLength,Integer maximumLength){
		return getAlphanumeric(getInteger(minimumLength, maximumLength));
	}
	
	public String getString(Integer length){
		return getString(length,length);
	}
	
	public String getLines(Integer minimumNumberOfLines,Integer maximumNumberOfLines,Integer minimumLineLength,Integer maximumLineLength){
		Collection<String> lines = new ArrayList<>();
		Integer numberOfLines = getInteger(minimumNumberOfLines, maximumNumberOfLines);
		for(Integer index = 0 ; index < numberOfLines ; index ++)
			lines.add(getString(minimumLineLength, maximumLineLength)+Constant.CHARACTER_DOT);
		return StringHelper.getInstance().concatenate(lines,  Constant.LINE_DELIMITER);
	}
	
	public String getAlphanumeric(Integer length){
		return GENERATOR_ALPHABETIC_NUMERIC.generate(length);
	}
	
	public String getAlphabetic(Integer length){
		return GENERATOR_ALPHABETIC.generate(length);
	}
	
	public Number getNumeric(Integer length){
		return new BigDecimal(GENERATOR_NUMERIC.generate(length));
	}
	
	public Boolean getBoolean(){
		return getInteger(1, 2) == 1;
	}
	
	public String getElectronicMailAddress(){
		return String.format(Constant.SimpleMailTransferProtocol.ADDRESS_FORMAT, getAlphabetic(5),getAlphabetic(5),getAlphabetic(5));
	}
	
	public Integer getInteger(Integer minimum,Integer maximum){
		return randomApache.nextInt(minimum, maximum);
	}
	
	public Date getDate(){
		return TimeHelper.getInstance().getDate(getInteger(1970, 2016), getInteger(1, 12), getInteger(1, 28));
	}
	
	public <T> T getElement(T[] elements){
		if(ArrayHelper.getInstance().isEmpty(elements))
			return null;
		return elements[getInteger(0, elements.length-1)];	
	}
	
	public <T> T getElement(Collection<T> collection){
		if(CollectionHelper.getInstance().isEmpty(collection))
			return null;
		return CollectionHelper.getInstance().getElementAt(collection, getInteger(0, collection.size()-1));
	}
	
	public String get(StringCollection stringCollection){
		return getElement(stringCollectionMap.get(stringCollection));
	}
	
	public FileHelper.File getFile(String name){
		try {
			FileHelper.File file = new FileHelper.File()
					.setName(FileHelper.getInstance().getName(name))
					.setBytes(FileHelper.getInstance().getBytes(RandomHelper.class,name))
					.setExtension(FileHelper.getInstance().getExtension(name))
					;
			return file.setMime(file.getExtension());
		} catch (Exception e) {
			logThrowable(e);
			return null;
		}
	}
	
	public FileHelper.File getFilePersonHeadOnly(Boolean isMale){
		String male = getMaleOrFemale(isMale);
		String name = get(StringCollection.valueOf(male.toUpperCase()+"_HEAD_ONLY_PHOTO"));
		FileHelper.File file = getFile("/META-INF/generator/image/"+male+"/head/"+name);
		file.setName(file.getName()+Constant.CHARACTER_UNDESCORE+System.currentTimeMillis());
		return file;
	}
	
	public Person getPerson(Boolean isMale){
		return new Person(isMale);
	}
	
	public Person getPerson(){
		return getPerson(getBoolean());
	}
	
	public Collection<Person> getPersons(Integer count){
		Collection<Person> persons = new ArrayList<>();
		for(Integer index = 0 ; index < count ; index ++)
			persons.add(getPerson());
		return persons;
	}
	/**/
	
	/**/
	
	public static interface Random<OUTPUT> extends Builder.NullableInput<OUTPUT> {
		
		public static class Adapter<OUTPUT> extends Builder.NullableInput.Adapter.Default<OUTPUT> implements Random<OUTPUT> , Serializable {
			private static final long serialVersionUID = -5769331804489939201L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}
			
			public static class Default<OUTPUT> extends Random.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
				
			}
			
		}
		
		public static interface String extends Random<java.lang.String> {
			
			public static class Adapter extends Random.Adapter.Default<java.lang.String> implements String , Serializable {
				private static final long serialVersionUID = -5769331804489939201L;

				public Adapter() {
					super(java.lang.String.class);
				}
				
				public static class Default extends String.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;

				}
				
			}
		}
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
	
	/**/
	
	@Getter @Setter
	public class Person implements Serializable{
		private static final long serialVersionUID = 1L;
		
		private Boolean isMale;
		private String firstname,middlename,lastname,surname;
		private FileHelper.File headOnlyPhoto;
		
		public Person(Boolean isMale) {
			this.isMale = isMale;
			String male = getMaleOrFemale(this.isMale);
			firstname = get(StringCollection.FIRST_NAME);
			middlename = get(StringCollection.MIDDLE_NAME);
			lastname = get(StringCollection.valueOf(male.toUpperCase()+"_LAST_NAME"));
			surname = get(StringCollection.SUR_NAME);
			
			headOnlyPhoto = getFilePersonHeadOnly(this.isMale);	
		}
	}
}
