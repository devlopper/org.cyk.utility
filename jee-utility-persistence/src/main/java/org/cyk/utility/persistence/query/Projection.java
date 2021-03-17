package org.cyk.utility.persistence.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.mapping.MapperSourceDestination;
import org.cyk.utility.__kernel__.object.AbstractObject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class Projection extends AbstractObject implements Serializable {

	private String fieldName;
	private Collection<Projection> children;
	
	public Projection(String fieldName) {
		this.fieldName = fieldName;
	}
	
	/**/
	
	@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
	public static class Dto extends AbstractObject implements Serializable {
		
		private String fieldName;
		private ArrayList<Projection> children;
	
		@org.mapstruct.Mapper
		public static abstract class Mapper extends MapperSourceDestination.AbstractImpl<Dto, Projection> {
			
		}
	}
}