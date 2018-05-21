package org.cyk.utility.common.helper.instance;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import org.cyk.utility.common.computation.Trigger;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class FieldValueComputationTriggers implements Serializable {
	private static final long serialVersionUID = 1L;

	private java.util.Map<String, Trigger> map;
	
	/**/
	
	public FieldValueComputationTriggers register(Trigger trigger,Collection<String> fieldNames){
		if(CollectionHelper.getInstance().isNotEmpty(fieldNames)){
			if(map == null)
				map = new HashMap<>();
			map.put(FieldHelper.getInstance().buildPath(fieldNames), trigger);
		}
		
		return this;
	}
	
	public FieldValueComputationTriggers register(Trigger trigger,String...fieldNames){
		if(ArrayHelper.getInstance().isNotEmpty(fieldNames))
			register(trigger, Arrays.asList(fieldNames));
		return this;
	}
	
	public FieldValueComputationTriggers register(Trigger trigger,ClassHelper.Listener.FieldName fieldName){
		if(fieldName != null)
			register(trigger, fieldName.getByValueUsageType(ClassHelper.Listener.FieldName.ValueUsageType.BUSINESS));
		return this;
	}
	
	public Trigger getTrigger(Collection<String> fieldNames){
		if(map == null || CollectionHelper.getInstance().isEmpty(fieldNames))
			return null;
		return map.get(FieldHelper.getInstance().buildPath(fieldNames));
	}
	
	public Trigger getTrigger(String...fieldNames){
		if(ArrayHelper.getInstance().isEmpty(fieldNames))
			return null;
		return getTrigger(Arrays.asList(fieldNames));
	}
	
	public Trigger getTrigger(ClassHelper.Listener.FieldName fieldName){
		return getTrigger(fieldName.getByValueUsageType(ClassHelper.Listener.FieldName.ValueUsageType.BUSINESS));
	}
}
