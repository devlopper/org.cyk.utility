package org.cyk.utility.__kernel__.internationalization;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @EqualsAndHashCode(callSuper=false,of={"name","classLoader"})
@NoArgsConstructor @AllArgsConstructor
public class ResourceBundle extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private ClassLoader classLoader;
	
	public ResourceBundle(String name){
		setName(name);
	}
	
	/**/
	
	public static final String FIELD_NAME = "name";
	public static final String FIELD_CLASS_LOADER = "classLoader";
}
