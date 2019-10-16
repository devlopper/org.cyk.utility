package org.cyk.utility.__kernel__.object.__static__.representation;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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
			List<java.lang.Object> elements = (List<java.lang.Object>) many.getElements();
			assertThat(elements.get(0)).isInstanceOf(Two.class);
			assertThat(elements.get(1)).isInstanceOf(One.class);
			assertThat(elements.get(2)).isInstanceOf(Two.class);
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void marshal_localDate() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Dates.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter writer = new StringWriter();
			marshaller.marshal(new Dates().setLocalDate(LocalDate.of(2000, 3, 1)), writer);
			String string = writer.toString();
			assertThat(string).contains("<localDate>2000-03-01</localDate>");
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void unmarshal_localDate() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Dates.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Dates dates = (Dates) unmarshaller.unmarshal(getClass().getResourceAsStream("dates.xml"));
			Assert.assertTrue(dates.getLocalDate().equals(LocalDate.of(2005, 10, 17)));
		} catch(Exception exception) {
			exception.printStackTrace();
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void marshal_localDateTime() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Dates.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter writer = new StringWriter();
			marshaller.marshal(new Dates().setLocalDateTime(LocalDateTime.of(2000, 3, 1,10,15)), writer);
			String string = writer.toString();
			assertThat(string).contains("<localDateTime>2000-03-01T10:15:00</localDateTime>");
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void unmarshal_localDateTime() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Dates.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Dates dates = (Dates) unmarshaller.unmarshal(getClass().getResourceAsStream("dates.xml"));
			Assert.assertTrue(dates.getLocalDateTime().equals(LocalDateTime.of(2010, 11, 25,23,18,13)));
		} catch(Exception exception) {
			exception.printStackTrace();
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void marshal_localTime() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Dates.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter writer = new StringWriter();
			marshaller.marshal(new Dates().setLocalTime(LocalTime.of(10,15)), writer);
			String string = writer.toString();
			assertThat(string).contains("<localTime>10:15:00</localTime>");
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void unmarshal_localTime() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Dates.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Dates dates = (Dates) unmarshaller.unmarshal(getClass().getResourceAsStream("dates.xml"));
			Assert.assertTrue(dates.getLocalTime().equals(LocalTime.of(12,15)));
		} catch(Exception exception) {
			exception.printStackTrace();
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void marshal_zonedDateTime() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Dates.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter writer = new StringWriter();
			marshaller.marshal(new Dates().setZonedDateTime(ZonedDateTime.of(2000, 3, 1,10,15,0,0,ZoneId.of("Z"))), writer);
			String string = writer.toString();
			assertThat(string).contains("<zonedDateTime>2000-03-01T10:15:00Z</zonedDateTime>");
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void unmarshal_zonedDateTime() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Dates.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Dates dates = (Dates) unmarshaller.unmarshal(getClass().getResourceAsStream("dates.xml"));
			Assert.assertTrue(dates.getZonedDateTime().equals(ZonedDateTime.of(2000, 3, 1,10,15,14,0,ZoneId.of("Z"))));
		} catch(Exception exception) {
			exception.printStackTrace();
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void marshal_action() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Action.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter writer = new StringWriter();
			marshaller.marshal(new Action().setIdentifier("download").setUniformResourceLocator("/file/1/isinline=true").setMethod("get"), writer);
			String string = writer.toString();
			assertThat(string).contains("<link");
			assertThat(string).contains("rel=\"download\"");
			assertThat(string).contains("method=\"get\"");
			assertThat(string).contains("href=\"/file/1/isinline=true\"");
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void unmarshal_action() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Action.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Action action = (Action) unmarshaller.unmarshal(getClass().getResourceAsStream("action.xml"));
			Assert.assertTrue(action.getIdentifier().equals("download"));
			Assert.assertTrue(action.getUniformResourceLocator().equals("http://localhost:8080/file/01/download?isinline=true"));
			Assert.assertTrue(action.getMethod().equals("GET"));
		} catch(Exception exception) {
			exception.printStackTrace();
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void marshal_actions() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Actions.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter writer = new StringWriter();
			marshaller.marshal(new Actions().add("download","/file/1/isinline=true","get"), writer);
			String string = writer.toString();
			assertThat(string).contains("<links");
			assertThat(string).contains("<link");
			assertThat(string).contains("rel=\"download\"");
			assertThat(string).contains("method=\"get\"");
			assertThat(string).contains("href=\"/file/1/isinline=true\"");
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void unmarshal_actions() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Actions.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Actions actions = (Actions) unmarshaller.unmarshal(getClass().getResourceAsStream("actions.xml"));
			Iterator<Action> iterator = actions.getCollection().iterator();
			Action action = iterator.next();
			Assert.assertTrue(action.getIdentifier().equals("update"));
			Assert.assertTrue(action.getUniformResourceLocator().equals("http://localhost:8080/file/01"));
			Assert.assertTrue(action.getMethod().equals("PUT"));
			action = iterator.next();
			Assert.assertTrue(action.getIdentifier().equals("delete"));
			Assert.assertTrue(action.getUniformResourceLocator().equals("http://localhost:8080/file/01"));
			Assert.assertTrue(action.getMethod().equals("DELETE"));
			action = iterator.next();
			Assert.assertTrue(action.getIdentifier().equals("download"));
			Assert.assertTrue(action.getUniformResourceLocator().equals("http://localhost:8080/file/01/download?isinline=true"));
			Assert.assertTrue(action.getMethod().equals("GET"));
		} catch(Exception exception) {
			exception.printStackTrace();
			throw new RuntimeException(exception);
		}
	}
	
	//@Test
	public void marshal_representation() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Representation.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter writer = new StringWriter();
			//marshaller.marshal(new Representation().add__download__("/file/1/isinline=true","get"), writer);
			String string = writer.toString();
			assertThat(string).contains("<representation>");
			assertThat(string).contains("</representation>");
			assertThat(string).contains("<links");
			assertThat(string).contains("<link");
			assertThat(string).contains("rel=\"download\"");
			assertThat(string).contains("method=\"get\"");
			assertThat(string).contains("href=\"/file/1/isinline=true\"");
			System.out.println(string);
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void unmarshal_representation() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Representation.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Representation representation = (Representation) unmarshaller.unmarshal(getClass().getResourceAsStream("representation.xml"));
			Iterator<Action> iterator = representation.get__actions__().getCollection().iterator();
			Action action = iterator.next();
			Assert.assertTrue(action.getIdentifier().equals("download"));
			Assert.assertTrue(action.getUniformResourceLocator().equals("http://localhost:8080/file/01/download?isinline=true"));
			Assert.assertTrue(action.getMethod().equals("GET"));
		} catch(Exception exception) {
			exception.printStackTrace();
			throw new RuntimeException(exception);
		}
	}
	
	/**/
	
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
		
		private Collection<java.lang.Object> elements = new ArrayList<>();
		
		@XmlElement(name="element")
		public Collection<java.lang.Object> getElements(){
			return elements;
		}
		
		public Many add(java.lang.Object element) {
			elements.add(element);
			return this;
		}
	}
	
	@XmlRootElement @Getter @Setter @Accessors(chain=true)
	public static class Two {
		private String f1;
		private String f2;
	}
	
	@XmlRootElement @Getter @Setter @Accessors(chain=true)
	public static class Dates {
		private LocalDate localDate;
		private LocalTime localTime;
		private LocalDateTime localDateTime;
		private ZonedDateTime zonedDateTime;
		
		@XmlJavaTypeAdapter(LocalDateAdapter.class)
		public LocalDate getLocalDate() {
			return localDate;
		}
		
		@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
		public LocalDateTime getLocalDateTime() {
			return localDateTime;
		}
		
		@XmlJavaTypeAdapter(LocalTimeAdapter.class)
		public LocalTime getLocalTime() {
			return localTime;
		}
		
		@XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)
		public ZonedDateTime getZonedDateTime() {
			return zonedDateTime;
		}
	}
	
	@XmlRootElement @Getter @Setter @Accessors(chain=true)
	public static class Representation extends AbstractRepresentationObject {
		private static final long serialVersionUID = 1L;
		
	}
}
