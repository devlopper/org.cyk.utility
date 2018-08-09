package org.cyk.utility.server.representation;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.number.NumberHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class MyEntityDto extends AbstractEntity<MyEntity> implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String identifier;
	private String code;

	public MyEntityDto(MyEntity myEntity) {
		super(myEntity);
	}
	
	public MyEntity getPersistenceEntity() {
		MyEntity myEntity = new MyEntity();
		myEntity.setIdentifier(DependencyInjection.inject(NumberHelper.class).getLong(identifier));
		myEntity.setCode(getCode());
		return myEntity;
	}
	
}
