package org.cyk.utility.sql.builder;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.criteria.Criteria;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.field.FieldNameGetter;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.value.ValueHelper;
import org.cyk.utility.value.ValueUsageType;

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
			String classSimpleName = __inject__(ClassHelper.class).getSimpleName(criteria.getClassName());
			if(__inject__(StringHelper.class).isBlank(classSimpleName)){
				if(clazz!=null)
					classSimpleName = clazz.getSimpleName();
			}
			Tuple tuple = new Tuple().setName(classSimpleName);
			
			FieldName fieldName = __inject__(ValueHelper.class).defaultToIfNull(criteria.getFieldName(), FieldName.IDENTIFIER);
			ValueUsageType valueUsageType = __inject__(ValueHelper.class).defaultToIfNull(criteria.getFieldValueUsageType(),ValueUsageType.BUSINESS);
			
			String fieldNameAsString = criteria.getFieldNameAsString();
			if(__inject__(StringHelper.class).isBlank(fieldNameAsString)){				
				fieldNameAsString = __inject__(FieldNameGetter.class).execute(criteria.getClazz(), fieldName, valueUsageType)
					.execute().getOutput();
			}
			
			addOperandStringBuilderAttributeName(fieldNameAsString, tuple);
			addOperandStringBuilderParameterName(fieldNameAsString);
		}
		return super.__execute__();
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
	public QueryPredicateStringBuilder addOperandStringBuilderAttributeName(String attributeName, Tuple tuple) {
		addFormatArgumentObjects(____inject____(QueryOperandStringBuilder.class).setAttributeNameBuilder(attributeName, tuple));
		return this;
	}
	
	@Override
	public QueryPredicateStringBuilder addOperandStringBuilderParameterName(String parameterName) {
		addFormatArgumentObjects(____inject____(QueryOperandStringBuilder.class).setParameterNameBuilder(parameterName));
		return this;
	}
	
}
