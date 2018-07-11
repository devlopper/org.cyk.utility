package org.cyk.utility.sql.builder;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.throwable.ThrowableHelper;

public abstract class AbstractQueryClauseStringBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<String> implements QueryClauseStringBuilder, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __execute__() throws Exception {
		String format = getFormat();
		if(__inject__(StringHelper.class).isBlank(format))
			__inject__(ThrowableHelper.class).throwRuntimeException("Sql query format is required");
		String keyword = getKeyword();
		if(__inject__(StringHelper.class).isBlank(keyword))
			__inject__(ThrowableHelper.class).throwRuntimeException("Sql clause keyword is required");
		Collection<Tuple> tuples = getTuples();
		if(__inject__(CollectionHelper.class).isEmpty(tuples) && Boolean.TRUE.equals(getProperties().getFromPath(Properties.IS,Properties.TUPLE,Properties.REQUIRED)))
			__inject__(ThrowableHelper.class).throwRuntimeException("Sql clause select tuples are required");	
		Collection<String> arguments = __executeGetArguments__(tuples,getArguments());
		if(__inject__(CollectionHelper.class).isEmpty(arguments))
			__inject__(ThrowableHelper.class).throwRuntimeException("Sql clause arguments are required");		
		return __execute__(format,keyword,arguments);		
	}
	
	protected Collection<String> __executeGetArguments__(Collection<Tuple> tuples,Collection<String> arguments){
		return arguments;
	}
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setFormat("%s %s");
	}
	
	protected String __execute__(String format,String keyword,Collection<String> arguments) throws Exception {
		return String.format(format, keyword,__inject__(StringHelper.class).concatenate(arguments, CharacterConstant.COMA.toString()));
	}
	
	@Override
	public String getKeyword() {
		return (String) getProperties().getKeyword();
	}
	
	@Override
	public QueryClauseStringBuilder setKeyword(String keyword) {
		getProperties().setKeyword(keyword);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<String> getArguments() {
		return (Collection<String>) getProperties().getArguments();
	}
	
	@Override
	public QueryClauseStringBuilder setArguments(Collection<String> arguments) {
		getProperties().setArguments(arguments);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Column> getColumns() {
		return (Collection<Column>) getProperties().getColumns();
	}
	
	@Override
	public QueryClauseStringBuilder setColumns(Collection<Column> columns) {
		getProperties().setColumns(columns);
		return this;
	}
	
	@Override
	public QueryClauseStringBuilder addColumns(Collection<Column> columns) {
		if(__inject__(CollectionHelper.class).isNotEmpty(columns)){
			Collection<Column> collection = getColumns();
			if(collection == null)
				setColumns(collection = new LinkedHashSet<Column>());
			for(Column index : columns)
				if(index.getTuple() == null)
					index.setTuple(__inject__(CollectionHelper.class).getFirst(getTuples()));
			collection.addAll(columns);
		}
		return this;
	}
	
	@Override
	public QueryClauseStringBuilder addColumns(Column... columns) {
		addColumns(__inject__(CollectionHelper.class).instanciate(columns));
		return this;
	}
	
	@Override
	public QueryClauseStringBuilder addColumnsByNames(Collection<String> columnNames) {
		if(__inject__(CollectionHelper.class).isNotEmpty(columnNames)){
			for(String index : columnNames)
				addColumns(new Column().setName(index));
		}
		return this;
	}
	
	@Override
	public QueryClauseStringBuilder addColumnsByNames(String... columnNames) {
		addColumnsByNames(__inject__(CollectionHelper.class).instanciate(columnNames));
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Tuple> getTuples() {
		return (Collection<Tuple>) getProperties().getTuples();
	}
	
	@Override
	public QueryClauseStringBuilder setTuples(Collection<Tuple> tuples) {
		getProperties().setTuples(tuples);
		return this;
	}
	
	@Override
	public QueryClauseStringBuilder addTuples(Collection<Tuple> tuples) {
		if(__inject__(CollectionHelper.class).isNotEmpty(tuples)){
			Collection<Tuple> collection = getTuples();
			if(collection == null)
				setTuples(collection = new LinkedHashSet<Tuple>());
			collection.addAll(tuples);
		}
		return this;
	}
	
	@Override
	public QueryClauseStringBuilder addTuples(Tuple... tuples) {
		addTuples(__inject__(CollectionHelper.class).instanciate(tuples));
		return this;
	}
	
	@Override
	public QueryClauseStringBuilder addTuplesByNames(Collection<String> tupleNames) {
		if(__inject__(CollectionHelper.class).isNotEmpty(tupleNames)){
			for(String index : tupleNames)
				addTuples(new Tuple().setName(index));
		}
		return this;
	}
	
	@Override
	public QueryClauseStringBuilder addTuplesByNames(String... tupleNames) {
		addTuplesByNames(__inject__(CollectionHelper.class).instanciate(tupleNames));
		return this;
	}
	
	@Override
	public String getFormat() {
		return (String) getProperties().getFormat();
	}
	
	@Override
	public QueryClauseStringBuilder setFormat(String format) {
		getProperties().setFormat(format);
		return this;
	}
	
	@Override
	public QueryClauseStringBuilder setIsPrefixColumnWithTupleRequired(Boolean value) {
		getProperties().setFromPath(new Object[]{Properties.IS,Properties.PREFIX,Properties.COLUMN,Properties.TUPLE},value);
		return this;
	}
	
	@Override
	public Boolean getIsPrefixColumnWithTupleRequired() {
		return (Boolean) getProperties().getFromPath(Properties.IS,Properties.PREFIX,Properties.COLUMN,Properties.TUPLE);
	}
}
