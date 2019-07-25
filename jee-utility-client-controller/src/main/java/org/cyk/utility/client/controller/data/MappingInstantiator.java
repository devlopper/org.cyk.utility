package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.client.controller.data.Data;
import org.mapstruct.TargetType;

@ApplicationScoped
public class MappingInstantiator implements Serializable {
	private static final long serialVersionUID = 1L;

	public <T extends Data> T instantiate(@TargetType Class<T> klass) {
        return DependencyInjection.inject(ClassHelper.class).instanciateOne(klass);
    }
	
}