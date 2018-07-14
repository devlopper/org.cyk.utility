package org.cyk.utility.sql.builder;

import java.util.Collection;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface QueryClauseStringBuilder extends FunctionWithPropertiesAsInput<String> {

	String getKeyword();
	QueryClauseStringBuilder setKeyword(String keyword);
	
	Collection<String> getArguments();
	QueryClauseStringBuilder setArguments(Collection<String> arguments);
	
	String getFormat();
	QueryClauseStringBuilder setFormat(String format);
	
	Collection<Tuple> getTuples();
	QueryClauseStringBuilder setTuples(Collection<Tuple> tuples);
	QueryClauseStringBuilder addTuples(Collection<Tuple> tuples);
	QueryClauseStringBuilder addTuples(Tuple...tuples);
	QueryClauseStringBuilder addTuplesByNames(Collection<String> tupleNames);
	QueryClauseStringBuilder addTuplesByNames(String...tupleNames);
	
	Collection<Attribute> getAttributes();
	QueryClauseStringBuilder setAttributes(Collection<Attribute> attributes);
	QueryClauseStringBuilder addAttributes(Collection<Attribute> attributes);
	QueryClauseStringBuilder addAttributes(Attribute...attributes);
	QueryClauseStringBuilder addAttributesByNames(Collection<String> attributeNames);
	QueryClauseStringBuilder addAttributesByNames(String...attributeNames);
	
	QueryAttributeNameBuilder getAttributeNameBuilder();
	QueryClauseStringBuilder setAttributeNameBuilder(QueryAttributeNameBuilder builder);
	
	QueryClauseStringBuilder setIsAttributeNamePrefixedWithTuple(Boolean isAttributeNamePrefixedWithTuple);
	
	Boolean getIsAttributeNamePrefixedWithTuple();
}
