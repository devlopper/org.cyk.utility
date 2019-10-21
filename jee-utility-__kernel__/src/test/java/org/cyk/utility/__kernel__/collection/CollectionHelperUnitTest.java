package org.cyk.utility.__kernel__.collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cyk.utility.__kernel__.collection.CollectionHelper.getElementAt;
import static org.cyk.utility.__kernel__.collection.CollectionHelper.getElementsFrom;
import static org.cyk.utility.__kernel__.collection.CollectionHelper.getElementsFromTo;
import static org.cyk.utility.__kernel__.collection.CollectionHelper.getSize;
import static org.cyk.utility.__kernel__.collection.CollectionHelper.isEmpty;
import static org.cyk.utility.__kernel__.collection.CollectionHelper.isNotEmpty;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

public class CollectionHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		ConfigurationHelper.clear();
	}
	
	@Test
	public void instantiateFromJson_(){
		@SuppressWarnings("rawtypes")
		Collection<Map> maps = CollectionHelper.instantiateFromJson(Map.class, "[{\"name\":\"zadi\",\"age\":\"12\"},{\"name\":\"konan\",\"age\":\"28\"}]");
		assertThat(maps).isNotNull();
		assertThat(maps).containsExactly(Map.of("name","zadi","age","12"),Map.of("name","konan","age","28"));
	}
	
	@Test
	public void getFromJsonLocatedAtUniformResourceIdentifier_() throws URISyntaxException{
		Collection<Person> persons = CollectionHelper.getFromJsonLocatedAtUniformResourceIdentifier(Person.class, getClass().getResource("person.json").toURI()
				,"identifier","name","age");
		assertThat(persons).isNotNull();
		assertThat(persons).containsExactly(new Person("1","zadi",12),new Person("2","konan",28));
	}
	
	@Test
	public void getFromJsonLocatedAtUniformResourceIdentifier_uriIsNull() throws URISyntaxException{
		ConfigurationHelper.setClassUniformResourceIdentifier(Person.class, getClass().getResource("person.json"));
		Collection<Person> persons = CollectionHelper.getFromJsonLocatedAtUniformResourceIdentifier(Person.class, (URI)null,"identifier","name","age");
		assertThat(persons).isNotNull();
		assertThat(persons).containsExactly(new Person("1","zadi",12),new Person("2","konan",28));
	}
	
	@Test
	public void isEmpty_null(){
		assertThat(isEmpty((Collection<?>)null)).isTrue();
	}
	
	@Test
	public void isEmpty_blank(){
		assertThat(isEmpty(List.of())).isTrue();
	}
	
	@Test
	public void isEmpty_notBlank(){
		assertThat(isEmpty(List.of(1))).isFalse();
	}
	
	@Test
	public void isNotEmpty_null(){
		assertThat(isNotEmpty((Collection<?>)null)).isFalse();
	}
	
	@Test
	public void isNotEmpty_blank(){
		assertThat(isNotEmpty(List.of())).isFalse();
	}
	
	@Test
	public void isNotEmpty_notBlank(){
		assertThat(isNotEmpty(List.of(1))).isTrue();
	}
	
	@Test
	public void getSize_null(){
		assertThat(getSize((Collection<?>)null)).isEqualTo(0);
	}
	
	@Test
	public void getSize_blank(){
		assertThat(getSize(List.of())).isEqualTo(0);
	}
	
	@Test
	public void getSize_notBlank(){
		assertThat(getSize(List.of(1))).isEqualTo(1);
	}
	
	@Test
	public void getElementAt_null_null(){
		assertThat(getElementAt((Collection<?>)null,null)).isNull();
	}
	
	@Test
	public void getElementAt_null_1(){
		assertThat(getElementAt((Collection<?>)null,1)).isNull();
	}
	
	@Test
	public void getElementAt_list_empty_null(){
		assertThat(getElementAt(new ArrayList<Integer>(),null)).isNull();
	}
	
	@Test
	public void getElementAt_list_empty_1(){
		assertThat(getElementAt(new ArrayList<Integer>(),1)).isNull();
	}
	
	@Test
	public void getElementAt_list_1(){
		assertThat(getElementAt(List.of(5,7),1)).isEqualTo(7);
	}
	
	@Test
	public void getElementAt_list_10_high(){
		assertThat(getElementAt(List.of(5,7),10)).isNull();
	}
	
	@Test
	public void getElementAt_list_10_low(){
		assertThat(getElementAt(List.of(5,7),-10)).isNull();
	}
	
	@Test
	public void getElementsFrom_(){
		assertThat(getElementsFrom(List.of(5,7,4,2,9),3)).containsExactly(2,9);
	}
	
	@Test
	public void getElementsFromTo_(){
		assertThat(getElementsFromTo(List.of(5,7,4,2,9),1,3)).containsExactly(7,4);
	}

	/**/
	
	@Getter @Setter @Accessors(chain=true) 
	@EqualsAndHashCode(of = {"identifier"}) @ToString
	@NoArgsConstructor @AllArgsConstructor
	public static class Person {
		private String identifier;
		private String name;
		private Integer age;
	}
}
