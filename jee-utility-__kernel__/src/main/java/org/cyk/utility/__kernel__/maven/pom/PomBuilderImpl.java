package org.cyk.utility.__kernel__.maven.pom;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.cyk.utility.__kernel__.KernelHelperImpl;
import org.cyk.utility.__kernel__.properties.Properties;

public class PomBuilderImpl implements PomBuilder {

	/**/
	
	public static Pom __execute__(Properties properties) {
		Pom pom = null;
		try {
			String path = (String) properties.getPath();
			JAXBContext jaxbContext = JAXBContext.newInstance(Pom.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			String xml = KernelHelperImpl.__getStringFromFile__(path);
			pom = xml == null ? null : (Pom) unmarshaller.unmarshal(new StringReader(xml));
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
		if(pom == null){
			//TODO log warning 
		}	
		return pom;
	}
	
	public static Pom __execute__(String path) {
		return __execute__(new Properties().setPath(path));
	}
	
	public static Pom __execute__() {
		return __execute__(new Properties().setPath(System.getProperty("user.dir")+"/pom.xml"));
	}
}
