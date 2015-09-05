package org.cyk.utility.database;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public abstract class AbstractMode implements Serializable {

	private static final long serialVersionUID = 8479918633297500632L;

	private String driverName,targetDatabase;
	
	@Override
	public String toString() {
		return "Driver : "+driverName+"\r\n"+"Target DB : "+targetDatabase;
	}
	
}
