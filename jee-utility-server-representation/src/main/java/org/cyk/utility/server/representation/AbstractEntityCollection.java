package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.json.JsonObject;
import javax.xml.bind.annotation.XmlElement;

import org.cyk.utility.__kernel__.object.__static__.representation.AbstractRepresentationObject;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @Deprecated
public abstract class AbstractEntityCollection<T> extends AbstractRepresentationObject implements Serializable {
	private static final long serialVersionUID = 1L;

	//@JsonbTypeAdapter(value = JsonbAdapter.class)
	//@JsonbProperty(value = "collectionelements")
	private ArrayList<T> collection;
	
	@XmlElement(name="element")
	public ArrayList<T> getCollection(){
		return collection;
	}
	
	public AbstractEntityCollection<T> add(Collection<T> collection) {
		if(collection!= null && !collection.isEmpty()) {
			if(this.collection == null)
				this.collection = new ArrayList<>();
			this.collection.addAll(collection);
		}
		return this;
	}
	
	public AbstractEntityCollection<T> add(@SuppressWarnings("unchecked") T...objects) {
		if(collection == null)
			collection = new ArrayList<>();
		for(T index : objects)
			collection.add(index);
		return this;
	}
	
	@Override
	public String toString() {
		return collection == null ? null : collection.toString();
	}
	
	/**/
	
	@Deprecated
	public static class JsonbAdapter implements javax.json.bind.adapter.JsonbAdapter<Object, JsonObject> {
	    @Override
	    public JsonObject adaptToJson(Object object) throws Exception {
	    	return null;
	    }

	    @Override
	    public Object adaptFromJson(JsonObject jsonObject) throws Exception {
	    	return null;
	    }
	}
}
