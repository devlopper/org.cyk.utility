package org.cyk.utility.sql.builder;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;

public abstract class AbstractQueryClauseStringBuilderWhereImpl extends AbstractQueryClauseStringBuilderImpl implements QueryClauseStringBuilderFrom, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setKeyword("WHERE");
		//getProperties().setFromPath(new Object[]{Properties.IS,Properties.TUPLE,Properties.REQUIRED}, Boolean.TRUE);
	}
	
	@Override
	protected Collection<String> __executeGetArguments__(Collection<Tuple> tuples,Collection<String> arguments) {
		for(Tuple index : tuples){
			if(arguments == null)
				arguments = new LinkedHashSet<String>();
			arguments.add(index.getName()+" "+index.getAlias());
		}
		return arguments;
	}

}
