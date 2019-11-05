package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;
import java.util.List;

import org.cyk.utility.__kernel__.field.FieldHelper;

public abstract class AbstractCollectionableOfIdentifiableSystemImpl<IDENTIFIABLE,IDENTIFIER> extends AbstractCollectionableImpl<IDENTIFIABLE> implements Serializable {
	private static final long serialVersionUID = 120190653223178348L;

	public AbstractCollectionableOfIdentifiableSystemImpl<IDENTIFIABLE,IDENTIFIER> add(IDENTIFIER systemIdentifier) {
		IDENTIFIABLE identifiable = instantiateElement();
		FieldHelper.writeSystemIdentifier(identifiable, systemIdentifier);
		add(List.of(identifiable));
		return this;
	}

}
