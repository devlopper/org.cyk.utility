package org.cyk.utility.persistence.server.hibernate;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.persistence.server.hibernate.annotation.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;

@Hibernate
public class MetricsManagerImpl extends org.cyk.utility.persistence.server.MetricsManager.AbstractImpl implements Serializable {

	protected Statistics getStatistics(EntityManagerFactory entityManagerFactory) {
		return entityManagerFactory.unwrap(SessionFactory.class).getStatistics();
	}
	
	@Override
	protected void enable(EntityManagerFactory entityManagerFactory) {		
		if(Boolean.TRUE.equals(getEnabled(entityManagerFactory)))
			throw new RuntimeException("Metrics are already enabled");
		getStatistics(entityManagerFactory).setStatisticsEnabled(Boolean.TRUE);
	}
	
	@Override
	protected void disable(EntityManagerFactory entityManagerFactory) {
		if(!Boolean.TRUE.equals(getEnabled(entityManagerFactory)))
			throw new RuntimeException("Metrics are already disabled");
		getStatistics(entityManagerFactory).setStatisticsEnabled(Boolean.FALSE);
	}
	
	@Override
	protected void populate(EntityManagerFactory entityManagerFactory,Map<String, Object> map) {
		super.populate(entityManagerFactory,map);
		Statistics statistics = getStatistics(entityManagerFactory);
		for(Method method : Statistics.class.getDeclaredMethods()) {
			if(method.getReturnType().equals(Void.TYPE) || method.getReturnType().isArray() || method.getReturnType().getName().startsWith("org.hibernate"))
				continue;
			String name = method.getName();			
			if(name.startsWith("is"))
				name = name.substring(2);
			else if(name.startsWith("get"))
				name = name.substring(3);
			Collection<String> names = StringHelper.splitByCharacterTypeCamelCase(name);
			name = StringHelper.concatenate(names, " ");
			try {
				map.put(name, MethodUtils.invokeExactMethod(statistics, method.getName()));
			} catch (Exception exception) {
				LogHelper.log(exception, getClass());
			}
		}
		//map.put("Query Plan Cache Hit Count", statistics.getQueryPlanCacheHitCount());
		//map.put("Query Plan Cache Miss Count", statistics.getQueryPlanCacheMissCount());
	}
	
	@Override
	protected Boolean getEnabled(EntityManagerFactory entityManagerFactory) {
		Statistics statistics = getStatistics(entityManagerFactory);
		return statistics.isStatisticsEnabled();
	}
}