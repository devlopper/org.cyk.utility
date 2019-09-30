package org.cyk.utility.__kernel__.runnable;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.collection.AbstractCollectionInstanceImpl;

@Dependent
public class RunnablesImpl extends AbstractCollectionInstanceImpl<Runnable> implements Runnables,Serializable {
	private static final long serialVersionUID = 1L;

}
