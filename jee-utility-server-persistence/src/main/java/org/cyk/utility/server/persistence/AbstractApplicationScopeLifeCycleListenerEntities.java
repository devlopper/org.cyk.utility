package org.cyk.utility.server.persistence;
import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantString;
import org.cyk.utility.__kernel__.field.FieldInstance;
import org.cyk.utility.__kernel__.field.FieldInstancesRuntime;
import org.cyk.utility.__kernel__.internationalization.InternationalizationHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.object.__static__.identifiable.AbstractIdentifiedPersistableByLong;
import org.cyk.utility.__kernel__.object.__static__.identifiable.AbstractIdentifiedPersistableByString;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.clazz.ClassInstance;
import org.cyk.utility.clazz.ClassInstancesRuntime;
import org.cyk.utility.server.persistence.jpa.AbstractIdentifiedByString;
import org.cyk.utility.server.persistence.jpa.AbstractIdentifiedByStringAndCoded;
import org.cyk.utility.server.persistence.jpa.AbstractIdentifiedByStringAndCodedAndNamed;
import org.cyk.utility.server.persistence.jpa.AbstractIdentifiedByStringAndCodedAndNamedAndDescribable;
import org.cyk.utility.server.persistence.jpa.AbstractIdentifiedByStringAndLinkedByStringAndNamed;
import org.cyk.utility.server.persistence.jpa.hierarchy.AbstractIdentifiedByStringAndLinkedByString;

public abstract class AbstractApplicationScopeLifeCycleListenerEntities extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	public static Boolean IS_ACTIONABLE = Boolean.FALSE; //FIXME should be true after computation process optimisation done!
	public static Boolean IS_PROJECTIONABLE = Boolean.TRUE;
	
	@Override
	public void __initialize__(Object object) {
		__inject__(org.cyk.utility.server.persistence.ApplicationScopeLifeCycleListener.class).initialize(null);
		InternationalizationHelper.addResourceBundlesFromNames(getClass(),0, ConstantString.MESSAGE);		
		Class<?>[] basesClasses = __getClassesBasesClasses__();
		if(__inject__(ArrayHelper.class).isNotEmpty(basesClasses)) {
			Collection<Class<?>> classes = ClassHelper.filter(List.of(getClass().getPackage()), List.of(basesClasses), Boolean.FALSE);
			if(CollectionHelper.isNotEmpty(classes)) {
				for(@SuppressWarnings("rawtypes") Class index : classes) {
					if(!Modifier.isAbstract(index.getModifiers())) {
						ClassInstance classInstance = __inject__(ClassInstancesRuntime.class).get(index);
						classInstance.setIsActionable(IS_ACTIONABLE);
						classInstance.setIsProjectionable(IS_PROJECTIONABLE);
						FieldInstance fieldInstance = __inject__(FieldInstancesRuntime.class).get(index,classInstance.getSystemIdentifierField().getName());
						if(org.cyk.utility.__kernel__.klass.ClassHelper.isInstanceOf(index, AbstractIdentifiedPersistableByString.class))
							fieldInstance.setType(String.class);
						else if(org.cyk.utility.__kernel__.klass.ClassHelper.isInstanceOf(index, AbstractIdentifiedPersistableByLong.class))
							fieldInstance.setType(Long.class);		
					}
				}
			}	
		}
	}
	
	protected Class<?>[] __getClassesBasesClasses__() {
		return new Class<?>[] {
			AbstractIdentifiedByString.class
			,AbstractIdentifiedByStringAndCoded.class
			,AbstractIdentifiedByStringAndCodedAndNamed.class
			,AbstractIdentifiedByStringAndCodedAndNamedAndDescribable.class
			,AbstractIdentifiedByStringAndLinkedByString.class
			,AbstractIdentifiedByStringAndLinkedByStringAndNamed.class
			,org.cyk.utility.server.persistence.jpa.hierarchy.AbstractIdentifiedByString.class
			,org.cyk.utility.server.persistence.jpa.hierarchy.AbstractIdentifiedByStringAndCoded.class
			,org.cyk.utility.server.persistence.jpa.hierarchy.AbstractIdentifiedByStringAndCodedAndNamed.class
			,org.cyk.utility.server.persistence.jpa.hierarchy.AbstractIdentifiedByStringAndCodedAndNamedAndHierarchical.class
			,org.cyk.utility.server.persistence.jpa.hierarchy.AbstractIdentifiedByStringAndLinkedByString.class
			,org.cyk.utility.server.persistence.jpa.hierarchy.AbstractIdentifiedByStringAndLinkedByStringAndNamed.class
			};
	}
	
}
