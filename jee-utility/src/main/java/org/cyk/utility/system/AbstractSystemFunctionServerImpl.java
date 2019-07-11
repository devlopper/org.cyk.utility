package org.cyk.utility.system;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.system.action.SystemActor;
import org.cyk.utility.system.action.SystemActorServer;
import org.cyk.utility.value.ValueUsageType;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author Christian
 *
 */
@Getter @Setter @Accessors(chain=true)
public abstract class AbstractSystemFunctionServerImpl extends AbstractSystemFunctionImpl implements SystemFunctionServer, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __log__() {
		Object entity = getProperties().getEntity();
		if(entity != null) {
			Collection<FieldName> fieldNames = __getLoggedEntityFieldNames__();
			if(__inject__(CollectionHelper.class).isNotEmpty(fieldNames)){
				for(FieldName index : fieldNames){
					Collection<ValueUsageType> valueUsageTypes = getValueUsageTypes(index);
					if(__inject__(CollectionHelper.class).isNotEmpty(valueUsageTypes))
						for(ValueUsageType indexValueUsageType : valueUsageTypes){
							Field field = __injectFieldHelper__().getField(getEntityClass(), index, indexValueUsageType);
							if(field!=null)
								addLogMessageBuilderParameter(field.getName(), getEnityFieldValue(entity,index,indexValueUsageType, field.getName()));	
						}
				}
			}	
		}
		super.__log__();
	}
	
	protected SystemActor getSystemActor(){
		return __inject__(SystemActorServer.class);
	}

}
