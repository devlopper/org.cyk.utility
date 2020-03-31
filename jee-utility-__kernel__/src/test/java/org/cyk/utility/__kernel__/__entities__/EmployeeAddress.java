package org.cyk.utility.__kernel__.__entities__;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"employee","address"})})
public class EmployeeAddress extends AbstractIdentifiableSystemScalarStringImpl {
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee")
	private Employee employee;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address")
	private Address address;
}
