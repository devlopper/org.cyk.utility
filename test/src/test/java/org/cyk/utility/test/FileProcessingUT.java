package org.cyk.utility.test;

import org.junit.Test;

public class FileProcessingUT extends AbstractTest {

	private static final long serialVersionUID = 3426638169905760812L;

	@Test
	public void simple(){
		String[][] nodes = new String[][]{
			new String[]{"container","configuration","property",null}
		};
		
		readXmlNode("arquillian.xml", ARQUILLIAN_NAMESPACE, nodes);
		System.out.println("Before update : "+nodes[0][3]);
		
		nodes[0][3] = "MyValue";
		updateXmlNode("arquillian.xml","arquillian.xml", ARQUILLIAN_NAMESPACE, nodes);
		
		readXmlNode("arquillian.xml", ARQUILLIAN_NAMESPACE, nodes);
		System.out.println("After update : "+nodes[0][3]);
		
	}
	
}
