package org.cyk.utility.common;

import org.cyk.utility.test.unit.AbstractUnitTest;

public class LogMessageUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Override
	protected void _execute_() {
		super._execute_();
		assertEquals("Create student. Class {} , Level {}", new LogMessage.Builder().set("Create","student","Class",5,"Level","B2").build().getTemplate());
	}
	
}
