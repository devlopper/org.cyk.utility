package org.cyk.utility.field;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.collection.AbstractCollectionInstanceImpl;

@Dependent
public class FieldDescriptionsImpl extends AbstractCollectionInstanceImpl<FieldDescription> implements FieldDescriptions,Serializable {
	private static final long serialVersionUID = 1L;
	
}
