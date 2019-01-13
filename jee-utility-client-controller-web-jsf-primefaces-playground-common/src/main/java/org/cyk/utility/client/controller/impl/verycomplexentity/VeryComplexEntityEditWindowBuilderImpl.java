package org.cyk.utility.client.controller.impl.verycomplexentity;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.component.window.AbstractWindowContainerManagedWindowBuilderEditDataImpl;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.client.controller.entities.verycomplexentity.VeryComplexEntity;
import org.cyk.utility.client.controller.entities.verycomplexentity.VeryComplexEntityEditWindowBuilder;
import org.cyk.utility.system.action.SystemAction;

public class VeryComplexEntityEditWindowBuilderImpl extends AbstractWindowContainerManagedWindowBuilderEditDataImpl implements VeryComplexEntityEditWindowBuilder, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __execute__(Form form,SystemAction systemAction,Data data,ViewBuilder viewBuilder) {
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VeryComplexEntity.PROPERTY_CODE);
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VeryComplexEntity.PROPERTY_FIRST_NAME);
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VeryComplexEntity.PROPERTY_LAST_NAMES);
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VeryComplexEntity.PROPERTY_DESCRIPTION);
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VeryComplexEntity.PROPERTY_ELECTRONIC_MAIL_ADDRESS);
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VeryComplexEntity.PROPERTY_PHONE_NUMBER);
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VeryComplexEntity.PROPERTY_POSTAL_BOX);
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VeryComplexEntity.PROPERTY_ENUMERATION_01);
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VeryComplexEntity.PROPERTY_ENUMERATIONS_01);
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VeryComplexEntity.PROPERTY_ENUMERATION_02);
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VeryComplexEntity.PROPERTY_ENUMERATIONS_02);
	}

}
