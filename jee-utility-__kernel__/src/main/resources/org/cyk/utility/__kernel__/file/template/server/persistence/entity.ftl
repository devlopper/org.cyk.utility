package org.cyk.system.${systemIdentifier}.server.persistence.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity @Table(name=Title.TABLE_NAME)
public class ${entity} extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public ${entity} setCode(String code) {
		return (${entity}) super.setCode(code);
	}
	
	@Override
	public ${entity} setName(String name) {
		return (${entity}) super.setName(name);
	}
	
	public static final String TABLE_NAME = "${tableName}";	
	
}
