package org.cyk.utility.client.controller;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.server.representation.AbstractEntityFromPersistenceEntity;

public abstract class AbstractInstanceBuilderImpl extends org.cyk.utility.instance.AbstractInstanceBuilderImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __copy__(Object source, Object destination, Properties properties) {
		if(source instanceof AbstractEntityFromPersistenceEntity && destination instanceof Data) {
			((Data)destination).setIdentifier(((AbstractEntityFromPersistenceEntity)source).getIdentifier());
		}else if(source instanceof Data && destination instanceof AbstractEntityFromPersistenceEntity) {
			if(((Data)source).getIdentifier()!=null)
				((AbstractEntityFromPersistenceEntity)destination).setIdentifier(((Data)source).getIdentifier().toString());
		}
		super.__copy__(source, destination, properties);
	}
	
}
