/**
 * 
 */
package org.cyk.utility.system;

import java.io.Serializable;

import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActor;
import org.cyk.utility.system.layer.SystemLayer;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author Christian
 *
 */
@Getter @Setter @Accessors(chain=true)
public abstract class AbstractSystemFunctionImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements SystemFunction, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setMonitorable(Boolean.TRUE).setLoggable(Boolean.TRUE);
		addLogMarkers(__inject__(CollectionHelper.class).instanciate(getSystemActor().getIdentifier().toString()));
	}
	
	@Override
	protected void ____execute____() {
		SystemAction action = getAction();
		if(action == null){
			//TODO log error : action must not be null
		}else{
			__execute__(action);	
		}
	}
	
	protected abstract void __execute__(SystemAction action);
	
	@Override
	public SystemAction getAction(){
		return (SystemAction) getProperties().getAction();
	}
	
	@Override
	public SystemFunction setAction(SystemAction action) {
		getProperties().setAction(action);
		return this;
	}
	
	/**/
	
	protected abstract SystemActor getSystemActor();
	
	protected abstract SystemLayer getSystemLayer();
	
	protected String getLogMessagePrefix(){
		return __inject__(StringHelper.class).concatenate(getLogMarkers(),CharacterConstant.SPACE.toString());
	}

}
