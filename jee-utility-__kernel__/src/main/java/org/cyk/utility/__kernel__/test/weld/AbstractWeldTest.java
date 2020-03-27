package org.cyk.utility.__kernel__.test.weld;

import java.io.Serializable;

import javax.transaction.Synchronization;
import javax.transaction.UserTransaction;

import org.jboss.weld.bootstrap.api.CDI11Bootstrap;
import org.jboss.weld.bootstrap.spi.Deployment;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldSetup;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.jboss.weld.transaction.spi.TransactionServices;

@org.jboss.weld.junit5.EnableWeld
public abstract class AbstractWeldTest extends org.cyk.utility.__kernel__.test.AbstractTest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@WeldSetup
    protected WeldInitiator weldInitiator = __getWeldInitiator__();
	
	protected WeldInitiator __getWeldInitiator__() {
		Weld weld = __getWeld__();
		if(weld == null)
			weld = new Weld();
		return WeldInitiator.of(weld);
	}
	
	protected Weld __getWeld__() {
		return new Weld() {
			@Override
			protected Deployment createDeployment(ResourceLoader resourceLoader, CDI11Bootstrap bootstrap) {
				Deployment deployment = super.createDeployment(resourceLoader, bootstrap);
				deployment.getServices().add(TransactionServices.class, new TransactionServices() {
					
					@Override
					public void cleanup() {
						
					}
					
					@Override
					public void registerSynchronization(Synchronization synchronizedObserver) {
						
					}
					
					@Override
					public boolean isTransactionActive() {
						return false;
					}
					
					@Override
					public UserTransaction getUserTransaction() {
						return null;
					}
				});
				return deployment;
			}
		};
	}	
}