package org.cyk.utility.test;

import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractTest implements Serializable {
	
	private static final long serialVersionUID = -4375668358714913342L;
	
	public static Namespace ARQUILLIAN_NAMESPACE = Namespace.getNamespace("http://jboss.org/schema/arquillian");
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTest.class);
	
	//protected Collection<AbstractTestMethod> methods = new ArrayList<>();
	
	protected static AbstractMethod<Object, Object> AFTER_CLASS_METHOD;
	
	@Before
	public final void before() throws Exception {
		_before_();
		_methods_();
	}
	
	protected void _before_() throws Exception{}
	
	protected void _methods_(){}
	
	@After
	public final void after() throws Exception {_after_();}
	protected void _after_() throws Exception {}
	
	@AfterClass
	public static final void afterClass() throws Exception {
		if(AFTER_CLASS_METHOD!=null)
			AFTER_CLASS_METHOD.execute();
	}
	/**/
	
	@Test
	public final void execute(){
		_execute_();
	}
	
	protected void _execute_(){
		/*for(AbstractTestMethod method : methods)
			method.execute();*/
	}
	
	protected Date date(Integer year,Integer monthOfYear,Integer dayOfMonth,Integer hourOfDay,Integer minuteOfHour,Integer secondOfMinute,Integer millisOfSecond){
		return new DateTime(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond).toDate();
	}
	
	protected Date date(Integer year,Integer monthOfYear,Integer dayOfMonth,Integer hourOfDay,Integer minuteOfHour,Integer secondOfMinute){
		return date(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour, secondOfMinute, 0);
	}
	
	protected Date date(Integer year,Integer monthOfYear,Integer dayOfMonth,Integer hourOfDay,Integer minuteOfHour){
		return date(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour, 0);
	}
	
	protected Date date(Integer year,Integer monthOfYear,Integer dayOfMonth,Integer hourOfDay){
		return date(year, monthOfYear, dayOfMonth, hourOfDay, 0);
	}
	
	protected Date date(Integer year,Integer monthOfYear,Integer dayOfMonth){
		return date(year, monthOfYear, dayOfMonth, 0);
	}
	
	protected Date date(Integer year,Integer monthOfYear){
		return date(year, monthOfYear, 0);
	}
	
	protected Date date(Integer year){
		return date(year, 0);
	}
	
	protected BigDecimal bigDecimal(String value){
		return new BigDecimal(value);
	}
	protected BigDecimal bigDecimal(Integer value){
		return new BigDecimal(value);
	}
	
	/**/
	
	protected void assertEquals(String message, Object expected, Object actual){
		Assert.assertEquals(message, expected, actual);
	}
	protected void assertEquals(Object expected, Object actual){
		Assert.assertEquals(expected, actual);
	}
	
	protected void assertBigDecimalValue(String message,String expected,BigDecimal value){
    	Assert.assertEquals(message,new BigDecimal(expected).doubleValue()+"", value.doubleValue()+"");
    }
	
	/* Hamcrest Short cuts*/
	
	protected static void assertThat(String reason,Boolean assertion){
		MatcherAssert.assertThat(reason, assertion);
	}
	
	protected static <T> void assertThat(T actual,Matcher<? super T> matcher){
		MatcherAssert.assertThat(actual, matcher);
	}
	
	protected static <T> void assertThat(String reason,T actual,Matcher<? super T> matcher){
		MatcherAssert.assertThat(reason,actual, matcher);
	}
	
	protected static void hasProperty(Object object,String name,Object value){
		assertThat(object, hasPropertyMatcher(name, value));
	}
	
	protected static void hasProperties(Object object,Object...entries){
		for(int i=0;i<entries.length;i=i+2)
			hasProperty(object, (String) entries[i], entries[i+1]);
	}
	
	protected static <T> void contains(Class<T> aClass,Collection<T> list,Object[] names,Object[][] values){
		MatcherAssert.assertThat(list,Matchers.contains(hasPropertiesMatchers(aClass,names, values)));
	}
	
	
	/**/
	
	/* Harmcrest Matchers*/
	
	protected static Matcher<Object> hasPropertyMatcher(String name,Object value){
		return Matchers.hasProperty(name,Matchers.is(value));
	}
	
	protected static <T> List<Matcher<? super T>> hasPropertiesMatchers(Class<T> aClass,Object[] names,Object[][] values){
		List<Matcher<? super T>> matchers = new ArrayList<>();
		for(Object[] objectValues : values){
			List<Matcher<? super T>> objectMatchers = new ArrayList<>();
			for(int i=0;i<objectValues.length;i++){
				Object infos = names[i];
				String name;
				Class<?> type = null;
				Object value = objectValues[i];
				if(infos instanceof Object[]){
					name = (String) ((Object[]) infos)[0];
					if(((Object[]) infos).length>1){
						type = (Class<?>) ((Object[]) infos)[1];
					}
				}else{
					name = (String) names[i];
				}
				if(type==null)
					type = FieldUtils.getField(aClass, name, Boolean.TRUE).getType();
				if(value!=null && !value.getClass().equals(type)){
					if(BigDecimal.class.equals(type))
						if(value instanceof String)
							value = new BigDecimal((String)value);
						else if(value instanceof Long)
							value = new BigDecimal((Long)value);
						else if(value instanceof Integer)
							value = new BigDecimal((Integer)value);
						else if(value instanceof Float)
							value = new BigDecimal((Float)value);
						else if(value instanceof Double)
							value = new BigDecimal((Double)value);
				}
				
				objectMatchers.add(hasPropertyMatcher(name, value));
			}
			matchers.add(Matchers.allOf(objectMatchers));
		}
		return matchers;
	}
	
	/**/
	
	protected File testSourceDirectory(){
		File userDirectory = new File(System.getProperty("user.dir"));
    	File arquillianFile = new File(userDirectory,"/src/test/resources");
    	return arquillianFile;
	}
	
	protected File testSourceFile(String filename){
    	return new File(testSourceDirectory(),filename);
	}
	
	protected void updateXmlNode(String sourceFile,String destinationFile,Namespace namespace,String[][] valuePaths){
		File arquillianFile = testSourceFile(sourceFile);
		SAXBuilder builder = new SAXBuilder();
		Document document;
		try {
			document = builder.build(arquillianFile);
			Element element = null;
			for(String[] valuePath : valuePaths){
				element = document.getRootElement();
				for(int i=0;i<valuePath.length-1;i++)
					element = element.getChild(valuePath[i],namespace);
				
				element.setText(valuePath[valuePath.length-1]);
			}
			
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(document, new FileWriter(testSourceFile(destinationFile)));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**/
	protected void debug(Object object){
		System.out.println("------------------------------------- Debug -----------------------------");
		System.out.println(ToStringBuilder.reflectionToString(object, ToStringStyle.MULTI_LINE_STYLE));
	}
	
}
