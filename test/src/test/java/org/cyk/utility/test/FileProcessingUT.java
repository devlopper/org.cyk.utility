package org.cyk.utility.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class FileProcessingUT extends AbstractTest {

	private static final long serialVersionUID = 3426638169905760812L;

	@Test
	public void simple(){
		String[][] nodes = new String[][]{
			new String[]{"container","configuration","property","static value"}
		};
		
		readXmlNode("arquillian.xml", ARQUILLIAN_NAMESPACE, nodes);
		System.out.println("Before update : "+StringUtils.join(nodes[0]," ; "));
		
		updateXmlNode("arquillian.xml","arquillian.xml", ARQUILLIAN_NAMESPACE, nodes);
		
		readXmlNode("arquillian.xml", ARQUILLIAN_NAMESPACE, nodes);
		System.out.println("After update : "+StringUtils.join(nodes[0]," ; "));
		
	}
	
}
