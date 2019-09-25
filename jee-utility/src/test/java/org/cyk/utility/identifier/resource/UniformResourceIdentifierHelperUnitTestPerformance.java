package org.cyk.utility.identifier.resource;

import static org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierHelper.build;
import static org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierHelper.buildParameterValue;
import static org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierHelper.buildPath;
import static org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierHelper.buildQuery;
import static org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierHelper.buildQueryFromStringsMap;
import static org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierHelper.setPathByIdentifier;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cyk.utility.__kernel__.identifier.resource.ParameterName;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.system.action.SystemActionCreate;
import org.cyk.utility.test.weld.AbstractWeldUnitTestPerformance;
import org.junit.jupiter.api.Test;

public class UniformResourceIdentifierHelperUnitTestPerformance extends AbstractWeldUnitTestPerformance {
	private static final long serialVersionUID = 1L;

	@Test
	public void buildParameterValue_1000000(){
		execute("build parameter value from string",1000000,500,new Runnable() {
			@Override
			public void run() {
				buildParameterValue("v01");
			}
		});
		execute("build parameter value from class",1000000,500,new Runnable() {
			@Override
			public void run() {
				buildParameterValue(MyEntity.class);
			}
		});
		SystemAction systemAction = __inject__(SystemActionCreate.class);
		execute("build parameter value from objectable with identifier",1000000,500,new Runnable() {
			@Override
			public void run() {
				buildParameterValue(systemAction);
			}
		});
	}
	
	@Test
	public void buildPath_1000000(){
		setPathByIdentifier("pathIdentifier01", "");
		execute("build path from identifier",1000000,1000,new Runnable() {
			@Override
			public void run() {
				buildPath("pathIdentifier01");
			}
		});
		
		setPathByIdentifier("__entity__EditView", "p01");
		SystemAction systemAction = __inject__(SystemActionCreate.class);
		systemAction.setEntityClass(MyEntity.class);
		execute("build path from system action",1000000,5000,new Runnable() {
			@Override
			public void run() {
				buildPath(systemAction);
			}
		});
		
		execute("build path from system action class / entity class",1000000,5000,new Runnable() {
			@Override
			public void run() {
				buildPath(SystemActionCreate.class,MyEntity.class,null,null);
			}
		});
	}
	
	@Test
	public void buildQuery_1000000(){
		Map<String,List<String>> mapString = new HashMap<>();
		mapString.put("entityclass",Arrays.asList("myentity"));						
		mapString.put("entityidentifier",Arrays.asList("1"));
		mapString.put("actionclass",Arrays.asList("create"));	
		mapString.put("actionidentifier",Arrays.asList("create"));		
		execute("build query from map string",1000000,5000,new Runnable() {
			@Override
			public void run() {
				buildQueryFromStringsMap(mapString);
			}
		});
		
		Map<Object,List<Object>> mapObject = new HashMap<>();
		mapObject.put(ParameterName.ENTITY_CLASS,Arrays.asList(MyEntity.class));						
		mapObject.put(ParameterName.ENTITY_IDENTIFIER,Arrays.asList(__inject__(MyEntity.class).setIdentifier(1)));
		mapObject.put(ParameterName.ACTION_CLASS,Arrays.asList(SystemActionCreate.class));	
		mapObject.put(ParameterName.ACTION_IDENTIFIER,Arrays.asList(__inject__(SystemActionCreate.class)));		
		execute("build query from map object",1000000,5000,new Runnable() {
			@Override
			public void run() {
				buildQuery(mapObject);
			}
		});
		Collection<MyEntity> entities = Arrays.asList(__inject__(MyEntity.class).setIdentifier(1));
		execute("build query from system action class / entity class",1000000,15000,new Runnable() {
			@Override
			public void run() {
				buildQuery(SystemActionCreate.class,null,MyEntity.class,entities,null,null,null);
			}
		});
		
		SystemAction systemAction = __inject__(SystemActionCreate.class);
		systemAction.setEntityClass(MyEntity.class);
		systemAction.getEntities(Boolean.TRUE).add(__inject__(MyEntity.class).setIdentifier(1));
		execute("build query from system action",1000000,15000,new Runnable() {
			@Override
			public void run() {
				buildQuery(systemAction);
			}
		});
	}	
	
	@Test
	public void build_1000000(){
		execute("build using all components",1000000,5000,new Runnable() {
			@Override
			public void run() {
				build("http","localhost",8080,"playground/list.jsf","entity=myentity",null,null);
			}
		});
		
		SystemAction systemAction = __inject__(SystemActionCreate.class);
		systemAction.setEntityClass(MyEntity.class);
		systemAction.getEntities(Boolean.TRUE).add(__inject__(MyEntity.class).setIdentifier(1));
		execute("build using system action create",1000000,15000,new Runnable() {
			@Override
			public void run() {
				build("http","localhost",8080,systemAction);
			}
		});
		
		Collection<MyEntity> entities = Arrays.asList(__inject__(MyEntity.class).setIdentifier(1));
		execute("build using system action class / entity class",1000000,15000,new Runnable() {
			@Override
			public void run() {
				build("http","localhost",8080,SystemActionCreate.class,null,MyEntity.class,entities,null,null,null);
			}
		});
	}	
	
	private static class MyEntity extends AbstractObject {
		private static final long serialVersionUID = 1L;
		@Override
		public MyEntity setIdentifier(Object identifier) {
			return (MyEntity) super.setIdentifier(identifier);
		}
	}
	
}
