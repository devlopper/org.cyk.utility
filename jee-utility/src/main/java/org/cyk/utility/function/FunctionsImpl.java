package org.cyk.utility.function;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.collection.AbstractCollectionInstanceImpl;

@Dependent @SuppressWarnings("rawtypes")
public class FunctionsImpl extends AbstractCollectionInstanceImpl<Function> implements Functions,Serializable {
	private static final long serialVersionUID = 1L;

}
