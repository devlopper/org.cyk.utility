package org.cyk.utility.__kernel__.object.__static__.representation;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.json.bind.JsonbBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

public class JsonbUnitTest {

	@Test
	public void action_01_to_json() {
		Action action01 = new Action();
		String string = JsonbBuilder.create().toJson(action01);
		__assertAction__(action01, string);
	}
	
	@Test
	public void action_02_to_json() {
		Action action01 = new Action();
		action01.setIdentifier("action01");
		String string = JsonbBuilder.create().toJson(action01);
		__assertAction__(action01, string);
	}
	
	@Test
	public void action_03_to_json() {
		Action action01 = new Action();
		action01.setIdentifier("action01").setMethod("method01");
		String string = JsonbBuilder.create().toJson(action01);
		__assertAction__(action01, string);
	}
	
	@Test
	public void action_04_to_json() {
		Action action01 = new Action();
		action01.setIdentifier("action01").setMethod("method01").setUniformResourceLocator("url01");
		String string = JsonbBuilder.create().toJson(action01);
		__assertAction__(action01, string);
	}
	
	@Test
	public void actions_to_json() {
		Actions actions01 = new Actions();
		actions01.add("action01","method01","url01").add("action02","method02","url02");
		String string = JsonbBuilder.create().toJson(actions01,Actions.class);
		__assertActions__(actions01, string);
	}
	
	@Test
	public void representation_to_json() {
		Representation representation01 = new Representation();
		representation01.add__action__("action01","method01","url01").add__action__("action02","method02","url02");
		String string = JsonbBuilder.create().toJson(representation01);
		Representation representation02 = JsonbBuilder.create().fromJson(string, Representation.class);
		__assertActions__(representation01.get__actions__(), representation02.get__actions__());
	}
	
	@Test
	public void representationDto_to_json() {
		RepresentationDto representationDto = new RepresentationDto();
		representationDto.setIdentifier("i").setCode("c");
		String string = JsonbBuilder.create().toJson(representationDto);
		assertThat(string).doesNotContain("\"systemIdentifier\":");
		assertThat(string).doesNotContain("\"businessIdentifier\":");
		//__assertActions__(representation01.get__actions__(), representation02.get__actions__());
	}
		
	/**/
	
	private void __assertAction__(Action action01,String json) {
		Action action02 = JsonbBuilder.create().fromJson(json, Action.class);
		__assertAction__(action01, action02);
	}
	
	private void __assertAction__(Action action01,Action action02) {
		assertThat(action02).isNotNull();
		assertThat(action02.getIdentifier()).isEqualTo(action01.getIdentifier());
		assertThat(action02.getMethod()).isEqualTo(action01.getMethod());
		assertThat(action02.getUniformResourceLocator()).isEqualTo(action01.getUniformResourceLocator());
	}
	
	private void __assertActions__(Actions actions01,String json) {
		Actions actions02 = JsonbBuilder.create().fromJson(json, Actions.class);
		__assertActions__(actions01, actions02);
	}
	
	private void __assertActions__(Actions actions01,Actions actions02) {
		assertThat(actions02).isNotNull();
		if(actions01.getCollection() == null)
			assertThat(actions02.getCollection()).isNull();
		else {
			assertThat(actions02.getCollection()).isNotNull();
			assertThat(actions02.getCollection()).hasSize(actions01.getCollection().size());
			List<Action> list01 = new ArrayList<>(actions01.getCollection());
			List<Action> list02 = new ArrayList<>(actions02.getCollection());
			for(Integer index = 0; index < list01.size(); index = index + 1)
				__assertAction__(list01.get(index), list02.get(index));
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
	
	@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
	public class RepresentationDto extends AbstractIdentifiedByStringAndCodedAndNamedImpl implements Serializable {	
		private static final long serialVersionUID = 1L;

		@Override
		public RepresentationDto setCode(String code) {
			return (RepresentationDto) super.setCode(code);
		}
		
		@Override
		public RepresentationDto setName(String name) {
			return (RepresentationDto) super.setName(name);
		}
	}

}
