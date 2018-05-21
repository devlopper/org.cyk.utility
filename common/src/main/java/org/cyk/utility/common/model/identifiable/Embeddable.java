package org.cyk.utility.common.model.identifiable;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public interface Embeddable {

	public static class BaseClass extends Common implements Embeddable , Serializable {
		private static final long serialVersionUID = 1L;
		
		@Getter @Setter @javax.persistence.MappedSuperclass
    	public static class JavaPersistenceEmbeddable extends Embeddable.BaseClass implements Serializable {
			private static final long serialVersionUID = 1L;
			
    	}
		
	}
	
}
