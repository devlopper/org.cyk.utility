package org.cyk.utility.__kernel__.throwable;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.representation.AbstractMapperSourceDestinationImpl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor @AllArgsConstructor @ToString(callSuper = false)
public class Message extends AbstractObject implements Serializable {

	private String identifier;
	private String summary;
	private String details;
	
	/**/
	
	@Getter @Setter @Accessors(chain=true) @NoArgsConstructor @AllArgsConstructor @ToString(callSuper = false)
	public static class Dto extends AbstractObject implements Serializable {
		private String identifier;
		private String summary;
		private String details;
		
		/**/
		
		@org.mapstruct.Mapper
		public static abstract class Mapper extends AbstractMapperSourceDestinationImpl<Dto, Message> {
			
		}
	}
}