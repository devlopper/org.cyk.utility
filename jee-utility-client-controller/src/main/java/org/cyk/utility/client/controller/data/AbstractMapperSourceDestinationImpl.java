package org.cyk.utility.client.controller.data;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.clazz.ClassNameBuilder;
import org.cyk.utility.__kernel__.value.ValueHelper;

public abstract class AbstractMapperSourceDestinationImpl<SOURCE,DESTINATION> extends org.cyk.utility.mapping.AbstractMapperSourceDestinationImpl<SOURCE,DESTINATION> {
	private static final long serialVersionUID = 1L;

	/* working variables : must be accessible from outside*/
	
	protected void __listenPostConstruct__() {
		if(__destinationClass__ == null) {
			ClassNameBuilder classNameBuilder = DependencyInjection.inject(ClassNameBuilder.class).setKlass(getClass());
			classNameBuilder.getSourceNamingModel(Boolean.TRUE).client().controller().entities().setSuffix("MapperImpl");
			classNameBuilder.getDestinationNamingModel(Boolean.TRUE).server().representation().entities().setSuffix("Dto");
			__destinationClass__ = ValueHelper.returnOrThrowIfBlank("representation entity class"
					,(Class<DESTINATION>) ClassHelper.getByName(classNameBuilder.execute().getOutput()));
		}
    }
	
}