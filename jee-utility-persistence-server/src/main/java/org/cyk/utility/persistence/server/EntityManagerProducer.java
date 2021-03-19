package org.cyk.utility.persistence.server;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.object.AbstractObject;

public interface EntityManagerProducer {

	EntityManager produce();
	
	public static abstract class AbstractImpl extends AbstractObject implements EntityManagerProducer,Serializable {
		
	}
}