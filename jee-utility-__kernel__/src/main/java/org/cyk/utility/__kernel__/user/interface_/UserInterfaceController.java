package org.cyk.utility.__kernel__.user.interface_;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named @ApplicationScoped
public class UserInterfaceController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public Integer getNumberOfMillisecondToBecomeIdle() {
		//TODO get it from session configuration
		return 1000 * 60 * 15; // 15 minutes
	}
	
	public void listenEventIdle() {
		UserInterfaceEventListener.getInstance().listen(UserInterfaceEvent.IDLE);
    }
	
    public void listenEventActivate() {
    	UserInterfaceEventListener.getInstance().listen(UserInterfaceEvent.ACTIVATE);
    }
	
}
