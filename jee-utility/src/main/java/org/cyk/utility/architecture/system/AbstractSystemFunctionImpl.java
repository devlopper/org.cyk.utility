/**
 * 
 */
package org.cyk.utility.architecture.system;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;
import org.cyk.utility.log.Log;
import org.cyk.utility.string.StringHelper;

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
	}
	
	@Override
	protected Void __execute__() {
		SystemAction action = getAction();
		if(action == null){
			//TODO log action must not be null
		}else{
			__execute__(action);	
		}
		
		return null;
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
	
	protected Log injectLog(SystemAction systemAction,Object object){
		return __getLog__().addMarkers(getLogMarkers(systemAction,object)).getMessageBuilder(Boolean.TRUE).addParameter(getLogMessagePrefix(systemAction, object))
				.getParent();
	}
	
	protected Collection<String> getLogMarkers(SystemAction systemAction,Object object){
		return systemAction == null ? null :__inject__(CollectionHelper.class).instanciate(getSystemActor().getIdentifier().toString()
				,getSystemLayer().getIdentifier().toString(),systemAction.getIdentifier().toString(),object.getClass().getSimpleName());
	}
	
	protected String getLogMessagePrefix(SystemAction systemAction,Object object){
		return __inject__(StringHelper.class).concatenate(getLogMarkers(systemAction, object),CharacterConstant.SPACE.toString());
	}

}
