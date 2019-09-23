package org.cyk.utility.system;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.utility.__kernel__.field.FieldName;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.system.action.SystemActor;
import org.cyk.utility.system.action.SystemActorServer;

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
			if(fieldNames != null && !fieldNames.isEmpty()){
				for(FieldName index : fieldNames){
					Collection<ValueUsageType> valueUsageTypes = getValueUsageTypes(index);
					if(valueUsageTypes!=null && !valueUsageTypes.isEmpty())
						for(ValueUsageType indexValueUsageType : valueUsageTypes){
							Field field = org.cyk.utility.__kernel__.field.FieldHelper.getByName(getEntityClass(), index, indexValueUsageType);
							if(field!=null)
								addLogMessageBuilderParameter(field.getName(), org.cyk.utility.__kernel__.field.FieldHelper.read(entity, field));	
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
