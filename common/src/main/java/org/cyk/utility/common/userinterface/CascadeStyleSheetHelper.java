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
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.StringHelper;

@Singleton
public class CascadeStyleSheetHelper extends AbstractHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	static {
		ClassHelper.getInstance().map(Listener.class, Listener.Adapter.Default.class,Boolean.FALSE);
	}
	
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
	
	public String getClass(Class<?>...classes){
		Listener listener = ClassHelper.getInstance().instanciateOne(Listener.class);
		return listener.getClass(classes);
	}
	
	public String getClass(String...strings){
		Listener listener = ClassHelper.getInstance().instanciateOne(Listener.class);
		return listener.getClass(strings);
	}
	
	public String getClass(Object object,java.lang.reflect.Field field){
		Listener listener = ClassHelper.getInstance().instanciateOne(Listener.class);
		return listener.getClass(object, field);
	}
	
	public String getClass(Object object,String[] fieldNames){
		Listener listener = ClassHelper.getInstance().instanciateOne(Listener.class);
		return listener.getClass(object, fieldNames);
	}
	
	/**/
	
	public static interface Listener {
		
		String getClassPrefix(Collection<String> strings);
		String getClass(String[] strings);
		String getClass(Object object,java.lang.reflect.Field field);
		String getClass(Object object,String[] fieldNames);
		String getClass(Class<?>[] classes);
		
		public static class Adapter extends AbstractBean implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				private static final String PREFIX = Constant.CYK.toLowerCase()+Constant.CHARACTER_UNDESCORE+"userinterface"+Constant.CHARACTER_UNDESCORE;
				
				@Override
				public String getClassPrefix(Collection<String> strings) {
					return PREFIX;
				}
				
				@Override
				public String getClass(String[] strings) {
					Collection<String> tokens = new ArrayList<>();
					for(String string : strings)
						if(StringHelper.getInstance().isNotBlank(string))
							tokens.add(string);
					return CollectionHelper.getInstance().isEmpty(tokens) ? Constant.EMPTY_STRING : getClassPrefix(tokens)+StringHelper.getInstance().concatenate(tokens, Constant.CHARACTER_UNDESCORE);
				}
				
				@Override
				public String getClass(Object object,Field field) {
					return getClass(object,new String[]{field.getName()});
				}
				
				@Override
				public String getClass(Object object, String[] fieldNames) {
					Collection<String> tokens = new ArrayList<>();
					tokens.add(object.getClass().getSimpleName().toLowerCase());
					for(String fieldName : fieldNames)
						tokens.add(fieldName.toLowerCase());
					return getClass(tokens.toArray(new String[]{}));
				}
				
				@Override
				public String getClass(Class<?>[] classes) {
					Collection<String> tokens = new ArrayList<>();
					for(Class<?> aClass : classes)
						tokens.add(aClass.getSimpleName().toLowerCase());
					return getClass(tokens.toArray(new String[]{}));
				}
			}
			
			@Override
			public String getClassPrefix(Collection<String> strings) {
				return null;
			}
			
			@Override
			public String getClass(String[] strings) {
				return null;
			}
			
			@Override
			public String getClass(Object object,java.lang.reflect.Field field) {
				return null;
			}
			
			@Override
			public String getClass(Object object, String[] fieldNames) {
				return null;
			}
			
			@Override
			public String getClass(Class<?>[] classes) {
				return null;
			}
		}
		
	}
	
}
