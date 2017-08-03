package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.inject.Singleton;

import org.cyk.utility.common.Constant;

@Singleton
public class StructuredQueryLanguageHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String BETWEEN_FORMAT = "%s BETWEEN %s AND %s";
	
	private static StructuredQueryLanguageHelper INSTANCE;
	
	public static StructuredQueryLanguageHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new StructuredQueryLanguageHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public String getBetween(String field,Object from,Object to){
		return String.format(BETWEEN_FORMAT, field,from,to);
	}
	
	/**/
	
	public static interface Builder extends org.cyk.utility.common.Builder.NullableInput<String>{
		
		public static enum Operator{SELECT,FROM,WHERE,ORDER_BY}
		
		String getFormat(Operator operator);
		
		Builder addExpressions(Operator operator,Collection<String> expressions);
		Builder addExpressions(Operator operator,String...expressions);
		
		public static class Adapter extends org.cyk.utility.common.Builder.NullableInput.Adapter.Default<String> implements Builder,Serializable{
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(String.class);
			}
			
			@Override
			public String getFormat(Operator operator) {
				return null;
			}
			
			@Override
			public Builder addExpressions(Operator operator, Collection<String> expressions) {
				return null;
			}
			
			@Override
			public Builder addExpressions(Operator operator, String...expressions) {
				return null;
			}
			
			public static class Default extends Builder.Adapter implements Serializable{
				private static final long serialVersionUID = 1L;

				public static final String SELECT_FORMAT = "SELECT %s";
				public static final String FROM_FORMAT = "FROM %s";
				public static final String WHERE_FORMAT = "WHERE %s";
				public static final String ORDER_BY_FORMAT = "ORDER BY %s";
				
				@Override
				public String getFormat(Operator operator) {
					switch (operator) {
					case SELECT: return SELECT_FORMAT;
					case FROM: return FROM_FORMAT;
					case WHERE: return WHERE_FORMAT;
					case ORDER_BY: return ORDER_BY_FORMAT;
					}
					return null;
				}
				
				@Override
				public Builder addExpressions(Operator operator, Collection<String> expressions) {
					if(!CollectionHelper.getInstance().isEmpty(expressions)){
						@SuppressWarnings("unchecked")
						Collection<String> collection = (Collection<String>) getProperty(operator.name());
						if(collection==null)
							setProperty(operator.name(), collection = new ArrayList<>());
						collection.addAll(expressions);
					}
					return this;
				}
				
				@Override
				public Builder addExpressions(Operator operator, String... expressions) {
					if(!ArrayHelper.getInstance().isEmpty(expressions)){
						addExpressions(operator, Arrays.asList(expressions));
					}
					return this;
				}
				
				private String get(Operator operator) {
					@SuppressWarnings("unchecked")
					Collection<String> collection = (Collection<String>) getProperty(operator.name());
					String expressions = CollectionHelper.getInstance().concatenate(collection, Constant.CHARACTER_COMA.toString());
					return StringHelper.getInstance().isBlank(expressions) ? Constant.EMPTY_STRING : String.format(getFormat(operator), expressions);
				}
				
				@Override
				protected String __execute__() {
					Collection<String> collection = new ArrayList<>();
					for(Operator operator : new Operator[]{Operator.SELECT,Operator.FROM,Operator.WHERE,Operator.ORDER_BY}){
						String string = get(operator);
						if(!StringHelper.getInstance().isBlank(string))
							collection.add(string);
					}
					return CollectionHelper.getInstance().concatenate(collection, Constant.CHARACTER_SPACE.toString());
				}
				
				/**/
				
				public static class JavaPersistenceQueryLanguage extends Builder.Adapter.Default implements Serializable{
					private static final long serialVersionUID = 1L;

					
				}
				
			}
		}
		
	}
	
}
