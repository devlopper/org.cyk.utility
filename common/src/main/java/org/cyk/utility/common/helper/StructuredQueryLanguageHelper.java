package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

import javax.inject.Singleton;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.ArrayUtils;
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
		
		Collection<TupleCollection> getTupleCollections();
		Builder setTupleCollections(Collection<TupleCollection> tupleCollections);
		Builder addTupleCollection(String name,String alias);
		
		String getFormat(Operator operator);
		
		Builder addExpressions(Operator operator,Collection<String> expressions);
		Builder addExpressions(Operator operator,String...expressions);
		
		Builder setSelect(String...expressions);
		Builder setSelectFrom(String...expressions);
		Builder setFrom(String...expressions);
		Builder setWhere(String...expressions);
		Builder addWhere(String expression);
		
		String getParameterFormat();
		Builder setParameterFormat(String format);
		
		@Getter @Setter
		public static class Adapter extends org.cyk.utility.common.Builder.NullableInput.Adapter.Default<String> implements Builder,Serializable{
			private static final long serialVersionUID = 1L;

			protected Collection<TupleCollection> tupleCollections;
			
			public Adapter() {
				super(String.class);
			}
			
			public Builder setTupleCollections(Collection<TupleCollection> tupleCollections){
				return null;
			}
			
			@Override
			public Builder addTupleCollection(String name, String alias) {
				return null;
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
			
			@Override
			public Builder setSelect(String...expressions) {
				return null;
			}
			
			@Override
			public Builder setSelectFrom(String...expressions) {
				return null;
			}
			
			@Override
			public Builder setFrom(String...expressions) {
				return null;
			}
			
			@Override
			public Builder setWhere(String...expressions) {
				return null;
			}
			
			@Override
			public Builder addWhere(String expression) {
				return null;
			}
			
			public static class Default extends Builder.Adapter implements Serializable{
				private static final long serialVersionUID = 1L;

				public static final String SELECT_FORMAT = "SELECT %s";
				public static final String FROM_FORMAT = "FROM %s";
				public static final String WHERE_FORMAT = "WHERE %s";
				public static final String ORDER_BY_FORMAT = "ORDER BY %s";
				
				@Override
				public Builder addTupleCollection(String name, String alias) {
					if(tupleCollections==null)
						tupleCollections = new LinkedHashSet<>();
					tupleCollections.add(new TupleCollection(name, alias));
					return this;
				}
				
				@Override
				public Builder addWhere(String expression) {
					
					return this;
				}
				
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
				public Builder setSelect(String...expressions) {
					return addExpressions(Operator.SELECT, expressions);
				}
				
				@Override
				public Builder setSelectFrom(String... expressions) {
					setSelect("r");
					return setFrom(ArrayUtils.addAll(expressions));
				}
				
				@Override
				public Builder setFrom(String... expressions) {
					return addExpressions(Operator.FROM, expressions);
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
					Collection<TupleCollection> tupleCollections = getTupleCollections();
					Collection<String> collection = new ArrayList<>();
					
					if(StringHelper.getInstance().isBlank(get(Operator.SELECT))){
						if(tupleCollections!=null){
							addExpressions(Operator.SELECT, CollectionHelper.getInstance().concatenate(InstanceHelper.getInstance()
								.callGetMethod(tupleCollections, String.class, "alias"), Constant.CHARACTER_COMA.toString()) );
						}
					}
					
					if(StringHelper.getInstance().isBlank(get(Operator.FROM))){
						if(tupleCollections!=null){
							addExpressions(Operator.FROM, CollectionHelper.getInstance().concatenate(InstanceHelper.getInstance().callGetMethod(tupleCollections, String.class, "nameSpaceAlias")
								, Constant.CHARACTER_COMA.toString()) );
						}
					}
					
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
		
		/**/
		 
		@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(of={TupleCollection.FIELD_ALIAS})
		public static class TupleCollection implements Serializable {	
			private static final long serialVersionUID = 1L;
			
			private String name;
			private String alias;
			
			public String getNameSpaceAlias(){
				return name+Constant.CHARACTER_SPACE+alias;
			}
			
			public static final String FIELD_NAME = "name";
			public static final String FIELD_ALIAS = "alias";
			
		}
	
	}
	
	public static interface Where extends StringHelper.Builder.Collection {
		
		Where addParameter(String parameter);
		
		public static class Adapter extends StringHelper.Builder.Collection.Adapter.Default implements Where , Serializable{
			private static final long serialVersionUID = 1L;

			@Override
			public Where addParameter(String parameter) {
				return null;
			}
			
			public static class Default extends Where.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
			
				@Override
				public Where addParameter(String parameter) {
					addTokens(parameter);
					return this;
				}
				
				public static class JavaPersistenceQueryLanguage extends Where.Adapter.Default implements Serializable{
					private static final long serialVersionUID = 1L;
					
					
				}
				
			}
		}
		
	}
}
