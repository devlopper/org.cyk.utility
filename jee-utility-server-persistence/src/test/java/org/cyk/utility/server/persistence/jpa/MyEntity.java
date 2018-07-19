package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class MyEntity extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public MyEntity setCode(String code) {
		return (MyEntity) super.setCode(code);
	}
	
	@Override
	public String toString() {
		return getCode();
	}
}
