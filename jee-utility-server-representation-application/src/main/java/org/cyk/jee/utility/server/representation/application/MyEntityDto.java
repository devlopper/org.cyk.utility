package org.cyk.jee.utility.server.representation.application;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.number.NumberHelper;
import org.cyk.utility.server.representation.AbstractEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class MyEntityDto extends AbstractEntity<MyEntity> implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String name;

	public MyEntityDto(MyEntity myEntity) {
		super(myEntity);
		if(myEntity!=null) {
			name = myEntity.getName();
		}
	}
	
	public MyEntity getPersistenceEntity() {
		MyEntity myEntity = new MyEntity();
		myEntity.setIdentifier(DependencyInjection.inject(NumberHelper.class).getLong(getIdentifier()));
		myEntity.setCode(getCode());
		myEntity.setName(getName());
		return myEntity;
	}
	
}
