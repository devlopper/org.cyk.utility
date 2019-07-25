package org.cyk.utility.mapping;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.clazz.ClassHelper;
import org.mapstruct.TargetType;

@ApplicationScoped
public class Instantiator implements Serializable {
	private static final long serialVersionUID = 1L;

	public <T> T instantiate(@TargetType Class<T> klass) {
        return DependencyInjection.inject(ClassHelper.class).instanciateOne(klass);
    }
	
}
