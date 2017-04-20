package org.cyk.utility.common.generator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.math3.random.RandomData;
import org.apache.commons.math3.random.RandomDataImpl;
import org.cyk.utility.common.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter @Setter
public class RandomDataProvider implements Serializable {
	
	private static final long serialVersionUID = 3625402455172936488L;
	private static final Logger LOGGER = LoggerFactory.getLogger(RandomDataProvider.class);
	private static final RandomDataProvider INSTANCE = new RandomDataProvider();
	
	public static RandomDataProvider getInstance() {
		return INSTANCE;
	}
	
	/**/
	
	public static final int WORD_EMAIL = 0;
	public static final int WORD_PHONE_NUMBER = 1;
	public static final int WORD_LOCATION = 2;
	public static final int WORD_DATE = 3;
	public static final int WORD_NUMBER = 4;
	public static final int WORD_WEBSITE = 5;
	public static final int WORD_POSTALBOX = 6;
	
	/**/
	
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
	
	private RandomData randomApache = new RandomDataImpl();
	private Random random = new Random();
	private RandomPerson male=new RandomPerson("male"),female=new RandomPerson("female");
	
	private Long dateLimit = DateUtils.MILLIS_PER_DAY*365;
	private Integer intLimit = 30;
	private Integer intFrom = 1;
	private Integer intFromUpdated = intFrom;
	private Integer intTo = intLimit;
	private Boolean intUpdateNext = Boolean.TRUE;
	
	private Date dateFrom = new Date();
	private Date dateTo = new Date(System.currentTimeMillis()+dateLimit);
	private Boolean dateUpdateNext = Boolean.TRUE;
	
	/**/
	private List<String> companyNames,companyLogos=new ArrayList<>(),documentBackgrounds=new ArrayList<>(),documentDraftBackgrounds=new ArrayList<>();
	private List<String> signatureSpecimens=new ArrayList<>(),documentHeaders=new ArrayList<>(),documentFooters=new ArrayList<>();
	/**/
	
	protected RandomDataProvider() {
		companyNames = stringLines("/META-INF/generator/name/company.txt");
		companyLogos = images("/META-INF/generator/image/company/logo/");
		documentBackgrounds = images("/META-INF/generator/image/document/background/");
		documentDraftBackgrounds = images("/META-INF/generator/image/document/background/draft/");
		signatureSpecimens = images("/META-INF/generator/image/signature/specimen/");
		documentHeaders = images("/META-INF/generator/image/document/header/");
		documentFooters = images("/META-INF/generator/image/document/footer/");
	}
	
	public int randomInt(int min,int max){
		if(min==max)
			return min;
		return randomApache.nextInt(min,max);
	}
	
	public int randomPositiveInt(int max){
		return randomInt(0, max);
	}
	
	public int randomPositiveInt(){
		return randomPositiveInt(Integer.MAX_VALUE);
	}
	
	public int nextInt(Integer...exludes){
		int value;
		if(exludes==null)
			value = randomInt(intFromUpdated, intTo);
		else{
			boolean found;
			do{
				found = false;
				value = randomInt(intFromUpdated, intTo);
				for(int i=0;i<exludes.length;i++)
					if(exludes[i]==value){
						found = true;
						break;
					}
			}while(found);
		}
		if(intUpdateNext)
			synchronized(RandomDataProvider.class){
				intFromUpdated = value+1;
				if(intFromUpdated>=intTo)
					intFromUpdated = intFrom;
			}
		return value;
	}
	
	public int nextInt(List<Integer> excludes){
		return nextInt(excludes.toArray(new Integer[]{}));
	}
	
	public boolean randomBoolean(){
		return random.nextBoolean();
	}
	
	public Date randomDate(Date from,Date to){
		long now = from==null?System.currentTimeMillis():from.getTime();
		long limitDate = to==null?now+dateLimit:to.getTime();
		return new Date(randomApache.nextLong(now, limitDate));
	}
	
	public Date nextDate(){
		Date date = randomDate(dateFrom, dateTo);
		if(dateUpdateNext)
			synchronized(RandomDataProvider.class){
				dateFrom = date;
			}
		return date;
	}
	
	public Object randomFromList(List<?> list){
		if(list.isEmpty())
			return null;
		return list.get(randomInt(0, list.size()-1));
	}
	
	public List<?> randomSubList(List<?> list,int size){
		List<Object> l = new LinkedList<Object>();
		if(list.size()<size){
			l.addAll(list);
			return l;
		}
		if(size<1)
			return l;
		Set<Integer> indexes = new LinkedHashSet<Integer>();
		while(indexes.size()<size)
			indexes.add(randomPositiveInt(list.size()-1));
		for(Integer index : indexes)
			l.add(list.get(index));
		return l;
	}
	
	/* person stuff */
		
	@SuppressWarnings("unchecked")
	public List<String> stringLines(String fileName){
		try {
			LOGGER.trace("Reading string lines from file named {}",fileName);
			return (List<String>) IOUtils.readLines(getClass().getResourceAsStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*-------------------------------------------------------------------------------*/
	
	public String randomWord(Integer type,int minLength,int maxLength){
		switch(type){
		case WORD_EMAIL:return RandomStringUtils.randomAlphanumeric(10)+"@examplemail.com";
		case WORD_DATE:return simpleDateFormat.format(randomDate(new Date(System.currentTimeMillis()-DateUtils.MILLIS_PER_DAY*30), new Date()));
		case WORD_NUMBER:return RandomStringUtils.randomNumeric(randomInt(1, 3));
		case WORD_WEBSITE:return "www."+RandomStringUtils.randomAlphabetic(randomInt(5, 6))+".com";
		case WORD_POSTALBOX:return "BP "+RandomStringUtils.randomNumeric(randomInt(2, 3))+" Abidjan";
		case WORD_LOCATION:return "Abidjan";
		case WORD_PHONE_NUMBER:return RandomStringUtils.randomNumeric(8);
		default:return RandomStringUtils.randomAlphabetic(randomInt(minLength, maxLength));
		}
	}
	
	public String randomWord(int minLength,int maxLength){
		return randomWord(randomPositiveInt(10), minLength, maxLength);
	}
	
	public String randomLine(int minWords,int maxWords){
		StringBuilder s = new StringBuilder();
		for(int i=0;i<randomInt(minWords, maxWords);i++)
			s.append(randomWord(3, 10)+" ");
		return s.toString().trim()+StringUtils.repeat("\r\n",randomInt(0, 5)==4?2:1) ;
	}
	
	public String randomText(int minNumberOfLines,int maxNumberOfLines,int minWords,int maxWords){
		StringBuilder s = new StringBuilder();
		for(int i=0;i<randomInt(minNumberOfLines, maxNumberOfLines);i++)
			s.append(randomLine(minWords, maxWords));
		return s.toString();
	}
	
	/**/
	
	public String companyName(){
		return (String) randomFromList(companyNames);
	}
	
	public RandomFile companyLogo(){
		return getFile((String) randomFromList(companyLogos));
	}
	
	public RandomFile documentBackground(){
		return getFile((String) randomFromList(documentBackgrounds));
	}
	
	public RandomFile documentDraftBackground(){
		return getFile((String) randomFromList(documentDraftBackgrounds));
	}
	
	public RandomFile signatureSpecimen(){
		return getFile((String) randomFromList(signatureSpecimens));
	}
	
	public RandomFile documentHeader(){
		return getFile((String) randomFromList(documentHeaders));
	}
	
	public RandomFile documentFooter(){
		return getFile((String) randomFromList(documentFooters));
	}
	
	/**/
	
	public static void main(String args[]){
		System.out.println(RandomDataProvider.getInstance().getMale().firstName());
		System.out.println(RandomDataProvider.getInstance().getMale().middleName());
		System.out.println(RandomDataProvider.getInstance().getMale().lastName());
		System.out.println(RandomDataProvider.getInstance().getMale().middleAndLastName());
		System.out.println(RandomDataProvider.getInstance().getMale().photo());
		System.out.println(RandomDataProvider.getInstance().signatureSpecimen());
		System.out.println(RandomDataProvider.getInstance().documentBackground());
	}
	
	/**/
	
	@Getter @Setter
	public class RandomPerson{
		private List<String> firstNames,middleNames,lastNames,surNames,headOnlyPhotos;
		//private List<byte[]> headOnlyPhotos=new ArrayList<>();
		
		public RandomPerson(String type) {
			firstNames = stringLines("/META-INF/generator/name/first.txt");
			middleNames = stringLines("/META-INF/generator/name/first.txt");
			lastNames = stringLines("/META-INF/generator/name/"+type+"/last.txt");
			surNames = stringLines("/META-INF/generator/name/"+type+"/last.txt");
			headOnlyPhotos = images("/META-INF/generator/image/"+type+"/head/");
			
		}
		
		public String firstName(){
			return StringUtils.trim((String) randomFromList(firstNames));
		}
		
		public String middleName(){
			return StringUtils.trim((String) randomFromList(middleNames));
		}
		
		public String lastName(){
			return StringUtils.trim((String) randomFromList(lastNames));
		}
		
		public String surName(){
			return StringUtils.trim((String) randomFromList(surNames));
		}
		
		public String middleAndLastName(){
			return firstName()+" "+lastName();
		}
		
		public RandomFile photo(){
			return getFile((String) randomFromList(headOnlyPhotos));
		}
		
	}
	
	@Getter @Setter
	public class RandomFile{
		private byte[] bytes;
		private String extension;
		
		public RandomFile(byte[] bytes,String extension) {
			this.bytes = bytes;
			this.extension = extension;
		}
		
		public File createTemporaryFile(){
			File file = null;
			try {
				file = File.createTempFile(RandomStringUtils.randomAlphabetic(5), System.currentTimeMillis()+"");
				if(file!=null && file.exists()){
					file.deleteOnExit();
					IOUtils.write(bytes, new FileOutputStream(file));
				}
				else
					System.out.println("Temporary file cannot be created.");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return file;
		}
		
		@Override
		public String toString() {
			return "Bytes["+(bytes==null?0:bytes.length)+"] , extension("+(extension==null?Constant.EMPTY_STRING:extension)+")";
		}
	}
	
	private List<String> images(String directory){
		List<String> list = stringLines(directory+"_list.txt");
		for(int i=0;i<list.size();i++)
			list.set(i, directory+list.get(i));
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public static <TYPE> Collection<TYPE> generate(Class<TYPE> aClass,Integer count){
		Collection<TYPE> collection = new ArrayList<>();
		for(int i=0;i<count;i++){
			TYPE t;
			try {
				t = (TYPE) aClass.newInstance();
				((AbstractGeneratable<TYPE>)t).generate();
				collection.add(t);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return collection;
	}
	
	public RandomFile getFile(String name){
		try {
			return new RandomFile(IOUtils.toByteArray(RandomDataProvider.class.getResourceAsStream(name)), FilenameUtils.getExtension(name));
		} catch (Exception e) {
			System.err.println("Execption while getting resource <<"+name+">>");
			e.printStackTrace();
			return null;
		}
	}
	
}