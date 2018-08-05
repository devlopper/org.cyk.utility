package org.cyk.utility.sql.builder;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;

import org.cyk.utility.__kernel__.computation.SortOrder;
import org.cyk.utility.character.CharacterConstant;

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
		if(__injectCollectionHelper__().isEmpty(arguments)){
			Collection<Attribute> attributes = getAttributes();	
			if(__injectCollectionHelper__().isEmpty(attributes))
				__injectThrowableHelper__().throwRuntimeException(getClass()+" : attribute(s) are required");
			else{
				QueryAttributeNameBuilder attributeNameBuilder = getAttributeNameBuilder();
				attributeNameBuilder.setIsPrefixedWithTuple(getIsAttributeNamePrefixedWithTuple());
				for(Attribute index : attributes){
					if(arguments == null)
						arguments = new LinkedHashSet<String>();
					SortOrder sortOrder = __injectValueHelper__().defaultToIfNull(index.getSortOrder(), SortOrder.ASCENDING);
					arguments.add(attributeNameBuilder.setAttribute(index).execute().getOutput()+CharacterConstant.SPACE+(SortOrder.ASCENDING.equals(sortOrder)?"ASC":"DESC"));
				}
			}
		}
		return arguments;
	}
	
}
