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
import org.cyk.utility.common.Action;
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
	
	public static String getParameterName(String fieldName){
		return FieldHelper.getInstance().getLast(fieldName);
	}
	
	/**/
	
	public static interface Builder extends org.cyk.utility.common.Builder.NullableInput<String>{
		
		public static enum Operator{SELECT,FROM,WHERE,ORDER_BY}
		
		Collection<TupleCollection> getTupleCollections();
		Builder setTupleCollections(Collection<TupleCollection> tupleCollections);
		Builder addTupleCollection(String name,String alias);
		Builder addTupleCollection(Class<?> tupleClass,String alias);
		Builder addTupleCollection(Class<?> tupleClass);
		String getTupleCollectionName(Class<?> tupleClass);
		
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
		
		Where getWhere();
		Where getWhere(Boolean createIfNull);
		Where createWhere();
		Builder setWhere(Where where);
		Where where();
		
		String getFieldNamePrefix();
		Builder setFieldNamePrefix(String fieldNamePrefix);
		String formatFieldName(String fieldName);
		Builder setFieldName(String fieldName);
		
		@Getter
		public static class Adapter extends org.cyk.utility.common.Builder.NullableInput.Adapter.Default<String> implements Builder,Serializable{
			private static final long serialVersionUID = 1L;

			protected Collection<TupleCollection> tupleCollections;
			protected String parameterFormat,fieldNamePrefix;
			protected Where where;
			
			public Adapter() {
				super(String.class);
			}
			
			@Override
			public Builder addTupleCollection(Class<?> tupleClass, String alias) {
				return null;
			}
			
			@Override
			public Builder addTupleCollection(Class<?> tupleClass) {
				return null;
			}
			
			@Override
			public String getTupleCollectionName(Class<?> tupleClass) {
				return null;
			}
			
			@Override
			public String formatFieldName(String fieldName) {
				return null;
			}
			
			@Override
			public Builder setFieldName(String fieldName) {
				return null;
			}
			
			@Override
			public Builder setFieldNamePrefix(String fieldNamePrefix) {
				return null;
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
			
			@Override
			public Builder setWhere(Where where) {
				return null;
			}
			
			@Override
			public Where getWhere(Boolean createIfNull) {
				return null;
			}
			
			@Override
			public Where createWhere() {
				return null;
			}
			
			@Override
			public Where where() {
				return null;
			}
			
			public static class Default extends Builder.Adapter implements Serializable{
				private static final long serialVersionUID = 1L;

				public static final String SELECT_FORMAT = "SELECT %s";
				public static final String FROM_FORMAT = "FROM %s";
				public static final String WHERE_FORMAT = "WHERE %s";
				public static final String ORDER_BY_FORMAT = "ORDER BY %s";
				public static final String DEFAULT_TUPLE_COLLECTION_ALIAS = "t";
				
				public Default(String tupleCollection,String alias){
					addTupleCollection(tupleCollection, alias);
				}
				
				public Default(String tupleCollection){
					this(tupleCollection,DEFAULT_TUPLE_COLLECTION_ALIAS);
				}
				
				public Default(Class<?> tupleClass, String alias){
					addTupleCollection(tupleClass,alias);
				}
				
				public Default(Class<?> tupleClass){
					this(tupleClass,DEFAULT_TUPLE_COLLECTION_ALIAS);
				}
				
				@Override
				public Builder addTupleCollection(Class<?> tupleClass, String alias) {
					return addTupleCollection(getTupleCollectionName(tupleClass), alias);
				}
				
				@Override
				public Builder addTupleCollection(Class<?> tupleClass) {
					return addTupleCollection(tupleClass, DEFAULT_TUPLE_COLLECTION_ALIAS);
				}
				
				@Override
				public String getTupleCollectionName(Class<?> tupleClass) {
					String name = tupleClass.getSimpleName();
					return name;
				}
				
				@Override
				public Builder setFieldName(String fieldName) {
					setFieldNamePrefix(DEFAULT_TUPLE_COLLECTION_ALIAS+Constant.CHARACTER_DOT+fieldName+Constant.CHARACTER_DOT);
					return this;
				}
				
				@Override
				public String formatFieldName(String fieldName) {
					return StringUtils.defaultString(getFieldNamePrefix())+fieldName;
				}
				
				@Override
				public Builder setFieldNamePrefix(String fieldNamePrefix) {
					this.fieldNamePrefix = fieldNamePrefix;
					return this;
				}
				
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
				public Builder setWhere(Where where) {
					this.where = where;
					return this;
				}
				
				@Override
				public Where where() {
					return getWhere(Boolean.TRUE);
				}
				
				@Override
				protected String __execute__() {
					Collection<TupleCollection> tupleCollections = getTupleCollections();
					Collection<String> collection = new ArrayList<>();
					
					if(StringHelper.getInstance().isBlank(get(Operator.SELECT))){
						if(tupleCollections!=null){
							addExpressions(Operator.SELECT, CollectionHelper.getInstance().concatenate(MethodHelper.getInstance()
								.callGet(tupleCollections, String.class, "alias"), Constant.CHARACTER_COMA.toString()) );
						}
					}
					
					if(StringHelper.getInstance().isBlank(get(Operator.FROM))){
						if(tupleCollections!=null){
							addExpressions(Operator.FROM, CollectionHelper.getInstance().concatenate(MethodHelper.getInstance().callGet(tupleCollections, String.class, "nameSpaceAlias")
								, Constant.CHARACTER_COMA.toString()) );
						}
					}
					
					Where where = getWhere();
					if(StringHelper.getInstance().isBlank(get(Operator.WHERE))){
						if(where!=null){
							addExpressions(Operator.WHERE, where.execute());
						}
					}
					
					for(Operator operator : new Operator[]{Operator.SELECT,Operator.FROM,Operator.WHERE,Operator.ORDER_BY}){
						String string = get(operator);
						if(!StringHelper.getInstance().isBlank(string))
							collection.add(string);
					}
					return CollectionHelper.getInstance().concatenate(collection, Constant.CHARACTER_SPACE.toString());
				}
				
				@Override
				public Where getWhere(Boolean createIfNull) {
					if(where==null)
						if(Boolean.TRUE.equals(createIfNull)){
							where = createWhere();
							if(where!=null){
								where.setParent(this);
							}
						}
					setWhere(where);
					return where;
				}
				
				/**/
				
				public static class JavaPersistenceQueryLanguage extends Builder.Adapter.Default implements Serializable{
					private static final long serialVersionUID = 1L;
					
					public static final String PARAMETER_FORMAT = ":%s";

					{
						setParameterFormat(PARAMETER_FORMAT);
					}
					
					public JavaPersistenceQueryLanguage(String tupleCollection,String alias){
						super(tupleCollection, alias);
					}
					
					public JavaPersistenceQueryLanguage(String tupleCollection){
						super(tupleCollection);
					}
					
					public JavaPersistenceQueryLanguage(Class<?> tupleClass, String alias){
						super(tupleClass,alias);
					}
					
					public JavaPersistenceQueryLanguage(Class<?> tupleClass){
						super(tupleClass);
					}
					
					@Override
					public JavaPersistenceQueryLanguage addTupleCollection(String name, String alias) {
						return (JavaPersistenceQueryLanguage) super.addTupleCollection(name, alias);
					}
					
					@Override
					public Where createWhere() {
						return new Where.Adapter.Default.JavaPersistenceQueryLanguage();
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
		
		Where addBetween(String fieldName,String parameterSuffix);
		Where addBetween(String fieldName);
		Where bw(String fieldName);
		
		Where addLike(String fieldName);
		Where lk(String fieldName);
		
		Where addLessThanOrEqual(String fieldName,String parameter);
		Where lte(String fieldName,String parameter);
		Where addGreaterThanOrEqual(String fieldName,String parameter);
		Where gte(String fieldName,String parameter);
		
		@Override Where leftParathensis();
		Where lp();
		@Override Where rightParathensis();
		Where rp();
		@Override Where and();
		@Override Where or();
		@Override Where addTokens(String... tokens);
		
		@Override StructuredQueryLanguageHelper.Builder getParent();
		
		@Getter
		public static class Adapter extends StringHelper.Builder.Collection.Adapter.Default implements Where , Serializable{
			private static final long serialVersionUID = 1L;

			@Override
			public StructuredQueryLanguageHelper.Builder getParent() {
				return (StructuredQueryLanguageHelper.Builder) super.getParent();
			}
			
			@Override
			public Where addLike(String fieldName) {
				return null;
			}
			
			@Override
			public Where lk(String fieldName) {
				return null;
			}
			
			@Override
			public Where lte(String fieldName, String parameter) {
				return null;
			}
			
			@Override
			public Where gte(String fieldName, String parameter) {
				return null;
			}
			
			@Override
			public Where bw(String fieldName) {
				return null;
			}
			
			@Override
			public Where lp() {
				return null;
			}
			
			@Override
			public Where rp() {
				return null;
			}
			
			@Override
			public Where addGreaterThanOrEqual(String fieldName, String parameter) {
				return null;
			}
			
			@Override
			public Where addLessThanOrEqual(String fieldName, String parameter) {
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
				return (Where) super.and();
			}
			
			@Override
			public Where or() {
				return (Where) super.or();
			}
			
			@Override
			public Where addTokens(String... tokens) {
				return (Where) super.addTokens(tokens);
			}
			
			public static class Default extends Where.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
			
				@Override
				public Where lte(String fieldName, String parameter) {
					return addLessThanOrEqual(fieldName, parameter);
				}
				
				@Override
				public Where gte(String fieldName, String parameter) {
					return addGreaterThanOrEqual(fieldName, parameter);
				}
				
				@Override
				public Where bw(String fieldName) {
					return addBetween(fieldName);
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
				public Where lp() {
					return leftParathensis();
				}
				
				@Override
				public Where rp() {
					return rightParathensis();
				}
				
				@Override
				public Where addGreaterThanOrEqual(String fieldName, String parameter) {
					return addTokens(getParent().formatFieldName(fieldName),">=", getParent().formatParameter(parameter));
				}
				
				@Override
				public Where addLessThanOrEqual(String fieldName, String parameter) {
					return addTokens(getParent().formatFieldName(fieldName),"<=",getParent().formatParameter(parameter));
				}
				
				@Override
				public Where lk(String fieldName) {
					return addLike(fieldName);
				}
				
				public static class JavaPersistenceQueryLanguage extends Where.Adapter.Default implements Serializable{
					private static final long serialVersionUID = 1L;
					
					@Override
					public Where addBetween(String fieldName,String parameterSuffix) {
						addActions(new Between.Adapter.Default.JavaPersistenceQueryLanguage().setParent(this)
							.setProperty(Between.PROPERTY_NAME_FIELD_NAME, getParent().formatFieldName(fieldName)).setProperty(Between.PROPERTY_NAME_SUFFIX, parameterSuffix));
						return this;
					}
					
					@Override
					public Where addBetween(String fieldName) {
						return addBetween(fieldName, null);
					}
					
					@Override
					public Where addLike(String fieldName) {
						addActions(new Like.Adapter.Default.JavaPersistenceQueryLanguage().setParent(this)
								.setProperty(Between.PROPERTY_NAME_FIELD_NAME, getParent().formatFieldName(fieldName)));
						return this;
					}
					
				}
			}
		}
		
		/**/
		
		// field BETWEEN parameter1 AND parameter2
		public static interface Between extends StringHelper.Builder.Collection {
			
			String PARAMETER_FROM_FORMAT = "from%s";
			String PARAMETER_TO_FORMAT = "to%s";
			
			@Override Where getParent();
			@Override Between setParent(Action<Object, String> parent);
			
			@Getter
			public static class Adapter extends StringHelper.Builder.Collection.Adapter.Default implements Between , Serializable{
				private static final long serialVersionUID = 1L;

				@Override
				public Between setParent(Action<Object, String> parent) {
					return (Between) super.setParent(parent);
				}
				
				@Override
				public Where getParent() {
					return (Where) super.getParent();
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
					protected String __execute__() {
						String suffix = getPropertyAsString(PROPERTY_NAME_SUFFIX);
						String fieldName = getPropertyAsString(PROPERTY_NAME_FIELD_NAME);
						String parameter1 = getPropertyAsString(PROPERTY_NAME_PARAMETER_1);
						if(StringHelper.getInstance().isBlank(parameter1))
							parameter1 = getParent().getParent().formatParameter(getFromParameterName(fieldName)+StringUtils.defaultString(suffix));
						String parameter2 = getPropertyAsString(PROPERTY_NAME_PARAMETER_2);
						if(StringHelper.getInstance().isBlank(parameter2))
							parameter2 = getParent().getParent().formatParameter(getToParameterName(fieldName)+StringUtils.defaultString(suffix));
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
		
		//((field IS NULL ) AND (string_length = 0)) OR ((field IS NOT NULL ) AND (LOWER(field) LIKE LOWER(string)))
		public static interface Like extends StringHelper.Builder.Collection {
			
			@Override Where getParent();
			@Override Like setParent(Action<Object, String> parent);
			
			@Getter
			public static class Adapter extends StringHelper.Builder.Collection.Adapter.Default implements Like , Serializable{
				private static final long serialVersionUID = 1L;

				@Override
				public Like setParent(Action<Object, String> parent) {
					return (Like) super.setParent(parent);
				}
				
				@Override
				public Where getParent() {
					return (Where) super.getParent();
				}
				/*
				public static String get(String field,String parameter,String length){
					return String.format(FORMAT, field,parameter,length);
				}
				
				public static String get(String field,String parameter){
					return get(field,parameter,parameter+"Length");
				}
				*/
				public static String getParameterNameString(String fieldName){
					return getParameterName(fieldName)+"String";
				}
				
				public static String getParameterNameLike(String fieldName){
					return getParameterName(fieldName)+"Like";
				}
				
				public static class Default extends Like.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
							
					public static class JavaPersistenceQueryLanguage extends Like.Adapter.Default implements Serializable{
						private static final long serialVersionUID = 1L;
					
						//private static final String FORMAT = "(((%1$s IS NULL ) AND (%3$s = 0)) OR ((%1$s IS NOT NULL ) AND (LOWER(%1$s) LIKE LOWER(%2$s))))";
						private static final String FORMAT = "(((%1$s IS NULL ) AND (LENGTH(%3$s) = 0)) OR ((%1$s IS NOT NULL ) AND (LOWER(%1$s) LIKE LOWER(%2$s))))";
						
						@Override
						protected String __execute__() {
							String fieldName = getPropertyAsString(PROPERTY_NAME_FIELD_NAME);
							String string = getParent().getParent().formatParameter(getParameterNameString(fieldName));
							String like = getParent().getParent().formatParameter(getParameterNameLike(fieldName));
							String s =  String.format(FORMAT, fieldName,like,string);
							addTokens(s);
							return super.__execute__();
						}
						
					}
				}
			}
		}
		
	}
}
