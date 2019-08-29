package org.cyk.utility.client.controller.api.verycomplexentity;

import java.io.Serializable;
import java.util.Arrays;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.client.controller.AbstractControllerEntityPersistedInCollectionImpl;
import org.cyk.utility.client.controller.entities.verycomplexentity.VeryComplexEntity;
import org.cyk.utility.client.controller.entities.verycomplexentity.VeryComplexEntityEnum01;
import org.cyk.utility.random.RandomHelper;

@ApplicationScoped
public class VeryComplexEntityControllerImpl extends AbstractControllerEntityPersistedInCollectionImpl<VeryComplexEntity> implements VeryComplexEntityController,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected VeryComplexEntity __instanciateOneRandomly__(Integer index) {
		return super.__instanciateOneRandomly__(index)
				.setFirstName(__inject__(RandomHelper.class).getAlphabetic(5))
				.setDescription(__inject__(RandomHelper.class).getAlphabetic(10))
				.setEnumeration01(VeryComplexEntityEnum01.VCE_E01_01).setEnumerations01(Arrays.asList(VeryComplexEntityEnum01.VCE_E01_02,VeryComplexEntityEnum01.VCE_E01_04));
	}
	
}
