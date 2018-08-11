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

	public MyEntityDto(MyEntity myEntity) {
		super(myEntity);
	}
	
	public MyEntity getPersistenceEntity() {
		MyEntity myEntity = new MyEntity();
		myEntity.setIdentifier(DependencyInjection.inject(NumberHelper.class).getLong(getIdentifier()));
		myEntity.setCode(getCode());
		return myEntity;
	}
	
	@Override
	public MyEntityDto setCode(String code) {
		return (MyEntityDto) super.setCode(code);
	}
	
	@Override
	public String toString() {
		return getIdentifier()+":"+getCode();
	}
}
