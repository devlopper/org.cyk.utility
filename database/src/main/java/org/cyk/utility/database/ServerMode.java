package org.cyk.utility.database;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ServerMode extends AbstractMode implements Serializable {

	private static final long serialVersionUID = 5866657479814410500L;

	private String host = "localhost";
	private int port;

	public ServerMode(String driverName,String targetDatabase,int port) {
		super(driverName,targetDatabase);
		this.port = port;
	}
	
}
