package org.cyk.utility.client.controller.api.verysimpleentity;

import java.io.Serializable;
import java.util.Arrays;

import javax.inject.Singleton;

import org.cyk.utility.client.controller.AbstractControllerEntityPersistedInCollectionImpl;
import org.cyk.utility.client.controller.entities.verysimpleentity.VerySimpleEntity;
import org.cyk.utility.client.controller.entities.verysimpleentity.VerySimpleEntityDetails;
import org.cyk.utility.client.controller.entities.verysimpleentity.VerySimpleEntityEnum;

@Singleton
public class VerySimpleEntityControllerImpl extends AbstractControllerEntityPersistedInCollectionImpl<VerySimpleEntity> implements VerySimpleEntityController,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected VerySimpleEntity __instanciateOneRandomly__(Integer index) {
		return super.__instanciateOneRandomly__(index).setName("VseName"+index).setDescription("VSeLine01"+index+"\nVSELine02"+index)
				.setDetails(__inject__(VerySimpleEntityDetails.class).setAddress("Near the cross"))
				.setEnumeration(VerySimpleEntityEnum.V02).setEnumerations(Arrays.asList(VerySimpleEntityEnum.V03,VerySimpleEntityEnum.V05));
	}
	
}
