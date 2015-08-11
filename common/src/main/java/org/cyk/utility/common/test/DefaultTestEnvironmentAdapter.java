package org.cyk.utility.common.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

public class DefaultTestEnvironmentAdapter extends TestEnvironmentAdapter {
	
	@Override
	public void assertBigDecimalEquals(String message, BigDecimal expected, BigDecimal actual) {
		assertEquals(message, formatBigDecimal(expected),formatBigDecimal(actual));
	}
	
	@Override
	public String formatBigDecimal(BigDecimal value) {
		return value.toString();
	}
	
	/**/
	
	/* Hamcrest Short cuts*/
	
	@Override
	public void assertThat(String reason,Boolean assertion){
		MatcherAssert.assertThat(reason, assertion);
	}
	
	@Override
	public <T> void assertThat(T actual,Matcher<? super T> matcher){
		MatcherAssert.assertThat(actual, matcher);
	}
	
	@Override
	public <T> void assertThat(String reason,T actual,Matcher<? super T> matcher){
		MatcherAssert.assertThat(reason,actual, matcher);
	}
	
	@Override
	public void hasProperty(Object object,String name,Object value){
		assertThat(object, hasPropertyMatcher(name, value));
	}
	
	@Override
	public void hasProperties(Object object,Object...entries){
		for(int i=0;i<entries.length;i=i+2)
			hasProperty(object, (String) entries[i], entries[i+1]);
	}
	
	@Override
	public <T> void contains(Class<T> aClass,Collection<T> list,Object[] names,Object[][] values){
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
	
}