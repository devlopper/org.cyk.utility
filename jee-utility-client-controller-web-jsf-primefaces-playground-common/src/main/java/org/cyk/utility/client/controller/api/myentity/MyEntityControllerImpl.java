package org.cyk.utility.client.controller.api.myentity;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.client.controller.AbstractControllerEntityPersistedInCollectionImpl;
import org.cyk.utility.client.controller.entities.myentity.MyEntity;

@ApplicationScoped
public class MyEntityControllerImpl extends AbstractControllerEntityPersistedInCollectionImpl<MyEntity> implements MyEntityController,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected MyEntity __instanciateOneRandomly__(Integer index) {
		return super.__instanciateOneRandomly__(index).setName("MeName"+index).setDescription("MeLine01"+index+"\nMeLine02"+index);
	}
	
}
