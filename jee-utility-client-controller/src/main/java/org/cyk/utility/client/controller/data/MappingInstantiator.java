package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.mapstruct.TargetType;

@ApplicationScoped
public class MappingInstantiator implements Serializable {
	private static final long serialVersionUID = 1L;

	public <T extends Data> T instantiate(@TargetType Class<T> klass) {
        return ClassHelper.instanciate(klass);
    }
	
}