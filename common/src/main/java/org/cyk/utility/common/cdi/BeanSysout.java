package org.cyk.utility.common.cdi;

public class BeanSysout implements BeanListener {

	@Override
	public void info(String message) {
		System.out.println(message);
	}

}
