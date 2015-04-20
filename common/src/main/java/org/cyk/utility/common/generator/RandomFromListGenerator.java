package org.cyk.utility.common.generator;

import java.util.List;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RandomFromListGenerator<T> extends AbstractValueGenerator<T>{

	private static final long serialVersionUID = -5467151716895814528L;

	private static Random RANDOM = new Random(); 
	
	private List<T> list;
	
	public RandomFromListGenerator(List<T> list) {
		super();
		this.list = list;
	}

	@Override
	public T generate() {
		return list.get(RANDOM.nextInt(list.size()));
	}

}
