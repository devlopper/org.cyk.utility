package org.cyk.utility.common.cdi;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceProducer implements Serializable {

	private static final long serialVersionUID = -4304963247295921401L;

	@Produces  
    public Logger produceLogger(InjectionPoint injectionPoint) {  
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());  
    }  
	
}
