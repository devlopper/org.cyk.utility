package org.cyk.utility.maven;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.File;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.cyk.utility.maven.pom.Pom;
import org.cyk.utility.stream.StreamHelper;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class PomUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void unmarshall() throws JAXBException{
		JAXBContext jaxbContext = JAXBContext.newInstance(Pom.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		String xml = __inject__(StreamHelper.class).getString(new File(System.getProperty("user.dir")+"/src/test/resources/org/cyk/utility/maven/pom.xml"));
		Pom pom = (Pom) unmarshaller.unmarshal(new StringReader(xml));
		
		assertThat(pom.getParent()).isNotNull();
		assertThat(pom.getParent().getGroupId()).isEqualTo("parentGId");
		assertThat(pom.getParent().getArtifactId()).isEqualTo("parentAId");
		assertThat(pom.getParent().getVersion()).isEqualTo("parentVersion");
		
		assertThat(pom.getGroupId()).isEqualTo("myGId");
		assertThat(pom.getArtifactId()).isEqualTo("myAId");
		assertThat(pom.getVersion()).isEqualTo("myVersion");		
	}
	
}
