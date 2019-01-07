package org.cyk.utility.time;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.helper.AbstractHelper;

@Singleton
public class TimeHelperImpl extends AbstractHelper implements TimeHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public TimeHelper pause(Long numberOfMillisecond) {
		try {
			Thread.sleep(numberOfMillisecond);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return this;
	}

}
