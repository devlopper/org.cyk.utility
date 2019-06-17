package org.cyk.utility.__kernel__.object.__static__.representation;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class RepresentationBasedJaxbUnitTest {

	@Test
	public void one_marshal() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(One.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter writer = new StringWriter();
			marshaller.marshal(new One().setF1("v1").setF2("v2"), writer);
			String string = writer.toString();
			assertThat(string).contains("<one>").contains("<f1>v1</f1>").contains("<f2>v2</f2>").contains("</one>");
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
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
	
	@Test
	public void many_of_one_marshal() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ManyOfOne.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter writer = new StringWriter();
			marshaller.marshal(new ManyOfOne().add(new One().setF1("v1").setF2("v2"))
					.add(new One().setF1("va1").setF2("va2")), writer);
			String string = writer.toString();
			assertThat(string).contains("<manyOfOne>").contains("<one>").contains("<f1>v1</f1>").contains("<f2>v2</f2>").contains("</one>").contains("</manyOfOne>");
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void many_of_one_unmarshal() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ManyOfOne.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			ManyOfOne manyOfOne = (ManyOfOne) unmarshaller.unmarshal(getClass().getResourceAsStream("manyofone.xml"));
			Assert.assertEquals(2, manyOfOne.getOnes().size());
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void many_of_one_parameterized_marshal() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Many.class,One.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter writer = new StringWriter();
			marshaller.marshal(new Many().add(new One().setF1("v1").setF2("v2"))
					.add(new One().setF1("va1").setF2("va2")), writer);
			String string = writer.toString();
			assertThat(string).contains("<many>").contains("<f1>v1</f1>").contains("<f2>v2</f2>")
				.contains("<f1>va1</f1>").contains("<f2>va2</f2>").contains("</many>");
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void many_of_one_parameterized_unmarshal() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Many.class,One.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Many many = (Many) unmarshaller.unmarshal(getClass().getResourceAsStream("manyobjectsofone.xml"));
			Assert.assertEquals(2, many.getElements().size());
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void two_marshal() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Two.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter writer = new StringWriter();
			marshaller.marshal(new Two().setF1("v1").setF2("v2"), writer);
			String string = writer.toString();
			assertThat(string).contains("<two>").contains("<f1>v1</f1>").contains("<f2>v2</f2>").contains("</two>");
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void two_unmarshal() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Two.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Two two = (Two) unmarshaller.unmarshal(getClass().getResourceAsStream("two.xml"));
			Assert.assertEquals("v1", two.getF1());
			Assert.assertEquals("v2", two.getF2());
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void many_of_two_parameterized_marshal() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Many.class,Two.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter writer = new StringWriter();
			marshaller.marshal(new Many().add(new Two().setF1("v1").setF2("v2"))
					.add(new Two().setF1("va1").setF2("va2")), writer);
			String string = writer.toString();
			assertThat(string).contains("<many>").contains("<f1>v1</f1>").contains("<f2>v2</f2>")
				.contains("<f1>va1</f1>").contains("<f2>va2</f2>").contains("</many>");
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void many_of_two_parameterized_unmarshal() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Many.class,Two.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Many many = (Many) unmarshaller.unmarshal(getClass().getResourceAsStream("manyobjectsoftwo.xml"));
			Assert.assertEquals(2, many.getElements().size());
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void many_of_one_and_two_parameterized_marshal() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Many.class,Two.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter writer = new StringWriter();
			marshaller.marshal(new Many().add(new Two().setF1("v1").setF2("v2")).add(new Two().setF1("ONEv1").setF2("ONEv2"))
					.add(new Two().setF1("va1").setF2("va2")), writer);
			String string = writer.toString();
			assertThat(string).contains("<many>").contains("<f1>v1</f1>").contains("<f2>v2</f2>").contains("<f1>ONEv1</f1>").contains("<f2>ONEv2</f2>")
				.contains("<f1>va1</f1>").contains("<f2>va2</f2>").contains("</many>");
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void many_of_one_and_two_parameterized_unmarshal() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Many.class,One.class,Two.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Many many = (Many) unmarshaller.unmarshal(getClass().getResourceAsStream("manyobjectsofoneandtwo.xml"));
			Assert.assertEquals(3, many.getElements().size());
			List<Object> elements = (List<Object>) many.getElements();
			assertThat(elements.get(0)).isInstanceOf(Two.class);
			assertThat(elements.get(1)).isInstanceOf(One.class);
			assertThat(elements.get(2)).isInstanceOf(Two.class);
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	/**/
	
	@XmlRootElement @Getter @Setter @Accessors(chain=true)
	public static class One {
		private String f1;
		private String f2;
	}
	
	@XmlRootElement @Getter @Setter @Accessors(chain=true)
	public static class ManyOfOne {
		
		private Collection<One> ones = new ArrayList<>();
		
		@XmlElement(name="one")
		public Collection<One> getOnes(){
			return ones;
		}
		
		public ManyOfOne add(One one) {
			ones.add(one);
			return this;
		}
	}
	
	@XmlRootElement @Getter @Setter @Accessors(chain=true)
	public static class Many {
		
		private Collection<Object> elements = new ArrayList<>();
		
		@XmlElement(name="element")
		public Collection<Object> getElements(){
			return elements;
		}
		
		public Many add(Object element) {
			elements.add(element);
			return this;
		}
	}
	
	@XmlRootElement @Getter @Setter @Accessors(chain=true)
	public static class Two {
		private String f1;
		private String f2;
	}
}
