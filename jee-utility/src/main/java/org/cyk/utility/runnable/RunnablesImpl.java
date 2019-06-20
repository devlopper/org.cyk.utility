package org.cyk.utility.runnable;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.collection.AbstractCollectionInstanceImpl;

@Dependent
public class RunnablesImpl extends AbstractCollectionInstanceImpl<Runnable> implements Runnables,Serializable {
	private static final long serialVersionUID = 1L;

}
