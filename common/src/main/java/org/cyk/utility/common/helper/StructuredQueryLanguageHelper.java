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
import org.cyk.utility.common.Comparator;
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
	
	public Builder getBuilder(String tupleCollection, String alias){
		return ClassHelper.getInstance().instanciate(InstanceHelper.getInstance().getIfNotNullElseDefault(Builder.Adapter.Default.DEFAULT_CLASS
				, Builder.Adapter.Default.JavaPersistenceQueryLanguage.class),new Object[]{String.class,tupleCollection,String.class,alias});
	}
	
	public Builder getBuilder(String tupleCollection){
		return getBuilder(tupleCollection, Builder.Adapter.Default.DEFAULT_TUPLE_COLLECTION_ALIAS);
	}
	
	public Builder getBuilder(Class<?> tupleClass, String alias){
		return getBuilder(getTupleCollectionName(tupleClass), alias);
	}
	
	public Builder getBuilder(Class<?> tupleClass){
		return getBuilder(tupleClass, Builder.Adapter.Default.DEFAULT_TUPLE_COLLECTION_ALIAS);
	}
	
	public String getTupleCollectionName(Class<?> tupleClass){
		return tupleClass.getSimpleName();
	}
	
	public String getBetween(String field,Object from,Object to){
		return String.format(BETWEEN_FORMAT, field,from,to);
	}
	
	public String getParameterName(String fieldName,Boolean last){
		StringBuilder stringBuilder = new StringBuilder();
		if(last == null || Boolean.TRUE.equals(last))
			stringBuilder.append(FieldHelper.getInstance().getLast(fieldName));
		else
			stringBuilder.append(FieldHelper.getInstance().getVariableNameFromNames(fieldName));
		return stringBuilder.toString();
	}
	
	public String getParameterName(String fieldName){
		return getParameterName(fieldName,Boolean.TRUE);
	}
	
	/**/
	
	public static interface Builder extends org.cyk.utility.common.Builder.NullableInput<String>{
		
		public static enum Operator{SELECT,FROM,WHERE,ORDER_BY}
		
		@Override Builder getParent();
		@Override Builder setParent(Action<Object, String> parent);
		
		Builder createBuilder(String tupleCollection, String alias,String joinMasterFieldName,String joinChildFieldName);
		Builder createBuilder(Class<?> tupleClass, String alias,String joinMasterFieldName,String joinChildFieldName);
		
		Builder addToParentWhereTokens();
		
		Collection<TupleCollection> getTupleCollections();
		Builder setTupleCollections(Collection<TupleCollection> tupleCollections);
		Builder addTupleCollection(String name,String alias);
		Builder addTupleCollection(Class<?> tupleClass,String alias);
		Builder addTupleCollection(Class<?> tupleClass);
		TupleCollection getTupleCollection(Class<?> aClass);
		TupleCollection getTupleCollection(String name);
		String getTupleCollectionName(Class<?> tupleClass);
		
		String getFormat(Operator operator);
		
		Builder addExpressions(Operator operator,Collection<String> expressions);
		Builder addExpressions(Operator operator,String...expressions);
		
		Builder setSelect(String...expressions);
		Builder setSelectFrom(String...expressions);
		Builder setFrom(String...expressions);
		Builder setWhere(String...expressions);
		Builder addWhere(String expression);
		Builder setOrderBy(String...expressions);
		Builder addOrderBy(String expression);
		
		String getParameterFormat();
		Builder setParameterFormat(String format);
		String formatParameter(String parameter);
		
		Where getWhere();
		Where getWhere(Boolean createIfNull);
		Where createWhere();
		Builder setWhere(Where where);
		Where where();
		
		OrderBy getOrderBy();
		OrderBy getOrderBy(Boolean createIfNull);
		OrderBy createOrderBy();
		Builder setOrderBy(OrderBy orderBy);
		OrderBy orderBy();
		
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
			protected OrderBy orderBy;
			
			public Adapter() {
				super(String.class);
			}
			
			@Override
			public Builder getParent() {
				return (Builder) super.getParent();
			}
			
			@Override
			public Builder setParent(Action<Object, String> parent) {
				return (Builder) super.setParent(parent);
			}
			
			@Override
			public Builder createBuilder(String tupleCollection, String alias,String joinMasterFieldName,String joinChildFieldName) {
				return null;
			}
			
			@Override
			public Builder createBuilder(Class<?> tupleClass, String alias,String joinMasterFieldName,String joinChildFieldName) {
				return null;
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
			public TupleCollection getTupleCollection(Class<?> aClass) {
				return null;
			}
			
			@Override
			public TupleCollection getTupleCollection(String name) {
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
			
			@Override
			public Builder setOrderBy(String...expressions) {
				return null;
			}
			
			@Override
			public Builder addOrderBy(String expression) {
				return null;
			}
			
			@Override
			public Builder setOrderBy(OrderBy where) {
				return null;
			}
			
			@Override
			public OrderBy getOrderBy(Boolean createIfNull) {
				return null;
			}
			
			@Override
			public OrderBy createOrderBy() {
				return null;
			}
			
			@Override
			public OrderBy orderBy() {
				return null;
			}
			
			@Override
			public Builder addToParentWhereTokens() {
				return null;
			}
			
			public static class Default extends Builder.Adapter implements Serializable{
				private static final long serialVersionUID = 1L;

				@SuppressWarnings("unchecked")
				public static Class<? extends Builder> DEFAULT_CLASS = (Class<? extends Builder>) ClassHelper.getInstance().getByName(JavaPersistenceQueryLanguage.class);
				
				public static final String SELECT_FORMAT = "SELECT %s";
				public static final String FROM_FORMAT = "FROM %s";
				public static final String WHERE_FORMAT = "WHERE %s";
				public static final String ORDER_BY_FORMAT = "ORDER BY %s";
				public static final String DEFAULT_TUPLE_COLLECTION_ALIAS = "t";
				
				public Default(String tupleCollection,String alias){
					__constructor__(tupleCollection, alias);
				}
				
				public Default(String tupleCollection){
					this(tupleCollection,DEFAULT_TUPLE_COLLECTION_ALIAS);
				}
				
				public Default(Class<?> tupleClass, String alias){
					__constructor__(getTupleCollectionName(tupleClass),alias);
				}
				
				public Default(Class<?> tupleClass){
					this(tupleClass,DEFAULT_TUPLE_COLLECTION_ALIAS);
				}
				
				@Override
				public Builder addTupleCollection(Class<?> tupleClass, String alias) {
					return addTupleCollection(getTupleCollectionName(tupleClass), alias);
				}
				
				private void __constructor__(String tupleCollection,String alias){
					addTupleCollection(tupleCollection, alias);
					setFieldName(null);
				}
				
				@Override
				public Builder addTupleCollection(Class<?> tupleClass) {
					return addTupleCollection(tupleClass, DEFAULT_TUPLE_COLLECTION_ALIAS);
				}
				
				@Override
				public TupleCollection getTupleCollection(Class<?> aClass) {
					return getTupleCollection(getTupleCollectionName(aClass));
				}
				
				@Override
				public TupleCollection getTupleCollection(String name) {
					Collection<TupleCollection> tupleCollections = getTupleCollections();
					if(CollectionHelper.getInstance().isNotEmpty(tupleCollections))
						for(TupleCollection tupleCollection : tupleCollections)
							if(tupleCollection.getName().equals(name))
								return tupleCollection;
					return null;
				}
				
				@Override
				public String getTupleCollectionName(Class<?> tupleClass) {
					String name = tupleClass.getSimpleName();
					return name;
				}
				
				@Override
				public Builder createBuilder(String tupleCollection, String alias,String joinMasterFieldName,String joinChildFieldName) {
					Builder builder = getInstance().getBuilder(tupleCollection, StringUtils.defaultIfBlank(alias, DEFAULT_TUPLE_COLLECTION_ALIAS));
					builder.setParent(this);
					builder.where().join(joinMasterFieldName, joinChildFieldName).and();
					return builder;
				}
							
				@Override
				public Builder createBuilder(Class<?> tupleClass, String alias,String joinMasterFieldName,String joinChildFieldName) {
					return createBuilder(getInstance().getTupleCollectionName(tupleClass), alias,joinMasterFieldName,joinChildFieldName);
				}
				
				@Override
				public Builder addToParentWhereTokens() {
					getParent().where().addTokens(execute());
					return this;
				}
				
				@Override
				public Builder setFieldName(String fieldName) {
					TupleCollection tupleCollection = CollectionHelper.getInstance().getFirst(getTupleCollections());
					String alias = tupleCollection == null ? DEFAULT_TUPLE_COLLECTION_ALIAS : tupleCollection.getAlias();
					if(StringHelper.getInstance().isBlank(fieldName))
						setFieldNamePrefix(alias+Constant.CHARACTER_DOT);
					else
						setFieldNamePrefix(alias+Constant.CHARACTER_DOT+fieldName+Constant.CHARACTER_DOT);
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
					tupleCollections.add(new TupleCollection(name, StringUtils.defaultIfBlank(alias, DEFAULT_TUPLE_COLLECTION_ALIAS)));
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
				
				@Override
				public Builder setOrderBy(OrderBy orderBy) {
					this.orderBy = orderBy;
					return this;
				}
				
				@Override
				public OrderBy orderBy() {
					return getOrderBy(Boolean.TRUE);
				}
				
				@Override
				public OrderBy getOrderBy(Boolean createIfNull) {
					if(orderBy==null)
						if(Boolean.TRUE.equals(createIfNull)){
							orderBy = createOrderBy();
							if(orderBy!=null){
								orderBy.setParent(this);
							}
						}
					setOrderBy(orderBy);
					return orderBy;
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
					
					OrderBy orderBy = getOrderBy();
					if(StringHelper.getInstance().isBlank(get(Operator.ORDER_BY))){
						if(orderBy!=null){
							addExpressions(Operator.ORDER_BY, orderBy.execute());
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
					
					@Override
					public OrderBy createOrderBy() {
						return new OrderBy.Adapter.Default.JavaPersistenceQueryLanguage();
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
	}
	
	public static interface Where extends StringHelper.Builder.Collection {
		
		Where addJoin(String tupleCollection1FieldName,String tupleCollection2FieldName);
		Where join(String tupleCollection1FieldName,String tupleCollection2FieldName);
		
		Where addBetween(String fieldName,String parameterSuffix);
		Where addBetween(String fieldName);
		Where bw(String fieldName);
		
		Where addExists(String query,Boolean not);
		Where addExists(String query);
		Where exists(String query,Boolean not);
		Where exists(String query);
		Where exists(Builder queryBuilder,Boolean not);
		Where exists(Builder queryBuilder);
		
		Where addLike(String fieldName);
		Where lk(String fieldName);
		
		Where addIn(String fieldName,Boolean not);
		Where addIn(String fieldName);
		Where in(String fieldName,Boolean not);
		Where in(String fieldName);
		Where notIn(String fieldName);
		
		Where addEqual(String fieldName,Boolean not);
		Where addEqual(String fieldName);
		/*Where equal(String fieldName,Boolean not);
		Where equal(String fieldName);
		Where notEqual(String fieldName);
		*/
		Where addLessThanOrEqual(String fieldName,String parameter);
		Where lte(String fieldName,String parameter);
		Where addLessThan(String fieldName,String parameter);
		Where lt(String fieldName,String parameter);
		Where addGreaterThanOrEqual(String fieldName,String parameter);
		Where gte(String fieldName,String parameter);
		Where addGreaterThan(String fieldName,String parameter);
		Where gt(String fieldName,String parameter);
		Where addEqual(String fieldName,String parameter);
		Where eq(String fieldName,String parameter);
		Where eq(String fieldName);
		Where addNotEqual(String fieldName,String parameter);
		Where neq(String fieldName,String parameter);
		
		@Override Where leftParathensis();
		Where lp();
		@Override Where rightParathensis();
		Where rp();
		@Override Where and();
		@Override Where or();
		@Override Where equal();
		@Override Where space();
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
			public Where addJoin(String tupleCollection1FieldName, String tupleCollection2FieldName) {
				return null;
			}
			
			@Override
			public Where join(String tupleCollection1FieldName, String tupleCollection2FieldName) {
				return null;
			}
			
			@Override
			public Where addExists(String query, Boolean not) {
				return null;
			}
			
			@Override
			public Where addExists(String query) {
				return addExists(query, Boolean.FALSE);
			}
			
			@Override
			public Where exists(String query, Boolean not) {
				return addExists(query, not);
			}
			
			@Override
			public Where exists(String query) {
				return exists(query, Boolean.FALSE);
			}
			
			@Override
			public Where exists(Builder queryBuilder, Boolean not) {
				return exists(queryBuilder.execute(), not);
			}
			
			@Override
			public Where exists(Builder queryBuilder) {
				return exists(queryBuilder,Boolean.FALSE);
			}
			
			@Override
			public Where addIn(String fieldName, Boolean not) {
				return null;
			}
			
			@Override
			public Where addIn(String fieldName) {
				return addIn(fieldName, Boolean.FALSE);
			}
			
			@Override
			public Where in(String fieldName, Boolean not) {
				return addIn(fieldName, not);
			}
			
			@Override
			public Where in(String fieldName) {
				return in(fieldName, Boolean.FALSE);
			}
			
			@Override
			public Where notIn(String fieldName) {
				return in(fieldName, Boolean.TRUE);
			}
			
			public Where addEqual(String fieldName, Boolean not) {
				return null;
			}
			
			@Override
			public Where addEqual(String fieldName) {
				return addEqual(fieldName, (Boolean)null);
			}
			/*
			@Override
			public Where equal(String fieldName, Boolean not) {
				return addEqual(fieldName, not);
			}
			
			@Override
			public Where equal(String fieldName) {
				return equal(fieldName, null);
			}
			
			@Override
			public Where notEqual(String fieldName) {
				return equal(fieldName, Boolean.TRUE);
			}
			*/
			
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
			public Where lt(String fieldName, String parameter) {
				return null;
			}
			
			@Override
			public Where gte(String fieldName, String parameter) {
				return null;
			}
			
			@Override
			public Where gt(String fieldName, String parameter) {
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
			public Where addLessThan(String fieldName, String parameter) {
				return null;
			}
			
			@Override
			public Where addGreaterThan(String fieldName, String parameter) {
				return null;
			}
			
			@Override
			public Where addEqual(String fieldName, String parameter) {
				return null;
			}
			
			@Override
			public Where eq(String fieldName, String parameter) {
				return null;
			}
			
			@Override
			public Where eq(String fieldName) {
				return null;
			}
			
			@Override
			public Where addNotEqual(String fieldName, String parameter) {
				return null;
			}
			
			@Override
			public Where neq(String fieldName, String parameter) {
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
			public Where space() {
				return (Where) super.space();
			}
			
			@Override
			public Where equal() {
				return (Where) super.equal();
			}
			
			@Override
			public Where addTokens(String... tokens) {
				return (Where) super.addTokens(tokens);
			}
			
			public static class Default extends Where.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
			
				@Override
				public Where addJoin(String tupleCollection1FieldName, String tupleCollection2FieldName) {
					tupleCollection1FieldName = getParent().getParent().formatFieldName(tupleCollection1FieldName); //getTupleCollections().iterator().next().getAlias()+"."+tupleCollection1FieldName;
					tupleCollection2FieldName = getParent().formatFieldName(tupleCollection2FieldName); //getTupleCollections().iterator().next().getAlias()+"."+tupleCollection2FieldName;
					addTokens(tupleCollection1FieldName,"=",tupleCollection2FieldName);
					return this;
				}
				
				@Override
				public Where join(String tupleCollection1FieldName, String tupleCollection2FieldName) {
					return addJoin(tupleCollection1FieldName, tupleCollection2FieldName);
				}
				
				@Override
				public Where lte(String fieldName, String parameter) {
					return addLessThanOrEqual(fieldName, parameter);
				}
				
				@Override
				public Where gte(String fieldName, String parameter) {
					return addGreaterThanOrEqual(fieldName, parameter);
				}
				
				@Override
				public Where lt(String fieldName, String parameter) {
					return addLessThan(fieldName, parameter);
				}
				
				@Override
				public Where gt(String fieldName, String parameter) {
					return addGreaterThan(fieldName, parameter);
				}
				
				@Override
				public Where eq(String fieldName, String parameter) {
					return addEqual(fieldName, parameter);
				}
				
				@Override
				public Where eq(String fieldName) {
					return eq(fieldName,fieldName);
				}
				
				@Override
				public Where neq(String fieldName, String parameter) {
					return addNotEqual(fieldName, parameter);
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
				public Where addGreaterThan(String fieldName, String parameter) {
					return addTokens(getParent().formatFieldName(fieldName),">", getParent().formatParameter(parameter));
				}
				
				@Override
				public Where addLessThan(String fieldName, String parameter) {
					return addTokens(getParent().formatFieldName(fieldName),"<",getParent().formatParameter(parameter));
				}
				
				@Override
				public Where addEqual(String fieldName, String parameter) {
					return addTokens(getParent().formatFieldName(fieldName),"=",getParent().formatParameter(parameter));
				}
				
				@Override
				public Where addNotEqual(String fieldName, String parameter) {
					return addTokens(getParent().formatFieldName(fieldName),"<>",getParent().formatParameter(parameter));
				}
				
				@Override
				public Where lk(String fieldName) {
					return addLike(fieldName);
				}
				
				@Override
				public Where in(String fieldName,Boolean not) {
					return addIn(fieldName,not);
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
					
					@Override
					public Where addEqual(String fieldName, Boolean not) {
						addActions(new Equal.Adapter.Default.JavaPersistenceQueryLanguage().setParent(this)
								.setProperty(Between.PROPERTY_NAME_FIELD_NAME, getParent().formatFieldName(fieldName))
								.setProperty(Equal.PROPERTY_NAME_NOT, not));
						return this;
					}
					
					@Override
					public Where addIn(String fieldName, Boolean not) {
						addActions(new In.Adapter.Default.JavaPersistenceQueryLanguage().setParent(this)
								.setProperty(Between.PROPERTY_NAME_FIELD_NAME, getParent().formatFieldName(fieldName)).setProperty(PROPERTY_NAME_NOT, not));
						return this;
					}
					
					@Override
					public Where addExists(String query, Boolean not) {
						addActions(new Exists.Adapter.Default.JavaPersistenceQueryLanguage().setParent(this)
								.setProperty(Between.PROPERTY_NAME_QUERY, query).setProperty(PROPERTY_NAME_NOT, not));
						return this;
					}
					
					@Override
					public Where addNotEqual(String fieldName, String parameter) {
						return addTokens(getParent().formatFieldName(fieldName),"!=",getParent().formatParameter(parameter));
					}
				}
			}
		}
		
		/**/
		
		public static interface Operator extends StringHelper.Builder.Collection {
			
			java.util.Collection<String> getParameterNames();
			Operator setParameterNames(java.util.Collection<String> parameterNames);
			Operator addParameterNames(java.util.Collection<String> parameterNames);
			Operator addParameterNames(String...parameterNames);
			
			@Override Where getParent();
			@Override Operator setParent(Action<Object, String> parent);
			
			@Getter
			public static class Adapter extends StringHelper.Builder.Collection.Adapter.Default implements Operator , Serializable{
				private static final long serialVersionUID = 1L;

				protected java.util.Collection<String> parameterNames;
				
				@Override
				public Operator setParent(Action<Object, String> parent) {
					return (Operator) super.setParent(parent);
				}
				
				@Override
				public Where getParent() {
					return (Where) super.getParent();
				}
				
				@Override
				public Operator setParameterNames(java.util.Collection<String> parameterNames) {
					return null;
				}
				
				@Override
				public Operator addParameterNames(java.util.Collection<String> parameterNames) {
					return null;
				}
				
				@Override
				public Operator addParameterNames(String...parameterNames) {
					return null;
				}
				
				public static class Default extends Operator.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
					@Override
					protected String __execute__() {
						Boolean not = getPropertyAsBoolean(PROPERTY_NAME_NOT);
						String format = not == null ? getFormatNotIsNull() : not ? getFormatNotIsTrue() : getFormatNotIsFalse();
						addTokens(String.format(format, getFormatParameters(format,not)));
						return super.__execute__();
					}
					
					protected String getFormatNotIsFalse(){
						return null;
					}
					
					protected String getFormatNotIsTrue(){
						return null;
					}
					
					protected String getFormatNotIsNull(){
						return getFormatNotIsFalse();
					}
					
					protected Object[] getFormatParameters(String format,Boolean not){
						return null;
					}
					
					@Override
					public Operator setParameterNames(java.util.Collection<String> parameterNames) {
						this.parameterNames = parameterNames;
						return this;
					}
					
					@Override
					public Operator addParameterNames(java.util.Collection<String> parameterNames) {
						if(CollectionHelper.getInstance().isNotEmpty(parameterNames)) {
							if(this.parameterNames == null)
								this.parameterNames = new LinkedHashSet<>();
							this.parameterNames.addAll(parameterNames);
						}
						return this;
					}
					
					@Override
					public Operator addParameterNames(String...parameterNames) {
						if(ArrayHelper.getInstance().isNotEmpty(parameterNames))
							addParameterNames(Arrays.asList(parameterNames));
						return this;
					}
					
					protected String buildFromParameterNames(final String field,final String conjunction,Integer index) {
						final java.util.Collection<String> collection = new ArrayList<>();
						if(CollectionHelper.getInstance().isNotEmpty(this.parameterNames)) {
							for(Integer i = 0 ; i < this.parameterNames.size() ; i++)
								collection.add("("+field+" = %"+(index++)+"$s)");
						}
						return CollectionHelper.getInstance().concatenate(collection, " OR ");
					}
				}
			}
		}
		
		//((field IS NULL ) AND (param == NULL)) OR ((field IS NOT NULL ) AND (field == param))
		public static interface Equal extends Operator {
			
			@Override Equal setParent(Action<Object, String> parent);
			
			@Getter
			public static class Adapter extends Operator.Adapter.Default implements Equal , Serializable{
				private static final long serialVersionUID = 1L;

				@Override
				public Equal setParent(Action<Object, String> parent) {
					return (Equal) super.setParent(parent);
				}
				
				public static String getParameterNameEqual(String fieldName){
					return getInstance().getParameterName(fieldName)+"Equal";
				}
				
				public static class Default extends Equal.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
							
					public static class JavaPersistenceQueryLanguage extends Equal.Adapter.Default implements Serializable{
						private static final long serialVersionUID = 1L;
					
						//private static final String FORMAT = "(((%1$s IS NULL) AND (%2$s = NULL)) OR ((%1$s IS NOT NULL) AND (%1$s = %2$s)))";
						private static final String FORMAT = "(((%1$s IS NULL) AND (%2$s = NULL)) OR ((%1$s IS NOT NULL) AND ((%1$s = %3$s) OR (%1$s = %4$s))))";
						private static final String FORMAT_NOT = "(((%1$s IS NOT NULL) AND (%2$s = NULL)) OR ((%1$s IS NULL OR %1$s <> %2$s) AND (%2$s <> NULL)))";
						private static final String FORMAT_NOT_IS_NULL = "((%1$s IS NULL) OR (%1$s IS NOT NULL))";
						
						@Override
						protected String getFormatNotIsFalse() {
							return FORMAT;
						}
						
						@Override
						protected String getFormatNotIsTrue() {
							return FORMAT_NOT;
						}
						
						@Override
						protected String getFormatNotIsNull() {
							return FORMAT_NOT_IS_NULL;
						}
						
						@Override
						protected Object[] getFormatParameters(String format,Boolean not) {
							String fieldName = getPropertyAsString(PROPERTY_NAME_FIELD_NAME);
							String parameter = getParent().getParent().formatParameter(getParameterNameEqual(fieldName));
							return new Object[]{fieldName,parameter};
						}
						
					}
				}
			}
			
			/**/
		
		}
		
		// field BETWEEN parameter1 AND parameter2
		public static interface Between extends Operator {
			
			String PARAMETER_FROM_FORMAT = "from%s";
			String PARAMETER_TO_FORMAT = "to%s";
			
			@Override Between setParent(Action<Object, String> parent);
			
			@Getter
			public static class Adapter extends Operator.Adapter.Default implements Between , Serializable{
				private static final long serialVersionUID = 1L;

				@Override
				public Between setParent(Action<Object, String> parent) {
					return (Between) super.setParent(parent);
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
							
					/*@Override
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
					}*/
					
					public static class JavaPersistenceQueryLanguage extends Between.Adapter.Default implements Serializable{
						private static final long serialVersionUID = 1L;
						
						private static final String FORMAT = "%s BETWEEN %s AND %s";
						private static final String FORMAT_NOT = "%s NOT BETWEEN %s AND %s";
						
						@Override
						protected String getFormatNotIsFalse() {
							return FORMAT;
						}
						
						@Override
						protected String getFormatNotIsTrue() {
							return FORMAT_NOT;
						}
						
						@Override
						protected Object[] getFormatParameters(String format,Boolean not) {
							String suffix = getPropertyAsString(PROPERTY_NAME_SUFFIX);
							String fieldName = getPropertyAsString(PROPERTY_NAME_FIELD_NAME);
							String parameter1 = getPropertyAsString(PROPERTY_NAME_PARAMETER_1);
							if(StringHelper.getInstance().isBlank(parameter1))
								parameter1 = getParent().getParent().formatParameter(getFromParameterName(fieldName)+StringUtils.defaultString(suffix));
							String parameter2 = getPropertyAsString(PROPERTY_NAME_PARAMETER_2);
							if(StringHelper.getInstance().isBlank(parameter2))
								parameter2 = getParent().getParent().formatParameter(getToParameterName(fieldName)+StringUtils.defaultString(suffix));
							return new Object[]{fieldName,parameter1,parameter2};
						}
					}
				}
			}
		}
		
		//((field IS NULL ) AND (string_length = 0)) OR ((field IS NOT NULL ) AND (LOWER(field) LIKE LOWER(string)))
		public static interface Like extends Operator {
			
			@Override Like setParent(Action<Object, String> parent);
			
			@Getter
			public static class Adapter extends Operator.Adapter.Default implements Like , Serializable{
				private static final long serialVersionUID = 1L;

				@Override
				public Like setParent(Action<Object, String> parent) {
					return (Like) super.setParent(parent);
				}
				
				public static String getParameterNameString(String fieldName){
					return getInstance().getParameterName(fieldName)+"String";
				}
				
				public static String getParameterNameLike(String fieldName){
					return getInstance().getParameterName(fieldName)+"Like";
				}
				
				public static class Default extends Like.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
							
					public static class JavaPersistenceQueryLanguage extends Like.Adapter.Default implements Serializable{
						private static final long serialVersionUID = 1L;
					
						//private static final String FORMAT = "(((%1$s IS NULL ) AND (%3$s = 0)) OR ((%1$s IS NOT NULL ) AND (LOWER(%1$s) LIKE LOWER(%2$s))))";
						private static final String FORMAT = "(((%1$s IS NULL ) AND (LENGTH(%3$s) = 0)) OR ((%1$s IS NOT NULL ) AND (LOWER(%1$s) LIKE LOWER(%2$s))))";
						
			@Override
			protected String getFormatNotIsFalse() {
				return FORMAT;
			}
			
			@Override
			protected String getFormatNotIsTrue() {
				return super.getFormatNotIsTrue();
			}
						
						@Override
						protected Object[] getFormatParameters(String format,Boolean not) {
							String fieldName = getPropertyAsString(PROPERTY_NAME_FIELD_NAME);
							String string = getParent().getParent().formatParameter(getParameterNameString(fieldName));
							String like = getParent().getParent().formatParameter(getParameterNameLike(fieldName));
							return new Object[]{fieldName,like,string};
						}
						
					}
				}
			}
		}
		
		//IN     (setIsEmpty = true or field IN :set) //when set is empty use boolean var empty to decide
		//NOT IN (field IS NULL OR field NOT IN :set)
		public static interface In extends Operator {
			
			@Override Operator setParent(Action<Object, String> parent);
			
			@Getter
			public static class Adapter extends Operator.Adapter.Default implements In , Serializable{
				private static final long serialVersionUID = 1L;
		
				@Override
				public In setParent(Action<Object, String> parent) {
					return (In) super.setParent(parent);
				}
				
				public static String getParameterNameIn(String fieldName){
					return getInstance().getParameterName(fieldName,Boolean.FALSE)+"Set";
				}
				
				public static String getParameterNameIsEmpty(String fieldName){
					return getInstance().getParameterName(fieldName,Boolean.FALSE)+"SetIsEmpty";
				}
				
				public static String getParameterNameIsEmptyMeansAll(String fieldName){
					return getInstance().getParameterName(fieldName,Boolean.FALSE)+"SetIsEmptyMeansAll";
				}
				
				public static class Default extends In.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
										
					public static class JavaPersistenceQueryLanguage extends In.Adapter.Default implements Serializable{
						private static final long serialVersionUID = 1L;
						/*
						 * Known limitations : 
						 * 1 - null value cannot be test IN set
						 * 2 - empty set cannot be used for test IN | when set is empty it is not working , it is needed to use a boolean parameter
						 */
						//IN     (setIsEmpty = true or field IN :set) 
						//private static final String FORMAT = "(((%1$s IS NULL ) AND (%3$s = 0)) OR ((%1$s IS NOT NULL ) AND (LOWER(%1$s) LIKE LOWER(%2$s))))";
						//private static final String FORMAT = "(%1$s IN %2$s)";
						//private static final String FORMAT_NOT = "(%1$s IS NULL OR %1$s NOT IN %2$s)";
						
						private static final String SET_IS_EMPTY_AND_EMPTY_MEANS_ALL_FORMAT = "((%1$s = true AND %2$s = true)";
						/**
						 * value IN set     - (set is empty AND empty set means all) OR value belongs to set
						 */
						private static final String FORMAT = SET_IS_EMPTY_AND_EMPTY_MEANS_ALL_FORMAT+" OR %3$s IN %4$s)";
						/**
						 * value NOT IN set - (set is empty AND empty set means all) OR value is null OR (set is not empty AND value does not belong to set)
						 */
						private static final String FORMAT_NOT = SET_IS_EMPTY_AND_EMPTY_MEANS_ALL_FORMAT+" OR %3$s IS NULL OR (%1$s = false AND %3$s NOT IN %4$s))";
						
						
						@Override
						protected String getFormatNotIsFalse() {
							return FORMAT;
						}
						
						@Override
						protected String getFormatNotIsTrue() {
							return FORMAT_NOT;
						}
						
						@Override
						protected Object[] getFormatParameters(String format,Boolean not) {
							String isSetEmpty = getPropertyAsString(PROPERTY_NAME_IS_SET_EMPTY);
							String emptySetMeansAll = getPropertyAsString(PROPERTY_NAME_EMPTY_SET_MEANS_ALL);
							String fieldName = getPropertyAsString(PROPERTY_NAME_FIELD_NAME);
							
							String setName = getPropertyAsString(PROPERTY_NAME_SET_NAME);
							if(StringHelper.getInstance().isBlank(setName)){
								String s = FieldHelper.getInstance().getAfterFirst(getParent().getParent().getFieldNamePrefix());
								setName =  s+StringUtils.substringAfter(fieldName, s);
								//System.out.println(fieldName+" /// "+getParent().getParent().getFieldNamePrefix()+" - "+s+" - "+fieldName);
								//setName = fieldName;
							}
							String in = getParent().getParent().formatParameter(getParameterNameIn(setName));
							isSetEmpty = getParent().getParent().formatParameter(getParameterNameIsEmpty(setName));
							emptySetMeansAll = getParent().getParent().formatParameter(getParameterNameIsEmptyMeansAll(setName));
							return new Object[]{isSetEmpty,emptySetMeansAll,fieldName,in};
						}
						
					}
				}
			}
		}
	
		//EXISTS(query)
		public static interface Exists extends Operator {
			
			@Override Exists setParent(Action<Object, String> parent);
			
			@Getter
			public static class Adapter extends Operator.Adapter.Default implements Exists , Serializable{
				private static final long serialVersionUID = 1L;

				@Override
				public Exists setParent(Action<Object, String> parent) {
					return (Exists) super.setParent(parent);
				}
				
				public static class Default extends Exists.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
							
					public static class JavaPersistenceQueryLanguage extends Exists.Adapter.Default implements Serializable{
						private static final long serialVersionUID = 1L;
					
						private static final String FORMAT = "EXISTS(%s)";
						private static final String FORMAT_NOT = "EXISTS(%s)";
						
						@Override
						protected String getFormatNotIsFalse() {
							return FORMAT;
						}
						
						@Override
						protected String getFormatNotIsTrue() {
							return FORMAT_NOT;
						}
						
						@Override
						protected Object[] getFormatParameters(String format,Boolean not) {
							String query = getPropertyAsString(PROPERTY_NAME_QUERY);
							return new Object[]{query};
						}
						
					}
				}
			}
		}
		
	}
	
	public static interface OrderBy extends StringHelper.Builder.Collection {
		
		@Override OrderBy addTokens(String... tokens);
		@Override OrderBy setParent(Action<Object, String> parent);
		@Override StructuredQueryLanguageHelper.Builder getParent();
		
		OrderBy addOrder(String fieldName,Comparator.Order order);
		OrderBy addOrder(String fieldName);
		OrderBy od(String fieldName,Comparator.Order order);
		OrderBy od(String fieldName);
		OrderBy asc(String fieldName);
		OrderBy desc(String fieldName);
		
		@Getter
		public static class Adapter extends StringHelper.Builder.Collection.Adapter.Default implements OrderBy , Serializable{
			private static final long serialVersionUID = 1L;

			@Override
			public StructuredQueryLanguageHelper.Builder getParent() {
				return (StructuredQueryLanguageHelper.Builder) super.getParent();
			}
			
			@Override
			public OrderBy addTokens(String... tokens) {
				return (OrderBy) super.addTokens(tokens);
			}
			
			@Override
			public OrderBy addOrder(String fieldName, Comparator.Order order) {
				return null;
			}
			
			@Override
			public OrderBy addOrder(String fieldName) {
				return null;
			}
			
			@Override
			public OrderBy od(String fieldName, Comparator.Order order) {
				return null;
			}
			
			@Override
			public OrderBy od(String fieldName) {
				return null;
			}
			
			@Override
			public OrderBy asc(String fieldName) {
				return null;
			}
			
			@Override
			public OrderBy desc(String fieldName) {
				return null;
			}
			
			@Override
			public OrderBy setParent(Action<Object, String> parent) {
				return (OrderBy) super.setParent(parent);
			}
			
			public static class Default extends OrderBy.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Default() {
					setSeparator(Constant.CHARACTER_COMA.toString());
				}
				
				@Override
				public OrderBy addOrder(String fieldName, Comparator.Order order) {
					addActions(new Order.Adapter.Default.JavaPersistenceQueryLanguage().setParent(this)
							.setProperty(Order.PROPERTY_NAME_FIELD_NAME, getParent().formatFieldName(fieldName))
							.setProperty(PROPERTY_NAME_NOT, Comparator.Order.DESCENDING.equals(order)));
					return this;
				}
				
				@Override
				public OrderBy addOrder(String fieldName) {
					return addOrder(fieldName, Comparator.Order.ASCENDING);
				}
				
				@Override
				public OrderBy od(String fieldName, org.cyk.utility.common.Comparator.Order order) {
					return addOrder(fieldName, order);
				}
				
				@Override
				public OrderBy od(String fieldName) {
					return addOrder(fieldName);
				}
				
				@Override
				public OrderBy asc(String fieldName) {
					return od(fieldName,Comparator.Order.ASCENDING);
				}
				
				@Override
				public OrderBy desc(String fieldName) {
					return od(fieldName,Comparator.Order.DESCENDING);
				}
				
				public static class JavaPersistenceQueryLanguage extends OrderBy.Adapter.Default implements Serializable{
					private static final long serialVersionUID = 1L;
					
				}
			}
		}
		
		/**/
		
		public static interface Operator extends StructuredQueryLanguageHelper.Operator {
			
			@Override OrderBy getParent();
			@Override Operator setParent(Action<Object, String> parent);
			
			@Getter
			public static class Adapter extends StructuredQueryLanguageHelper.Operator.Adapter.Default implements Operator , Serializable{
				private static final long serialVersionUID = 1L;

				@Override
				public Operator setParent(Action<Object, String> parent) {
					return (Operator) super.setParent(parent);
				}
				
				@Override
				public OrderBy getParent() {
					return (OrderBy) super.getParent();
				}
				
				public static class Default extends Operator.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
				}
			}
		}
		
		// field ASC|DESC
		public static interface Order extends Operator {
			
			@Override Order setParent(Action<Object, String> parent);
			
			@Getter
			public static class Adapter extends Operator.Adapter.Default implements Order , Serializable{
				private static final long serialVersionUID = 1L;

				@Override
				public Order setParent(Action<Object, String> parent) {
					return (Order) super.setParent(parent);
				}
				
				public static class Default extends Order.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
					public static class JavaPersistenceQueryLanguage extends Order.Adapter.Default implements Serializable{
						private static final long serialVersionUID = 1L;
						
						private static final String FORMAT = "%s ASC";
						private static final String FORMAT_NOT = "%s DESC";
						
						@Override
						protected String getFormat() {
							return FORMAT;
						}
						
						@Override
						protected String getFormatNot() {
							return FORMAT_NOT;
						}
						
						@Override
						protected Object[] getFormatParameters(String format,Boolean not) {
							String fieldName = getPropertyAsString(PROPERTY_NAME_FIELD_NAME);
							return new Object[]{fieldName};
						}
					}
				}
			}
		}
		
	}

	/**/
	
	public static interface Operator extends StringHelper.Builder.Collection {
		
		@Getter
		public static class Adapter extends StringHelper.Builder.Collection.Adapter.Default implements Operator , Serializable{
			private static final long serialVersionUID = 1L;

			public static class Default extends Operator.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				protected static final String NOT = "NOT";
				
				@Override
				protected String __execute__() {
					Boolean not = Boolean.TRUE.equals(getPropertyAsBoolean(PROPERTY_NAME_NOT));
					String format =  not ? getFormatNot() : getFormat();
					addTokens(String.format(format, getFormatParameters(format,not)));
					return super.__execute__();
				}
				
				protected String getFormat(){
					return null;
				}
				
				protected String getFormatNot(){
					return null;
				}
				
				protected Object[] getFormatParameters(String format,Boolean not){
					return null;
				}
				
			}
		}
	}
}
