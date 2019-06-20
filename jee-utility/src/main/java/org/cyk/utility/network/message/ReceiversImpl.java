package org.cyk.utility.network.message;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.collection.AbstractCollectionInstanceImpl;

@Dependent
public class ReceiversImpl extends AbstractCollectionInstanceImpl<Receiver> implements Receivers,Serializable {
	private static final long serialVersionUID = 3197117377411227727L;

	@Override
	public Receivers add(Receiver... elements) {
		return (Receivers) super.add(elements);
	}
	
}
