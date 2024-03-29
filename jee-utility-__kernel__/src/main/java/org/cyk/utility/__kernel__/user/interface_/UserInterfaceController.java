package org.cyk.utility.__kernel__.user.interface_;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.random.RandomHelper;

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
	
    public Object getConfigurationValue(String name) {
    	return ConfigurationHelper.getValue(name);
    }
    
    public Object getRandomIdentifier() {
    	return "random"+RandomHelper.getAlphabetic(10);
    }
    
    public String formatNumber(Number number) {
    	return NumberHelper.format(number);
    }
    
    public void logout() { 
    	UserInterfaceEventListener.getInstance().listen(UserInterfaceEvent.LOGOUT);
    }
}