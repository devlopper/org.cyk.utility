package org.cyk.utility.__kernel__.mapping;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.mapstruct.TargetType;

@ApplicationScoped
public class Instantiator implements Serializable {
	private static final long serialVersionUID = 1L;

	public <T> T instantiate(@TargetType Class<T> klass) {
        return ClassHelper.instanciate(klass);
    }
	
}
