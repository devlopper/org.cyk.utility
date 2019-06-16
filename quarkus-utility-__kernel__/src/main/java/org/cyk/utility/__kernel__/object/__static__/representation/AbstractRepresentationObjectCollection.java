package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractRepresentationObjectCollection<ELEMENT> extends AbstractRepresentationObject implements Serializable {
	private static final long serialVersionUID = 120190653223178348L;

	private Collection<ELEMENT> collection;
	
	@XmlElement(name="element")
	public Collection<ELEMENT> getCollection(){
		return collection;
	}
	
	public AbstractRepresentationObjectCollection<ELEMENT> add(Collection<ELEMENT> elements) {
		if(elements!=null && elements.size()>0) {
			if(this.collection == null)
				this.collection = new ArrayList<>();
			this.collection.addAll(elements);
		}
		return this;
	}
	
	public AbstractRepresentationObjectCollection<ELEMENT> add(@SuppressWarnings("unchecked") ELEMENT...elements) {
		if(elements!=null && elements.length>0) {
			Collection<ELEMENT> collection = new ArrayList<>();
			for(ELEMENT index : elements)
				collection.add(index);
			add(collection);
		}
		return this;
	}
	
	public Integer getSize() {
		return collection == null ? 0 : collection.size();
	}
	
}
