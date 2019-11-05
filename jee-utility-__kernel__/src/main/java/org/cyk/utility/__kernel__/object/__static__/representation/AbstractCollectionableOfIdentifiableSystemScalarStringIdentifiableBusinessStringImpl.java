package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;

public abstract class AbstractCollectionableOfIdentifiableSystemScalarStringIdentifiableBusinessStringImpl<IDENTIFIABLE> extends AbstractCollectionableOfIdentifiableSystemScalarStringImpl<IDENTIFIABLE> implements Serializable {
	private static final long serialVersionUID = 120190653223178348L;

	public AbstractCollectionableOfIdentifiableSystemScalarStringIdentifiableBusinessStringImpl<IDENTIFIABLE> add(String systemIdentifier,String businessIdentifier) {
		add(systemIdentifier);
		IDENTIFIABLE identifiable = CollectionHelper.getLast(getElements());
		FieldHelper.writeBusinessIdentifier(identifiable, businessIdentifier);
		return this;
	}
	
}
