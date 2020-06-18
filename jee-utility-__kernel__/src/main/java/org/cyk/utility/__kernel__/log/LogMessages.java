package org.cyk.utility.__kernel__.log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class LogMessages implements Serializable {

	private Collection<String> messages;
	private Level level;
	private Class<?> klass;
	private Boolean loggable;
		
	public Collection<String> getMessages(Boolean injectIfNull) {
		if(messages == null && Boolean.TRUE.equals(injectIfNull))
			messages = new ArrayList<>();
		return messages;
	}
	
	public LogMessages add(Collection<String> messages) {
		if(CollectionHelper.isEmpty(messages))
			return this;
		getMessages(Boolean.TRUE).addAll(messages);
		return this;
	}
	
	public LogMessages add(String...messages) {
		if(ArrayHelper.isEmpty(messages))
			return this;
		return add(CollectionHelper.listOf(messages));
	}
	
	public LogMessages addObject(Object object,String prefix,String suffix) {
		if(ValueHelper.isBlank(object))
			return this;
		return add(ValueHelper.defaultToIfNull(prefix, ConstantEmpty.STRING)+object.toString()+ValueHelper.defaultToIfNull(suffix, ConstantEmpty.STRING));
	}

	public String getMessagesAsOne() {
		return StringHelper.concatenate(messages, ">");
	}
}