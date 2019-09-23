package org.cyk.utility.stream.distributed;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.collection.AbstractCollectionInstanceImpl;

@Dependent
public class MessagesImpl extends AbstractCollectionInstanceImpl<Message> implements Messages,Serializable {
	private static final long serialVersionUID = 1L;

}
