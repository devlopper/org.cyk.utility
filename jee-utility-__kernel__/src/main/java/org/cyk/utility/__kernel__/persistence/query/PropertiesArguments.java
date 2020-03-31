package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.object.__static__.identifiable.AbstractObject;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class PropertiesArguments extends AbstractObject implements Serializable {

	private EntityGraphArguments entityGraph;

	public Map<String,Object> deriveMap(Class<?> klass,EntityManager entityManager) {
		if(klass == null)
			return null;
		return deriveMap(klass, entityManager, this);
	}
	
	/**/
	
	public static Map<String,Object> deriveMap(Class<?> klass,EntityManager entityManager,PropertiesArguments arguments) {
		if(klass == null || arguments == null)
			return null;
		Map<String,Object> map = new HashMap<>();
		if(arguments.getEntityGraph() != null) {
			EntityGraph<?> entityGraph = arguments.getEntityGraph().deriveEntityGraph(klass, entityManager);
			if(entityGraph != null) {
				EntityGraphArguments.Type type = arguments.getEntityGraph().getType();
				if(type == null)
					type = EntityGraphArguments.Type.FETCH;
				map.put(type.getKey(), entityGraph);
			}
		}
		return map;
	}
}