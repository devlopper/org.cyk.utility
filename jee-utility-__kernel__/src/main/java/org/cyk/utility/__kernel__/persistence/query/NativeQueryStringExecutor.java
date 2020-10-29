package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.EntityManagerGetter;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface NativeQueryStringExecutor {

	void execute(Arguments arguments);
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements NativeQueryStringExecutor,Serializable {
		private static final long serialVersionUID = 1L;
	
		@Override
		public void execute(Arguments arguments) {
			if(arguments == null || CollectionHelper.isEmpty(arguments.queriesStrings))
				return;
			Long t0 = System.currentTimeMillis();
			Collection<String> executablesQueriesStrings = arguments.queriesStrings.stream().filter(queryString -> StringHelper.isNotBlank(queryString)).collect(Collectors.toList());
			LogHelper.log(String.format("#Q=%s , #EQ=%s", arguments.queriesStrings.size(),executablesQueriesStrings.size()), arguments.logLevel, getClass());
			Integer count = 0;
			if(CollectionHelper.isNotEmpty(executablesQueriesStrings)) {
				EntityManager entityManager = EntityManagerGetter.getInstance().get();
				for(String queryString : executablesQueriesStrings)
					count = count + entityManager.createNativeQuery(queryString).executeUpdate();
			}
			Long duration = System.currentTimeMillis() - t0;
			LogHelper.log(String.format("#%s=%s , duration=%s", arguments.action,count,duration), arguments.logLevel, getClass());
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments {
		private Collection<String> queriesStrings;
		private Level logLevel = Level.FINE;
		private Action action;
		
		public Collection<String> getQueriesStrings(Boolean injectIfNull) {
			if(queriesStrings == null && Boolean.TRUE.equals(injectIfNull))
				queriesStrings = new ArrayList<>();
			return queriesStrings;
		}
		
		public Arguments addQueriesStrings(Collection<String> queriesStrings) {
			if(CollectionHelper.isEmpty(queriesStrings))
				return this;
			getQueriesStrings(Boolean.TRUE).addAll(queriesStrings);
			return this;
		}
		
		public Arguments addQueriesStrings(String...queriesStrings) {
			if(ArrayHelper.isEmpty(queriesStrings))
				return this;
			return addQueriesStrings(CollectionHelper.listOf(queriesStrings));
		}
	}
	
	/**/
	
	static NativeQueryStringExecutor getInstance() {
		return Helper.getInstance(NativeQueryStringExecutor.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}