package org.cyk.jee.utility.server.representation.application;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.utility.server.persistence.jpa.AbstractEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity @Getter @Setter @Accessors(chain=true)
public class MyEntity extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long long1;
	private Long long2;
	
	@Override
	public MyEntity setCode(String code) {
		return (MyEntity) super.setCode(code);
	}
	
	@Override
	public String toString() {
		return getCode();
	}
}
