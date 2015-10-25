package org.cyk.utility.test;

import org.junit.Test;

public class FileProcessingUT extends AbstractTest {

	private static final long serialVersionUID = 3426638169905760812L;

	@Test
	public void simple(){
		updateXmlNode("arquillian.xml","arquillian.xml", ARQUILLIAN_NAMESPACE, new String[][]{
			new String[]{"container","configuration","property","v1"}
		});
		
	}
	
}
