package org.cyk.utility.client.controller;

import java.io.Serializable;

import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.clazz.Classes;
import org.cyk.utility.clazz.ClassesGetter;
import org.cyk.utility.client.controller.data.DataIdentifiedByString;
import org.cyk.utility.client.controller.data.DataIdentifiedByStringAndCoded;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierParameterValueMatrix;

public abstract class AbstractApplicationScopeLifeCycleListenerEntities extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		__inject__(org.cyk.utility.client.controller.ApplicationScopeLifeCycleListener.class).initialize(null);
		Class<?>[] basesClasses = __getUniformResourceIdentifierParameterValueMatrixClassesBasesClasses__();
		if(__inject__(ArrayHelper.class).isNotEmpty(basesClasses)) {
			Classes classes = __inject__(ClassesGetter.class).addPackageNames(getClass().getPackage().getName()).addBasesClasses(basesClasses).execute().getOutput();
			if(__inject__(CollectionHelper.class).isNotEmpty(classes)) {
				for(@SuppressWarnings("rawtypes") Class index : classes.get())
					__inject__(UniformResourceIdentifierParameterValueMatrix.class).setClasses(index);
			}	
		}
	}
	
	protected Class<?>[] __getUniformResourceIdentifierParameterValueMatrixClassesBasesClasses__() {
		return new Class<?>[] {DataIdentifiedByString.class,DataIdentifiedByStringAndCoded.class};
	}
	
}
