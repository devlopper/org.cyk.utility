package org.cyk.utility.network.message;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.collection.AbstractCollectionInstanceImpl;

@Dependent
public class MessagesImpl extends AbstractCollectionInstanceImpl<Message> implements Messages,Serializable {
	private static final long serialVersionUID = 1L;

}
