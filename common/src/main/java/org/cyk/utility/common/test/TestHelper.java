package org.cyk.utility.common.test;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.AbstractHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.RandomHelper;

@Singleton
public class TestHelper extends AbstractHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	private static TestHelper INSTANCE;
	
	public static TestHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new TestHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	/**/
	
	public static class TestCase extends AbstractBean implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public <T> T instanciateOne(Class<T> aClass){
			return ClassHelper.getInstance().instanciateOne(aClass);
		}
		
		public <T> T instanciateOne(Class<T> aClass,Object identifier){
			return ClassHelper.getInstance().instanciateOne(aClass,identifier);
		}
		
		public <T> T instanciateOneWithRandomIdentifier(Class<T> aClass){
			Class<?> identifierClass = FieldHelper.getInstance().getType(aClass, ClassHelper.getInstance().getIdentifierFieldName(aClass));
			Object identifier = null;
			if(String.class.equals(identifierClass))
				identifier = getRandomHelper().getAlphabetic(5);
			return ClassHelper.getInstance().instanciateOne(aClass,identifier);
		}
		
		public TestCase computeChanges(Object instance) {
			InstanceHelper.getInstance().computeChanges(instance);
			return this;
		}
		
		/* shortcuts */
		
		public RandomHelper getRandomHelper(){
			return RandomHelper.getInstance();
		}
		
	}
	
}
