package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity @Getter @Setter @Accessors(chain=true)
public class MyEntity extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer integerValue;
	
	@Override
	public MyEntity setCode(String code) {
		return (MyEntity) super.setCode(code);
	}
	
	@Override
	public String toString() {
		return getCode();
	}
}
