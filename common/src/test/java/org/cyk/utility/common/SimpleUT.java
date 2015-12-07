package org.cyk.utility.common;

import java.util.Date;

import org.cyk.utility.test.unit.AbstractUnitTest;

public class SimpleUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Override
	protected void _execute_() {
		super._execute_();
		System.out.println("SimpleUT._execute_() : "+new Date());
	}
	
}
