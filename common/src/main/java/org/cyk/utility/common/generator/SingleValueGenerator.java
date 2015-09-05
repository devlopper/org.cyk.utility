package org.cyk.utility.common.generator;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SingleValueGenerator<T> extends AbstractValueGenerator<T>{

	private static final long serialVersionUID = 5477215048061693805L;

	private T value;
	
	public SingleValueGenerator(T value) {
		super();
		this.value = value;
	}

	@Override
	public T generate() {
		return value;
	}

}
