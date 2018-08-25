package org.cyk.utility.system;

import java.io.Serializable;

import org.cyk.utility.system.action.SystemActor;
import org.cyk.utility.system.action.SystemActorClient;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author Christian
 *
 */
@Getter @Setter @Accessors(chain=true)
public abstract class AbstractSystemFunctionClientImpl extends AbstractSystemFunctionImpl implements SystemFunctionClient, Serializable {
	private static final long serialVersionUID = 1L;
	
	/*@Override
	protected void __afterExecute__() {
		super.__afterExecute__();
		Object entity = getProperties().getEntity();
		Collection<FieldName> fieldNames = getLoggedEntityFieldNames();
		if(__inject__(CollectionHelper.class).isNotEmpty(fieldNames)){
			for(FieldName index : fieldNames){
				Collection<ValueUsageType> valueUsageTypes = getValueUsageTypes(index);
				if(__inject__(CollectionHelper.class).isNotEmpty(valueUsageTypes))
					for(ValueUsageType indexValueUsageType : valueUsageTypes){
						String fieldName = __inject__(FieldNameGetter.class).execute(getEntityClass(), index,indexValueUsageType).getOutput();
						addLogMessageBuilderParameter(fieldName, getEnityFieldValue(entity,index,indexValueUsageType, fieldName));	
					}
			}
		}
	}*/
	
	protected SystemActor getSystemActor(){
		return __inject__(SystemActorClient.class);
	}

}
