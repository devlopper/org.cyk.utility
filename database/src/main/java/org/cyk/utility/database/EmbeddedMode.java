package org.cyk.utility.database;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmbeddedMode extends AbstractMode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3267001086068952852L;

	private String path;
	
	public EmbeddedMode(String driverName,String targetDatabase,String path) {
		super(driverName,targetDatabase);
		if(!path.endsWith("\\"))
			path = path+"\\";
		this.path = path;
	}
	
	@Override
	public String toString() {
		return super.toString()+"\r\nPath : "+path;
	}
	
}
