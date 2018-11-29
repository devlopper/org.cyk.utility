package org.cyk.utility.client.controller.api.verysimpleentity;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.client.controller.AbstractControllerEntityPersistedInCollectionImpl;
import org.cyk.utility.client.controller.entities.verysimpleentity.VerySimpleEntity;

@Singleton
public class VerySimpleEntityControllerImpl extends AbstractControllerEntityPersistedInCollectionImpl<VerySimpleEntity> implements VerySimpleEntityController,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected VerySimpleEntity __instanciateOneRandomly__(Integer index) {
		return super.__instanciateOneRandomly__(index).setName("VseName"+index).setDescription("VSeLine01"+index+"\nVSELine02"+index);
	}
}
