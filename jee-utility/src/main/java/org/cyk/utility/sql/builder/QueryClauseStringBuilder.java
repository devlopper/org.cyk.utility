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
	
	Collection<Column> getColumns();
	QueryClauseStringBuilder setColumns(Collection<Column> columns);
	QueryClauseStringBuilder addColumns(Collection<Column> columns);
	QueryClauseStringBuilder addColumns(Column...columns);
	QueryClauseStringBuilder addColumnsByNames(Collection<String> columnNames);
	QueryClauseStringBuilder addColumnsByNames(String...columnNames);
	
	Boolean getIsPrefixColumnWithTupleRequired();
	QueryClauseStringBuilder setIsPrefixColumnWithTupleRequired(Boolean value);
}
