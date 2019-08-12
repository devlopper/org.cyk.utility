package org.cyk.utility.__kernel__.object.__static__.representation;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractRepresentationObject extends org.cyk.utility.__kernel__.object.AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty(value=__ATTRIBUTE___ACTIONS____)
	private Actions __actions__;
	
	@XmlAttribute(name=__ATTRIBUTE___ACTIONS____)
	public Actions get__actions__() {
		return __actions__;
	}
	
	public Actions get__actions__(Boolean injectIfNull) {
		__actions__ = get__actions__();
		if(__actions__ == null && Boolean.TRUE.equals(injectIfNull))
			__actions__ = new Actions();
		return __actions__;
	}

	public AbstractRepresentationObject add__action__(String identifier, String uniformResourceLocator, String method) {
		get__actions__(Boolean.TRUE).add(identifier, uniformResourceLocator, method);
		return this;
	}
	
	public Action get__action__byIdentifier(String identifier) {
		Actions actions = get__actions__();
		return actions == null ? null : actions.getByIdentifier(identifier);
	}
	
	public String get__actionUniformResourceLocator__byIdentifier(String identifier) {
		Actions actions = get__actions__();
		return actions == null ? null : actions.getUniformResourceLocatorByIdentifier(identifier);
	}
	
	public String get__actionMethod__byIdentifier(String identifier) {
		Actions actions = get__actions__();
		return actions == null ? null : actions.getMethodByIdentifier(identifier);
	}
	
	public AbstractRepresentationObject add__download__(String uniformResourceLocator, String method) {
		add__action__(Action.DOWNLOAD, uniformResourceLocator,method);
		return this;
	}
	
	public String get__downloadUniformResourceLocator__() {
		return get__actionUniformResourceLocator__byIdentifier(Action.DOWNLOAD);
	}
	
	private static final String __ATTRIBUTE___ACTIONS____ = Actions.__ROOT_NAME__;
}