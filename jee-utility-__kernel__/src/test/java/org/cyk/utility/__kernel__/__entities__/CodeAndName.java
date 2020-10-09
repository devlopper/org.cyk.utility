package org.cyk.utility.__kernel__.__entities__;

import javax.persistence.Entity;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class CodeAndName extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl {

	@Override
	public CodeAndName setCode(String code) {
		return (CodeAndName) super.setCode(code);
	}
}