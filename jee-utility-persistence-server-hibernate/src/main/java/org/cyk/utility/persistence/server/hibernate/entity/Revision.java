package org.cyk.utility.persistence.server.hibernate.entity;

import java.io.Serializable;

import javax.persistence.Column;
//import javax.persistence.Entity;
import javax.persistence.Id;
//import javax.persistence.Table;

//import lombok.Getter;
//import lombok.Setter;
//import lombok.experimental.Accessors;

//@Entity @Table(name = Revision.TABLE_NAME)
//@Getter @Setter @Accessors(chain=true)
public class Revision implements Serializable {

	@Id @Column(name = "REV")
	private Long identifier;
	
	@Column(name = "REVTSTMP")
	private Long timestamp;
	
	//public static final String TABLE_NAME = "REVINFO_TODEL";
}