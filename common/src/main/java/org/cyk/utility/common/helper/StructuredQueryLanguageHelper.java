package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import javax.inject.Singleton;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.StringHelper.CaseType;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
		String formatParameter(String parameter);
		
		@Getter
		public static class Adapter extends org.cyk.utility.common.Builder.NullableInput.Adapter.Default<String> implements Builder,Serializable{
			private static final long serialVersionUID = 1L;

			protected Collection<TupleCollection> tupleCollections;
			protected String parameterFormat;
			
			public Adapter() {
				super(String.class);
			}
			
			@Override
			public Builder setParameterFormat(String parameterFormat){
				return null;
			}
			
			@Override
			public String formatParameter(String parameter) {
				return null;
			}
			
			@Override
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
				public String formatParameter(String parameter) {
					return String.format(getParameterFormat(), parameter);
				}
				
				@Override
				public Builder setParameterFormat(String parameterFormat) {
					this.parameterFormat = parameterFormat;
					return this;
				}
				
				@Override
				public Builder addWhere(String expression) {
					addExpressions(Operator.WHERE, expression);
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
					
					public static final String PARAMETER_FORMAT = ":%s";

					{
						setParameterFormat(PARAMETER_FORMAT);
					}
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
		
		/**/
		
		/*public static interface Parameter extends org.cyk.utility.common.Builder<java.lang.String, java.lang.String> {
			
			public static class Adapter extends org.cyk.utility.common.Builder.Adapter.Default<java.lang.String, java.lang.String> implements Builder.Parameter , Serializable{
				private static final long serialVersionUID = 1L;

				public Adapter(java.lang.String input) {
					super(java.lang.String.class, input, java.lang.String.class);
				}
				
				public static class Default extends Builder.Parameter.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
					public Default(java.lang.String input) {
						super(input);
					}
					
					public static class JavaPersistenceQueryLanguage extends Builder.Parameter.Adapter.Default implements Serializable{
						private static final long serialVersionUID = 1L;
							
					}
				}
			}
			
		}*/
	
	}
	
	public static interface Where extends StringHelper.Builder.Collection {
		
		StructuredQueryLanguageHelper.Builder getStructuredQueryLanguageBuilder();
		Where setStructuredQueryLanguageBuilder(StructuredQueryLanguageHelper.Builder builder);
		
		Where addBetween(String fieldName,String parameterSuffix);
		Where addBetween(String fieldName);
		
		Where addLessThanOrEqual(String fieldName,String parameter);
		Where addGreaterThanOrEqual(String fieldName,String parameter);
		
		@Override Where leftParathensis();
		@Override Where rightParathensis();
		@Override Where and();
		@Override Where or();
		@Override Where addTokens(String... tokens);
		
		@Getter
		public static class Adapter extends StringHelper.Builder.Collection.Adapter.Default implements Where , Serializable{
			private static final long serialVersionUID = 1L;

			protected StructuredQueryLanguageHelper.Builder structuredQueryLanguageBuilder;
			
			@Override
			public Where addGreaterThanOrEqual(String fieldName, String parameter) {
				return null;
			}
			
			@Override
			public Where addLessThanOrEqual(String fieldName, String parameter) {
				return null;
			}
			
			@Override
			public Where setStructuredQueryLanguageBuilder(Builder structuredQueryLanguageBuilder) {
				return null;
			}
			
			@Override
			public Where addBetween(String fieldName,String parameterSuffix) {
				return null;
			}
			
			@Override
			public Where addBetween(String fieldName) {
				return null;
			}
			
			@Override
			public Where leftParathensis() {
				return (Where) super.leftParathensis();
			}
			
			@Override
			public Where rightParathensis() {
				return (Where) super.rightParathensis();
			}
			
			@Override
			public Where and() {
				return (Where) addTokens(Constant.CHARACTER_SPACE.toString()+"AND"+Constant.CHARACTER_SPACE.toString());
			}
			
			@Override
			public Where or() {
				return (Where) addTokens(Constant.CHARACTER_SPACE.toString()+"OR"+Constant.CHARACTER_SPACE.toString());
			}
			
			@Override
			public Where addTokens(String... tokens) {
				return (Where) super.addTokens(tokens);
			}
			
			public static class Default extends Where.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
			
				public static class JavaPersistenceQueryLanguage extends Where.Adapter.Default implements Serializable{
					private static final long serialVersionUID = 1L;
						
					@Override
					public Where addBetween(String fieldName,String parameterSuffix) {
						addActions(new Between.Adapter.Default.JavaPersistenceQueryLanguage().setStructuredQueryLanguageBuilder(getStructuredQueryLanguageBuilder())
							.setProperty(Between.PROPERTY_NAME_FIELD_NAME, fieldName).setProperty(Between.PROPERTY_NAME_SUFFIX, parameterSuffix));
						return this;
					}
					
					@Override
					public Where addBetween(String fieldName) {
						return addBetween(fieldName, null);
					}
					
					@Override
					public Where setStructuredQueryLanguageBuilder(Builder structuredQueryLanguageBuilder) {
						this.structuredQueryLanguageBuilder = structuredQueryLanguageBuilder;
						return this;
					}
					
					@Override
					public Where addGreaterThanOrEqual(String fieldName, String parameter) {
						return addTokens(fieldName,">=",getStructuredQueryLanguageBuilder().formatParameter(parameter));
					}
					
					@Override
					public Where addLessThanOrEqual(String fieldName, String parameter) {
						return addTokens(fieldName,"<=",getStructuredQueryLanguageBuilder().formatParameter(parameter));
					}
				}
			}
		}
		
		/**/
		
		// field BETWEEN parameter1 AND parameter2
		public static interface Between extends StringHelper.Builder.Collection {
			
			StructuredQueryLanguageHelper.Builder getStructuredQueryLanguageBuilder();
			Between setStructuredQueryLanguageBuilder(StructuredQueryLanguageHelper.Builder builder);
			
			String PARAMETER_FROM_FORMAT = "from%s";
			String PARAMETER_TO_FORMAT = "to%s";
			
			@Getter
			public static class Adapter extends StringHelper.Builder.Collection.Adapter.Default implements Between , Serializable{
				private static final long serialVersionUID = 1L;

				protected StructuredQueryLanguageHelper.Builder structuredQueryLanguageBuilder;
				
				@Override
				public Between setStructuredQueryLanguageBuilder(Builder structuredQueryLanguageBuilder) {
					return null;
				}
				
				public static String getFromParameterName(String field){
					List<String> names =  FieldHelper.getInstance().getFieldNames(field);
					return String.format(PARAMETER_FROM_FORMAT,StringHelper.getInstance().applyCaseType(names.get(names.size()-1),CaseType.FU));
				}
				
				public static String getToParameterName(String field){
					List<String> names =  FieldHelper.getInstance().getFieldNames(field);
					return String.format(PARAMETER_TO_FORMAT,StringHelper.getInstance().applyCaseType(names.get(names.size()-1),CaseType.FU));
				}
				
				public static class Default extends Between.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
				
					@Override
					public Between setStructuredQueryLanguageBuilder(Builder structuredQueryLanguageBuilder) {
						this.structuredQueryLanguageBuilder = structuredQueryLanguageBuilder;
						return this;
					}
					
					@Override
					protected String __execute__() {
						String suffix = getPropertyAsString(PROPERTY_NAME_SUFFIX);
						String fieldName = getPropertyAsString(PROPERTY_NAME_FIELD_NAME);
						String parameter1 = getPropertyAsString(PROPERTY_NAME_PARAMETER_1);
						if(StringHelper.getInstance().isBlank(parameter1))
							parameter1 = structuredQueryLanguageBuilder.formatParameter(getFromParameterName(fieldName)+StringUtils.defaultString(suffix));
						String parameter2 = getPropertyAsString(PROPERTY_NAME_PARAMETER_2);
						if(StringHelper.getInstance().isBlank(parameter2))
							parameter2 = structuredQueryLanguageBuilder.formatParameter(getToParameterName(fieldName)+StringUtils.defaultString(suffix));
						addTokens(fieldName).space().addTokens("BETWEEN").space()
						.addTokens(parameter1).space().and().applyCaseToLastToken(CaseType.U).space().addTokens(parameter2);
						return super.__execute__();
					}
					
					public static class JavaPersistenceQueryLanguage extends Between.Adapter.Default implements Serializable{
						private static final long serialVersionUID = 1L;
							
					}
				}
			}
		}
		
	}
}
