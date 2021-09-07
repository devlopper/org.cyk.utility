package org.cyk.utility.persistence.server.entitymanagerproducer;

import java.io.Serializable;

import javax.persistence.EntityManagerFactory;

import org.cyk.utility.__kernel__.object.AbstractObject;

public interface EntityManagerFactoryProducer {

	EntityManagerFactory produce();
	
	public static abstract class AbstractImpl extends AbstractObject implements EntityManagerFactoryProducer,Serializable {
		
	}
}