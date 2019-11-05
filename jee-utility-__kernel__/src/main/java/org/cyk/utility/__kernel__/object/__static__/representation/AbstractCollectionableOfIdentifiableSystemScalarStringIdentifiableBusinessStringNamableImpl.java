package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;

public abstract class AbstractCollectionableOfIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl<IDENTIFIABLE> extends AbstractCollectionableOfIdentifiableSystemScalarStringIdentifiableBusinessStringImpl<IDENTIFIABLE> implements Serializable {
	private static final long serialVersionUID = 120190653223178348L;

	public AbstractCollectionableOfIdentifiableSystemScalarStringIdentifiableBusinessStringImpl<IDENTIFIABLE> add(String systemIdentifier,String businessIdentifier,String name) {
		add(systemIdentifier,businessIdentifier);
		IDENTIFIABLE identifiable = CollectionHelper.getLast(getElements());
		FieldHelper.writeName(identifiable, name);
		return this;
	}
	
}
