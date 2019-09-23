package org.cyk.utility.sql.builder;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.throwable.ThrowableHelper;

public abstract class AbstractQueryClauseStringBuilderSelectImpl extends AbstractQueryClauseStringBuilderImpl implements QueryClauseStringBuilderSelect, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setKeyword("SELECT");
		getProperties().setFromPath(new Object[]{Properties.IS,Properties.TUPLE,Properties.REQUIRED}, Boolean.TRUE);
	}
	
	@Override
	protected Collection<String> __executeGetArguments__(Collection<Tuple> tuples,Collection<String> arguments) {
		Collection<Attribute> columns = getAttributes();
		if(CollectionHelper.isNotEmpty(columns)){
			QueryAttributeNameBuilder attributeNameBuilder = getAttributeNameBuilder();
			attributeNameBuilder.setIsPrefixedWithTuple(getIsAttributeNamePrefixedWithTuple());
			for(Attribute index : columns){
				if(arguments == null)
					arguments = new LinkedHashSet<String>();
				arguments.add(attributeNameBuilder.setAttribute(index).execute().getOutput());
			}
		}
		if(CollectionHelper.isEmpty(arguments)){
			String allColumns = getAllColumnsArgument(tuples);
			if(__inject__(StringHelper.class).isBlank(allColumns))
				__inject__(ThrowableHelper.class).throwRuntimeException("Sql clause select all columns are required");
			return List.of(allColumns);
		}
		return arguments;
	}
	
	protected abstract String getAllColumnsArgument(Collection<Tuple> tuples);
}
