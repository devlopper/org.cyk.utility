package org.cyk.utility.server.persistence.query.filter;

import java.io.Serializable;

import org.cyk.utility.__kernel__.computation.ArithmeticOperator;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.field.FieldInstance;

public class FieldImpl extends AbstractObject implements Field,Serializable {
	private static final long serialVersionUID = 1L;
	
	private FieldInstance instance;
	private Object value;
	private ArithmeticOperator arithmeticOperator;
	
	@Override
	public FieldInstance getInstance() {
		return instance;
	}

	@Override
	public Field setInstance(FieldInstance instance) {
		this.instance = instance;
		return this;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public Field setValue(Object value) {
		this.value = value;
		return this;
	}

	@Override
	public ArithmeticOperator getArithmeticOperator() {
		return arithmeticOperator;
	}

	@Override
	public Field setArithmeticOperator(ArithmeticOperator arithmeticOperator) {
		this.arithmeticOperator = arithmeticOperator;
		return this;
	}

}
