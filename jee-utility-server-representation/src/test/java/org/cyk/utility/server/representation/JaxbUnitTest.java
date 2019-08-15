package org.cyk.utility.server.representation;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.cyk.utility.server.representation.entities.NodeDto;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class JaxbUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void one_marshal() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(NodeDto.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter writer = new StringWriter();
			NodeDto nodeDto = new NodeDto().setIdentifier("1").setCode("c").setName("n");
			nodeDto.addParents(new NodeDto().setIdentifier("p01").setCode("pc01").setName("pn01"));
			nodeDto.addChildren(new NodeDto().setIdentifier("c01").setCode("cc01").setName("cn01"));
			marshaller.marshal(nodeDto, writer);
			String string = writer.toString();
			System.out.println(string);
			assertThat(string)
			.contains("<nodeDto>")
			.contains("<identifier>1</identifier>").contains("<code>c</code>").contains("<name>n</name>")
			.contains("<parents>").contains("</parents>")
			.contains("<children>").contains("</children>")
			.contains("</nodeDto>");
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	/*
	@Test
	public void one_unmarshal() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(One.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			One one = (One) unmarshaller.unmarshal(getClass().getResourceAsStream("one.xml"));
			Assert.assertEquals("v1", one.getF1());
			Assert.assertEquals("v2", one.getF2());
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	*/
}
