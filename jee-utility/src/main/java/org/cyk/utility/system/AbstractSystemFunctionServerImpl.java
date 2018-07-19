package org.cyk.utility.system;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.field.FieldNameGetter;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.string.StringHelper;
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
	protected void __beforeExecute__() {
		super.__beforeExecute__();
		__getLog__().setLevel(LogLevel.INFO);
		addLogMarkers(getAction().getIdentifier().toString(),getEntityClass().getSimpleName());
		__getLog__().getMessageBuilder(Boolean.TRUE).addParameter(__inject__(StringHelper.class).concatenate(getLogMarkers(),CharacterConstant.SPACE.toString()));
	}
	
	@Override
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
						__getLog__().getMessageBuilder().addParameter(fieldName, getEnityFieldValue(entity,index,indexValueUsageType, fieldName));	
					}
			}
		}
	}
	
	protected SystemActor getSystemActor(){
		return __inject__(SystemActorServer.class);
	}

}
