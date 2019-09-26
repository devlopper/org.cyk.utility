package org.cyk.utility.sql.builder;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.computation.SortOrder;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.value.ValueHelper;

public abstract class AbstractQueryClauseStringBuilderOrderByImpl extends AbstractQueryClauseStringBuilderImpl implements QueryClauseStringBuilderOrderBy, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setKeyword("ORDER BY");
		//getProperties().setFromPath(new Object[]{Properties.IS,Properties.TUPLE,Properties.REQUIRED}, Boolean.TRUE);
	}
	
	@Override
	protected Collection<String> __executeGetArguments__(Collection<Tuple> tuples,Collection<String> arguments) {
		if(CollectionHelper.isEmpty(arguments)){
			Collection<Attribute> attributes = getAttributes();	
			if(CollectionHelper.isEmpty(attributes))
				__injectThrowableHelper__().throwRuntimeException(getClass()+" : attribute(s) are required");
			else{
				QueryAttributeNameBuilder attributeNameBuilder = getAttributeNameBuilder();
				attributeNameBuilder.setIsPrefixedWithTuple(getIsAttributeNamePrefixedWithTuple());
				for(Attribute index : attributes){
					if(arguments == null)
						arguments = new LinkedHashSet<String>();
					SortOrder sortOrder = ValueHelper.defaultToIfNull(index.getSortOrder(), SortOrder.ASCENDING);
					arguments.add(attributeNameBuilder.setAttribute(index).execute().getOutput()+ConstantCharacter.SPACE+(SortOrder.ASCENDING.equals(sortOrder)?"ASC":"DESC"));
				}
			}
		}
		return arguments;
	}
	
}
