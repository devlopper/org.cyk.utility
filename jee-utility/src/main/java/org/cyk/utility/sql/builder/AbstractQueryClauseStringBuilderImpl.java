package org.cyk.utility.sql.builder;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.throwable.ThrowableHelper;

public abstract class AbstractQueryClauseStringBuilderImpl extends AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl implements QueryClauseStringBuilder, Serializable {
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
		setAttributeNameBuilder(__inject__(QueryAttributeNameBuilder.class));
	}
	
	protected String __execute__(String format,String keyword,Collection<String> arguments) throws Exception {
		return String.format(format, keyword,__inject__(StringHelper.class).concatenate(arguments, CharacterConstant.COMA.toString()));
	}
	
	@Override
	public QueryStringBuilder getParent() {
		return (QueryStringBuilder) super.getParent();
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
		return (Collection<String>) getProperties().getFromPath(Properties.FORMAT,Properties.ARGUMENTS);
	}
	
	@Override
	public QueryClauseStringBuilder setArguments(Collection<String> arguments) {
		getProperties().setFromPath(new Object[]{Properties.FORMAT,Properties.ARGUMENTS}, arguments);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Attribute> getAttributes() {
		return (Collection<Attribute>) getProperties().getColumns();
	}
	
	@Override
	public QueryClauseStringBuilder setAttributes(Collection<Attribute> attributes) {
		getProperties().setColumns(attributes);
		return this;
	}
	
	@Override
	public QueryClauseStringBuilder addAttributes(Collection<Attribute> attributes) {
		if(__inject__(CollectionHelper.class).isNotEmpty(attributes)){
			Collection<Attribute> collection = getAttributes();
			if(collection == null)
				setAttributes(collection = new LinkedHashSet<Attribute>());
			for(Attribute index : attributes)
				if(index.getTuple() == null)
					index.setTuple(__inject__(CollectionHelper.class).getFirst(getTuples()));
			collection.addAll(attributes);
		}
		return this;
	}
	
	@Override
	public QueryClauseStringBuilder addAttributes(Attribute... attributes) {
		addAttributes(__inject__(CollectionHelper.class).instanciate(attributes));
		return this;
	}
	
	@Override
	public QueryClauseStringBuilder addAttributesByNames(Collection<String> attributeNames) {
		if(__inject__(CollectionHelper.class).isNotEmpty(attributeNames)){
			for(String index : attributeNames)
				addAttributes(new Attribute().setName(index));
		}
		return this;
	}
	
	@Override
	public QueryClauseStringBuilder addAttributesByNames(String... attributeNames) {
		addAttributesByNames(__inject__(CollectionHelper.class).instanciate(attributeNames));
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
		return (String) getProperties().getFromPath(Properties.FORMAT,Properties.__THIS__);
	}
	
	@Override
	public QueryClauseStringBuilder setFormat(String format) {
		getProperties().setFromPath(new Object[]{Properties.FORMAT,Properties.__THIS__}, format);
		return this;
	}
	
	@Override
	public QueryClauseStringBuilder setAttributeNameBuilder(QueryAttributeNameBuilder builder) {
		getProperties().setFromPath(new Object[]{Properties.ATTRIBUTE,Properties.NAME,Properties.BUILDER},builder);
		return this;
	}
	
	@Override
	public QueryAttributeNameBuilder getAttributeNameBuilder() {
		return (QueryAttributeNameBuilder) getProperties().getFromPath(Properties.ATTRIBUTE,Properties.NAME,Properties.BUILDER);
	}
	
	@Override
	public QueryClauseStringBuilder setIsAttributeNamePrefixedWithTuple(Boolean isAttributeNamePrefixedWithTuple){
		getProperties().setFromPath(new Object[]{Properties.IS,Properties.ATTRIBUTE,Properties.PREFIX,Properties.TUPLE}, isAttributeNamePrefixedWithTuple);
		return this;
	}
	
	@Override
	public Boolean getIsAttributeNamePrefixedWithTuple(){
		return (Boolean) getProperties().getFromPath(Properties.IS,Properties.ATTRIBUTE,Properties.PREFIX,Properties.TUPLE);
	}
}
