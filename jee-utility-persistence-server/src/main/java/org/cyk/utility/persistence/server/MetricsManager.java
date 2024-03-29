package org.cyk.utility.persistence.server;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManagerFactory;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.persistence.EntityManagerFactoryGetter;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface MetricsManager {

	Map<String,Object> get(Arguments arguments);
	Map<String,Object> get();

	Boolean getEnabled();
	
	MetricsManager enable();
	MetricsManager disable();
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements MetricsManager,Serializable {
		
		@Override
		public MetricsManager enable() {
			enable(EntityManagerFactoryGetter.getInstance().get());
			return this;
		}
		
		@Override
		public MetricsManager disable() {
			disable(EntityManagerFactoryGetter.getInstance().get());
			return this;
		}
		
		@Override
		public Map<String, Object> get(Arguments arguments) {
			Map<String, Object> map = new TreeMap<String, Object>();
			EntityManagerFactory entityManagerFactory = EntityManagerFactoryGetter.getInstance().get();
			populate(entityManagerFactory,map);
			if(MapHelper.isEmpty(map))
				return null;			
			return map;
		}
		
		@Override
		public Map<String, Object> get() {			
			return get(null);
		}
		
		@Override
		public Boolean getEnabled() {
			return getEnabled(EntityManagerFactoryGetter.getInstance().get());
		}
		
		/**/
		
		protected Boolean getEnabled(EntityManagerFactory entityManagerFactory) {
			throw new RuntimeException("Not yet implemented");
		}
		
		protected void enable(EntityManagerFactory entityManagerFactory) {
			throw new RuntimeException("Not yet implemented");
		}
		
		protected void disable(EntityManagerFactory entityManagerFactory) {
			throw new RuntimeException("Not yet implemented");
		}
		
		protected void populate(EntityManagerFactory entityManagerFactory,Map<String, Object> map) {
		
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments extends AbstractObject implements Serializable {
		
	}
	
	/**/
	
	static MetricsManager getInstance() {
		return Helper.getInstance(MetricsManager.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}