package org.cyk.utility.sql.builder;

import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.field.FieldName;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.criteria.Criteria;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.value.ValueHelper;

public abstract class AbstractQueryPredicateStringBuilderImpl extends AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl implements QueryPredicateStringBuilder, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		getProperties().setFromPath(new Object[]{Properties.IS,Properties.FORMAT,Properties.REQUIRED}, Boolean.TRUE);
		//setFormat("%s%s%s");
	}
	
	@Override
	protected String __execute__() throws Exception {
		Criteria criteria = getCriteria();
		if(criteria!=null){
			Class<?> clazz = criteria.getClazz();
			/*String className = criteria.getClassName();
			if(__inject__(StringHelper.class).isBlank(className)){
				if(clazz != null)
					className = clazz.getSimpleName();
			}*/
			String classSimpleName = ClassHelper.getSimpleName(criteria.getClassName());
			if(__inject__(StringHelper.class).isBlank(classSimpleName)){
				if(clazz!=null)
					classSimpleName = clazz.getSimpleName();
			}
			Tuple tuple = new Tuple().setName(classSimpleName);
			
			FieldName fieldName = __inject__(ValueHelper.class).defaultToIfNull(criteria.getFieldName(), FieldName.IDENTIFIER);
			ValueUsageType valueUsageType = __inject__(ValueHelper.class).defaultToIfNull(criteria.getFieldValueUsageType(),ValueUsageType.BUSINESS);
			
			String fieldNameAsString = criteria.getFieldNameAsString();
			if(__inject__(StringHelper.class).isBlank(fieldNameAsString)){				
				fieldNameAsString = org.cyk.utility.__kernel__.field.FieldHelper.getName(criteria.getClazz(), fieldName, valueUsageType);
			}
			
			addOperandStringBuilderAttributeName(fieldNameAsString, tuple);
			addFormatArgumentObjects(ComparisonOperator.EQ.getSymbol());
			//addChild(ComparisonOperator.EQ.getSymbol());//TODO must be get from criteria
			addOperandStringBuilderParameterName(fieldNameAsString);
		}
		String string = super.__execute__();
		string = StringUtils.replace(string, "( ", "(");
		string = StringUtils.replace(string, " )", ")");
		return string;
	}
	
	protected void __executeProcessCriteria__(Criteria criteria){
		Collection<Object> children = criteria.getChildren();
		if(__inject__(CollectionHelper.class).isEmpty(children)){
			Class<?> clazz = criteria.getClazz();
			/*String className = criteria.getClassName();
			if(__inject__(StringHelper.class).isBlank(className)){
				if(clazz != null)
					className = clazz.getSimpleName();
			}*/
			String classSimpleName = ClassHelper.getSimpleName(criteria.getClassName());
			if(__inject__(StringHelper.class).isBlank(classSimpleName)){
				if(clazz!=null)
					classSimpleName = clazz.getSimpleName();
			}
			Tuple tuple = new Tuple().setName(classSimpleName);
			
			FieldName fieldName = __inject__(ValueHelper.class).defaultToIfNull(criteria.getFieldName(), FieldName.IDENTIFIER);
			ValueUsageType valueUsageType = __inject__(ValueHelper.class).defaultToIfNull(criteria.getFieldValueUsageType(),ValueUsageType.BUSINESS);
			
			String fieldNameAsString = criteria.getFieldNameAsString();
			if(__inject__(StringHelper.class).isBlank(fieldNameAsString)){				
				fieldNameAsString = org.cyk.utility.__kernel__.field.FieldHelper.getName(criteria.getClazz(), fieldName, valueUsageType);
			}
			
			addOperandStringBuilderAttributeName(fieldNameAsString, tuple);
			addOperandStringBuilderParameterName(fieldNameAsString);
		}else{
			/*for(Object index : children){
				
			}*/
		}
	}
	
	@Override
	public Criteria getCriteria() {
		return (Criteria) getProperties().getCriteria();
	}
	
	@Override
	public QueryPredicateStringBuilder setCriteria(Criteria criteria) {
		getProperties().setCriteria(criteria);
		return this;
	}
	
	@Override
	public QueryPredicateStringBuilder addChild(Object... child) {
		return (QueryPredicateStringBuilder) super.addChild(child);
	}
	
	@Override
	public QueryPredicateStringBuilder addOperandStringBuilderAttributeName(String attributeName, Tuple tuple) {
		addFormatArgumentObjects(____inject____(QueryOperandStringBuilder.class).setAttributeNameBuilder(attributeName, tuple));
		return this;
	}
	
	@Override
	public QueryPredicateStringBuilder addOperandStringBuilderParameterName(String parameterName) {
		addFormatArgumentObjects(____inject____(QueryOperandStringBuilder.class).setParameterNameBuilder(parameterName));
		return this;
	}
	
	@Override
	public QueryPredicateStringBuilder and() {
		return (QueryPredicateStringBuilder) super.and();
	}
	
	@Override
	public QueryPredicateStringBuilder or() {
		return (QueryPredicateStringBuilder) super.or();
	}
	
	@Override
	public Tuple getTuple() {
		return (Tuple) getProperties().getTuple();
	}
	@Override
	public QueryPredicateStringBuilder setTuple(Tuple tuple) {
		getProperties().setTuple(tuple);
		return this;
	}
	
}
