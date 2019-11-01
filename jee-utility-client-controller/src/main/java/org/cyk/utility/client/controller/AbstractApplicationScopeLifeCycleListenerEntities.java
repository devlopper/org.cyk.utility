package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantString;
import org.cyk.utility.__kernel__.identifier.resource.ParameterName;
import org.cyk.utility.__kernel__.internationalization.InternationalizationHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.data.DataIdentifiedByString;
import org.cyk.utility.client.controller.data.DataIdentifiedByStringAndCoded;
import org.cyk.utility.client.controller.data.DataIdentifiedByStringAndCodedAndNamed;

public abstract class AbstractApplicationScopeLifeCycleListenerEntities extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		__inject__(org.cyk.utility.client.controller.ApplicationScopeLifeCycleListener.class).initialize(null);	
		InternationalizationHelper.addResourceBundlesFromNames(getClass(),0,ConstantString.MESSAGE);		
		ParameterName.ENTITY_CLASS.setType(Data.class);
		Class<?>[] basesClasses = __getUniformResourceIdentifierParameterValueMatrixClassesBasesClasses__();
		if(__inject__(ArrayHelper.class).isNotEmpty(basesClasses)) {
			Collection<Class<?>> classes = ClassHelper.filter(List.of(getClass().getPackage()), List.of(basesClasses), null);
			if(CollectionHelper.isNotEmpty(classes))
				ParameterName.addClasses(classes);				
		}
	}
	
	protected Class<?>[] __getUniformResourceIdentifierParameterValueMatrixClassesBasesClasses__() {
		return new Class<?>[] {
			Data.class
			,DataIdentifiedByString.class
			,DataIdentifiedByStringAndCoded.class
			,DataIdentifiedByStringAndCodedAndNamed.class
			,org.cyk.utility.client.controller.data.DataIdentifiedByStringAndLinkedByString.class
			,org.cyk.utility.client.controller.data.DataIdentifiedByStringAndLinkedByStringAndNamed.class
			
			,org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByString.class
			,org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByStringAndCoded.class
			,org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByStringAndCodedAndNamed.class
			
			};
	}
	
}
