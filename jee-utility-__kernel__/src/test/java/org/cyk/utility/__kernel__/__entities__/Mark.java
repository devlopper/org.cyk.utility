package org.cyk.utility.__kernel__.__entities__;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class Mark extends AbstractIdentifiableSystemScalarStringImpl {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee")
	private Employee employee;
	
	private Integer value;
	
	@Override
	public Mark setIdentifier(String identifier) {
		return (Mark) super.setIdentifier(identifier);
	}
}
