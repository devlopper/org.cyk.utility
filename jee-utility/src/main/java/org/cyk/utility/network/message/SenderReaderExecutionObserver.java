package org.cyk.utility.network.message;

import java.io.Serializable;

import javax.enterprise.event.Observes;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public class SenderReaderExecutionObserver extends AbstractObject implements Objectable,Serializable {
	private static final long serialVersionUID = 1L;

	public void observeEvent(@Observes SenderReader senderReader) {
		senderReader.execute();
		//senderReader.executeAsynchronously();//FIXME move to Java 9 or Upper to use CDI 2.0 Async Event
	}

}
