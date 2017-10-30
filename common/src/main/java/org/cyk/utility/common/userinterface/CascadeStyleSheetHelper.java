package org.cyk.utility.common.userinterface;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Singleton;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.AbstractHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.StringHelper;

@Singleton
public class CascadeStyleSheetHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static CascadeStyleSheetHelper INSTANCE;
	
	public static CascadeStyleSheetHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new CascadeStyleSheetHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public String getClass(Object object,java.lang.reflect.Field field){
		Listener listener = ClassHelper.getInstance().instanciateOne(Listener.Adapter.Default.DEFAULT_CLASS);
		return listener.getClass(object, field);
	}
	
	public String getClass(Object object,String[] fieldNames){
		Listener listener = ClassHelper.getInstance().instanciateOne(Listener.Adapter.Default.DEFAULT_CLASS);
		return listener.getClass(object, fieldNames);
	}
	
	/**/
	
	public static interface Listener {
		
		String getClass(Object object,java.lang.reflect.Field field);
		String getClass(Object object,String[] fieldNames);
		
		public static class Adapter extends AbstractBean implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			@Override
			public String getClass(Object object,java.lang.reflect.Field field) {
				return null;
			}
			
			@Override
			public String getClass(Object object, String[] fieldNames) {
				return null;
			}
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@SuppressWarnings("unchecked")
				public static Class<Listener> DEFAULT_CLASS = (Class<Listener>) ClassHelper.getInstance().getByName(Default.class);
				
				@Override
				public String getClass(Object object,Field field) {
					return object.getClass().getSimpleName().toLowerCase()+Constant.CHARACTER_UNDESCORE+field.getName().toLowerCase();
				}
				
				@Override
				public String getClass(Object object, String[] fieldNames) {
					Collection<String> tokens = new ArrayList<>();
					tokens.add(object.getClass().getSimpleName().toLowerCase());
					for(String fieldName : fieldNames)
						tokens.add(fieldName.toLowerCase());
					return StringHelper.getInstance().concatenate(tokens, Constant.CHARACTER_UNDESCORE);
				}
				
			}
			
		}
		
	}
	
}
