package org.cyk.utility.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
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
	
	public Result log(Class<?> klass) {
		if(duration == null)
			throw new RuntimeException(String.format("business function result <<%s>> must be closed before logging", name));
		if(CollectionHelper.isNotEmpty(messages))
			messages.forEach(message -> {
				LogHelper.logWarning(message, klass);
			});
		LogHelper.logInfo(String.format("%s exécuté(e) en %s.", name,TimeHelper.formatDuration(duration)), klass);
		return this;
	}
}