package org.cyk.utility.persistence.query;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.__static__.identifiable.AbstractObject;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class EntityGraphArguments extends AbstractObject implements Serializable {

	private Type type;
	private Attributes attributes;

	public <T> EntityGraph<T> deriveEntityGraph(Class<T> klass,EntityManager entityManager) {
		return deriveEntityGraph(klass, entityManager, this, null);
	}
	
	/**/
	
	public static <T> EntityGraph<T> deriveEntityGraph(Class<T> klass,EntityManager entityManager,EntityGraphArguments arguments,EntityGraph<T> parentEntityGraph) {
		if(klass == null)
			throw new IllegalArgumentException("class is required");
		if(entityManager == null)
			throw new IllegalArgumentException("entity manager is required");
		if(arguments == null || arguments.attributes == null || arguments.attributes.namesMap.isEmpty())
			return null;
		EntityGraph<T> entityGraph = entityManager.createEntityGraph(klass);
		arguments.attributes.namesMap.forEach( (parent,child) -> {
			entityGraph.addAttributeNodes(parent);
		});
		return entityGraph;
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Attributes extends AbstractObject implements Serializable {

		private Map<String,Attributes> namesMap;

		public Map<String,Attributes> getNamesMap(Boolean injectIfNull) {
			if(namesMap == null && Boolean.TRUE.equals(injectIfNull))
				namesMap = new HashMap<>();
			return namesMap;
		}
		
		public Attributes addNamesMap(Map<String,Attributes> namesMap) {
			if(MapHelper.isEmpty(namesMap))
				return this;
			getNamesMap(Boolean.TRUE).putAll(namesMap);
			return this;
		}
		
		public Collection<String> getNames() {
			if(MapHelper.isEmpty(namesMap))
				return null;
			return namesMap.keySet();
		}
		
		public Boolean isContainsName(String name) {
			if(MapHelper.isEmpty(namesMap))
				return Boolean.FALSE;
			return namesMap.containsKey(name);
		}
	}
	
	@Getter
	public static enum Type {
		FETCH("javax.persistence.fetchgraph"),LOAD("javax.persistence.loadgraph")
		;
		
		private String key;
		
		private Type(String key) {
			this.key = key;
		}
	}
}