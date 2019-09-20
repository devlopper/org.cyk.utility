package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;
import java.util.ArrayList;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@XmlRootElement(name=Actions.__ROOT_NAME__)
public class Actions implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonbProperty(value = "links")
	private ArrayList<Action> collection;
	
	@XmlElement(name=Action.__ROOT_NAME__)
	public ArrayList<Action> getCollection(){
		return collection;
	}
	
	public Actions add(String identifier,String uniformResourceLocator,String method) {
		if(collection == null)
			collection = new ArrayList<>();
		collection.add(new Action().setIdentifier(identifier).setUniformResourceLocator(uniformResourceLocator).setMethod(method));
		return this;
	}
	
	public static final String __ROOT_NAME__ = "links";

	@JsonbTransient
	public Action getByIdentifier(String identifier) {
		Action action = null;
		if(collection != null) {
			for(Action index : collection)
				if(index.getIdentifier().equals(identifier)) {
					action = index;
					break;
				}
		}
		return action;
	}
	
	@JsonbTransient
	public String getUniformResourceLocatorByIdentifier(String identifier) {
		Action action = getByIdentifier(identifier);
		return action == null ? null : action.getUniformResourceLocator();
	}
	
	@JsonbTransient
	public Actions setUniformResourceLocatorByIdentifier(String identifier,String uniformResourceLocator) {
		Action action = getByIdentifier(identifier);
		if(action == null)
			add(identifier, uniformResourceLocator, null);
		else
			action.setUniformResourceLocator(uniformResourceLocator);
		return this;
	}
	
	@JsonbTransient
	public String getMethodByIdentifier(String identifier) {
		Action action = getByIdentifier(identifier);
		return action == null ? null : action.getMethod();
	}

	@JsonbTransient
	public Actions setMethodByIdentifier(String identifier,String method) {
		Action action = getByIdentifier(identifier);
		if(action == null)
			add(identifier, null, method);
		else
			action.setMethod(method);
		return this;
	}
}
