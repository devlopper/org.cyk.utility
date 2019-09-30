package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.client.controller.component.annotation.InputStringLineMany;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.system.action.SystemActionCreate;
import org.cyk.utility.__kernel__.system.action.SystemActionRead;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ControllerFunctionCreatorUsingCollectionUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = -2849775962912387317L;

	static {
		__inject__(FunctionRunnableMap.class).set(ControllerFunctionCreatorImpl.class, ControllerFunctionCreatorFunctionRunnableImpl.class,1);
		__inject__(FunctionRunnableMap.class).set(ControllerFunctionReaderImpl.class, ControllerFunctionReaderFunctionRunnableImpl.class,1);
	}
	
	@Test
	public void createOne() {
		assertionHelper.assertNull(CollectionHelper.getFirst(__inject__(ControllerFunctionReader.class)
				.setActionEntityClass(Model.class).addActionEntitiesIdentifiers("1") .execute().getEntities()));
		assertionHelper.assertNull(CollectionHelper.getFirst(__inject__(ControllerFunctionReader.class)
				.setActionEntityClass(Model.class).addActionEntitiesIdentifiers("2") .execute().getEntities()));
		
		__inject__(ControllerFunctionCreator.class).setActionEntityClass(Model.class).addActionEntities(new Model().setIdentifier("1")).execute().getOutput();
		
		assertionHelper.assertNotNull(CollectionHelper.getFirst(__inject__(ControllerFunctionReader.class)
				.setActionEntityClass(Model.class).addActionEntitiesIdentifiers("1") .execute().getEntities()));
		assertionHelper.assertNull(CollectionHelper.getFirst(__inject__(ControllerFunctionReader.class)
				.setActionEntityClass(Model.class).addActionEntitiesIdentifiers("2") .execute().getEntities()));
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Model {
		private String identifier;
		@InputStringLineOne private String code;
		@InputStringLineMany private String name;
		
		public static final Collection<Model> COLLECTION = new ArrayList<>();
	}
	
	public static class ControllerFunctionCreatorFunctionRunnableImpl extends AbstractFunctionRunnableImpl<ControllerFunctionCreator> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public ControllerFunctionCreatorFunctionRunnableImpl() {
			setRunnable(new Runnable() {
				@Override
				public void run() {
					if(Model.class.equals(getFunction().getAction().getEntityClass())) {
						if(getFunction().getAction() instanceof SystemActionCreate)
							for(Object index : getFunction().getAction().getEntities().get())
								Model.COLLECTION.add((Model) index);
					}
				}
			});
		}	
	}
	
	public static class ControllerFunctionReaderFunctionRunnableImpl extends AbstractFunctionRunnableImpl<ControllerFunctionReader> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public ControllerFunctionReaderFunctionRunnableImpl() {
			setRunnable(new Runnable() {
				@Override
				public void run() {
					if(Model.class.equals(getFunction().getAction().getEntityClass())) {
						if(getFunction().getAction() instanceof SystemActionRead) {
							Collection<Object> result = new ArrayList<Object>();
							if(CollectionHelper.isNotEmpty(getFunction().getAction().getEntitiesIdentifiers())) {
								for(Object indexIdentifier : getFunction().getAction().getEntitiesIdentifiers().get()) {
									for(Object index : Model.COLLECTION) {
										if(((Model)index).getIdentifier().equals(indexIdentifier))
											result.add(index);
									}	
								}
							}else {
								result.addAll(Model.COLLECTION);
							}
							getFunction().setEntities(result);
						}
					}
				}
			});
		}	
	}
	
}
