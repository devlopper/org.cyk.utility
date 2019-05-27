package org.cyk.utility.client.controller.api.entitynoform;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.client.controller.AbstractControllerEntityPersistedInCollectionImpl;
import org.cyk.utility.client.controller.entities.entitynoform.EntityNoForm;
import org.cyk.utility.random.RandomHelper;

@Singleton
public class EntityNoFormControllerImpl extends AbstractControllerEntityPersistedInCollectionImpl<EntityNoForm> implements EntityNoFormController,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected EntityNoForm __instanciateOneRandomly__(Integer index) {
		return super.__instanciateOneRandomly__(index).setName(__inject__(RandomHelper.class).getAlphabetic(5)).setDescription(__inject__(RandomHelper.class).getAlphabetic(15))
				;
	}
	
	@Override
	protected Integer __getNumberOfInitialCount__() {
		return 100;
	}
}
