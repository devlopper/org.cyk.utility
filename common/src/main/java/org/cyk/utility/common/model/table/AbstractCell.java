package org.cyk.utility.common.model.table;

import java.io.Serializable;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Getter @Setter @NoArgsConstructor
public abstract class AbstractCell<VALUE> implements Serializable {

	private static final long serialVersionUID = 3633221262937015949L;

	private VALUE value;

	public AbstractCell(VALUE value) {
		super();
		this.value = value;
	}
	
	
}
