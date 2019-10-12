package org.cyk.utility.__kernel__.maven.pom;

import java.io.StringReader;

import javax.enterprise.context.Dependent;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.cyk.utility.__kernel__.file.FileHelper;
import org.cyk.utility.__kernel__.properties.Properties;

@Dependent
public class PomBuilderImpl implements PomBuilder {

	/**/
	
	public static Pom __execute__(Properties properties) {
		Pom pom = null;
		try {
			String path = (String) properties.getPath();
			JAXBContext jaxbContext = JAXBContext.newInstance(Pom.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			String xml = FileHelper.getString(path);
			pom = xml == null ? null : (Pom) unmarshaller.unmarshal(new StringReader(xml));
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
		
		if(pom == null)
			throw new RuntimeException("project object model (POM) cannot be found");
		return pom;
	}
	
	public static Pom __execute__(String path) {
		return __execute__(new Properties().setPath(path));
	}
	
	public static Pom __execute__() {
		return __execute__(new Properties().setPath(System.getProperty("user.dir")+"\\pom.xml"));
	}
	
	public static void marshall(Pom pom) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Pom.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			//System.out.println("PomBuilderImpl.marshall()");
			marshaller.marshal(pom, System.out);
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
		//System.out.println();
	}
}
