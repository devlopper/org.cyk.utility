package org.cyk.utility.test.unit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.cyk.utility.test.AbstractTest;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractUnitTest extends AbstractTest  {
	
	private static final long serialVersionUID = -8873735551443449606L;
	
	protected Collection<Object> beans;
	
	@Before
	public void setup(){
		beans = new ArrayList<>();
		registerBeans(beans);
		for(Object object : beans){
			try {
				Method postConstruct = MethodUtils.getAccessibleMethod(object.getClass(), "postConstruct");
				if(postConstruct==null)
					continue;
				postConstruct.setAccessible(Boolean.TRUE);
				postConstruct.invoke(object);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void registerBeans(Collection<Object> collection){}

	/**/
	
	protected <T> List<T> getList(@SuppressWarnings("unchecked") T...elements){
		if(elements == null)
			return null;
		return Arrays.asList(elements);
	}
}
