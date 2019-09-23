package org.cyk.utility.mapping;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.mapstruct.TargetType;

@ApplicationScoped
public class Instantiator implements Serializable {
	private static final long serialVersionUID = 1L;

	public <T> T instantiate(@TargetType Class<T> klass) {
        return org.cyk.utility.__kernel__.klass.ClassHelper.instanciate(klass);
    }
	
}
