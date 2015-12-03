package org.cyk.utility.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class FileProcessingUT extends AbstractTest {

	private static final long serialVersionUID = 3426638169905760812L;

	@Test
	public void simple(){
		String[][] nodes = new String[][]{
			new String[]{"container","configuration","property",null}
		};
		readXmlNode("arquillian.xml", ARQUILLIAN_NAMESPACE, nodes);
		
		System.out.println(nodes[0][3]);
		if(Boolean.TRUE.equals(StringUtils.contains(nodes[0][3], "memory.xml"))){
			System.out.println("Memory");
		}else if(Boolean.TRUE.equals(StringUtils.contains(nodes[0][3], "live.xml"))){
			System.out.println("Live");
			
		}
		
		/*
		readXmlNode("arquillian.xml", ARQUILLIAN_NAMESPACE, nodes);
		System.out.println("Before update : "+nodes[0][3]);
		
		nodes[0][3] = "MyValue";
		updateXmlNode("arquillian.xml","arquillian.xml", ARQUILLIAN_NAMESPACE, nodes);
		
		readXmlNode("arquillian.xml", ARQUILLIAN_NAMESPACE, nodes);
		System.out.println("After update : "+nodes[0][3]);
		*/
	}
	
}
