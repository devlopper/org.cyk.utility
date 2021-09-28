package org.cyk.utility.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.time.TimeHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
/**
 * This is execution of business function
 * @author CYK
 *
 */
public class Result extends AbstractObject implements Serializable {

	private String name;
	private Long startingTime,stoppingTime,duration;
	private Map<Class<?>,Map<Action,Integer>> countsMap;
	private Collection<String> messages;
	
	public Result open() {
		startingTime = System.currentTimeMillis();
		if(messages != null)
			messages.clear();
		return this;
	}
	
	public Result close() {
		stoppingTime = System.currentTimeMillis();
		duration = stoppingTime - startingTime;
		return this;
	}
	
	public Map<Class<?>,Map<Action,Integer>> getCountsMap(Boolean injectIfNull) {
		if(countsMap == null && Boolean.TRUE.equals(injectIfNull))
			countsMap = new HashMap<>();
		return countsMap;
	}
	
	public Result count(Class<?> klass,Action action,Integer value) {
		Map<Action,Integer> map = getCountsMap(Boolean.TRUE).get(klass);
		if(map == null)
			getCountsMap(Boolean.TRUE).put(klass, map = new HashMap<>());
		map.put(action, NumberHelper.getInteger(NumberHelper.add(map.get(action),value)));
		return this;
	}
	
	public Boolean isCountGreaterThanZero(Class<?> klass,Action action) {
		if(countsMap == null)
			return null;
		Map<Action,Integer> map = countsMap.get(klass);
		if(map == null)
			return null;
		return map.get(action) > 0;
	}
	
	public Collection<String> getMessages(Boolean injectIfNull) {
		if(messages == null && Boolean.TRUE.equals(injectIfNull))
			messages = new ArrayList<>();
		return messages;
	}
	
	public Result addMessages(Collection<String> messages) {
		if(CollectionHelper.isEmpty(messages))
			return this;
		getMessages(Boolean.TRUE).addAll(messages);
		return this;
	}
	
	public Result addMessages(String...messages) {
		if(ArrayHelper.isEmpty(messages))
			return this;
		return addMessages(CollectionHelper.listOf(messages));
	}
	
	public String buildMessage() {
		Collection<String> collection = new ArrayList<>();
		if(CollectionHelper.isNotEmpty(messages))
			collection.addAll(messages);
		/*if(MapHelper.isNotEmpty(countsMap)) {
			countsMap.entrySet().forEach( entry -> {
				if(MapHelper.isEmpty(entry.getValue()))
					return;
				entry.getValue().forEach( (k,v) -> {
					collection.add(String.format("%s.%s : %s", entry.getKey().getSimpleName(),k.name(), v));
				});
			});
		}*/
		return collection.stream().collect(Collectors.joining("\r\n"));
	}
	
	public Result log(Class<?> klass) {
		if(duration == null)
			throw new RuntimeException(String.format("business function result <<%s>> must be closed before logging", name));
		if(CollectionHelper.isNotEmpty(messages))
			messages.forEach(message -> {
				LogHelper.logWarning(message, klass);
			});
		
		Collection<String> collection = new ArrayList<>();
		if(MapHelper.isNotEmpty(countsMap)) {
			countsMap.entrySet().forEach( entry -> {
				if(MapHelper.isEmpty(entry.getValue()))
					return;
				entry.getValue().forEach( (k,v) -> {
					collection.add(String.format("%s.%s : %s", entry.getKey().getSimpleName(),k.name(), v));
				});
			});
		}
		if(CollectionHelper.isNotEmpty(collection))
			LogHelper.logInfo(String.format("Objects counts : %s.", collection), klass);
		LogHelper.logInfo(String.format("%s processed in %s.", name,TimeHelper.formatDuration(duration)), klass);
		return this;
	}
}