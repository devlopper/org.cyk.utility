package org.cyk.utility.common;

import org.cyk.utility.common.helper.FileHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class StreamHelperUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void read(){
		assertEquals("Hello Wolrd!!!", new String(FileHelper.getInstance().getBytes(Action.class,"files/text/hello world.txt")));
	}
	
	@Test
	public void write(){
		//StreamHelper.getInstance().w FileHelper.getInstance().get
	}
}
