package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.client.controller.ControllerFunctionReader;
import org.cyk.utility.client.controller.entities.myentity.MyEntity;
import org.cyk.utility.client.controller.web.jsf.AbstractFunctionRunnableImpl;
import org.cyk.utility.system.action.SystemActionRead;

public class ControllerFunctionReaderFunctionRunnableImpl extends AbstractFunctionRunnableImpl<ControllerFunctionReader> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public ControllerFunctionReaderFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				if(MyEntity.class.equals(getFunction().getAction().getEntityClass())) {
					if(getFunction().getAction() instanceof SystemActionRead) {
						Collection<Object> result = new ArrayList<Object>();
						/*if(__inject__(CollectionHelper.class).isNotEmpty(getFunction().getAction().getEntitiesIdentifiers())) {
							for(Object indexIdentifier : getFunction().getAction().getEntitiesIdentifiers().get()) {
								for(Object index : MyEntity.COLLECTION) {
									if(((MyEntity)index).getIdentifier().equals(indexIdentifier))
										result.add(index);
								}	
							}
						}else {
							result.addAll(MyEntity.COLLECTION);
						}*/
						getFunction().setEntities(result);
					}
				}
			}
		});
	}	
}