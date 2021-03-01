package org.cyk.utility.__kernel__.instance;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.__kernel__.variable.VariableHelper;
import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

public class InstanceGetterUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		InstanceGetterImpl.clear();
		ConfigurationHelper.clear();
	}
	
	@Test
	public void getBySystemIdentifier_(){
		InstanceGetterImpl.add(new Klass().setIdentifier("123").setCode("abc"));
		Klass instance = InstanceGetter.getInstance().getBySystemIdentifier(Klass.class, "123");
		assertThat(instance.getIdentifier()).isEqualTo("123");
		assertThat(instance.getCode()).isEqualTo("abc");
	}
	
	@Test
	public void getByBusinessIdentifier_(){
		InstanceGetterImpl.add(new Klass().setIdentifier("123").setCode("abc"));
		Klass instance = InstanceGetter.getInstance().getByBusinessIdentifier(Klass.class, "abc");
		assertThat(instance.getIdentifier()).isEqualTo("123");
		assertThat(instance.getCode()).isEqualTo("abc");
	}
	
	@Test
	public void getFromUniformResourceIdentifier(){
		VariableHelper.writeClassUniformResourceIdentifier(Person.class, getClass().getResource("person.json"));
		Collection<Person> persons = InstanceGetter.getInstance().getFromUniformResourceIdentifier(Person.class, "identifier","name","identifier");
		assertThat(persons).isNotNull();
		assertThat(persons.stream().map(Person::getIdentifier)).containsExactly("1","2");
	}
	
	@Test
	public void getFromUniformResourceIdentifier_1(){
		VariableHelper.writeClassUniformResourceIdentifier(Person.class, getClass().getResource("person_1.json"));
		Collection<Person> persons = InstanceGetter.getInstance().getFromUniformResourceIdentifier(Person.class, "identifier","name","identifier");
		assertThat(persons).isNotNull();
		assertThat(persons.stream().map(Person::getIdentifier)).containsExactly("1","2");
	}
	
	@Test
	public void getFromUniformResourceIdentifier_2(){
		VariableHelper.writeClassUniformResourceIdentifier(Person.class, getClass().getResource("person_2.json"));
		Collection<Person> persons = InstanceGetter.getInstance().getFromUniformResourceIdentifier(Person.class, "identifier","name","identifier");
		assertThat(persons).isNotNull();
		assertThat(persons.stream().map(Person::getIdentifier)).containsExactly("A","B");
	}
	
	@Test
	public void getFromUniformResourceIdentifier_1_2_classifier_1(){
		VariableHelper.writeClassUniformResourceIdentifier(Person.class,"1", getClass().getResource("person_1.json"));
		VariableHelper.writeClassUniformResourceIdentifier(Person.class,"2", getClass().getResource("person_2.json"));
		Collection<Person> persons = InstanceGetter.getInstance().getFromUniformResourceIdentifier(Person.class,"1", List.of("identifier","name","identifier"));
		assertThat(persons).isNotNull();
		assertThat(persons.stream().map(Person::getIdentifier)).containsExactly("1","2");
	}
	
	@Test
	public void getFromUniformResourceIdentifier_1_2_classifier_2(){
		VariableHelper.writeClassUniformResourceIdentifier(Person.class,"1", getClass().getResource("person_1.json"));
		VariableHelper.writeClassUniformResourceIdentifier(Person.class,"2", getClass().getResource("person_2.json"));
		Collection<Person> persons = InstanceGetter.getInstance().getFromUniformResourceIdentifier(Person.class,2, "identifier","name","identifier");
		assertThat(persons).isNotNull();
		assertThat(persons.stream().map(Person::getIdentifier)).containsExactly("A","B");
	}
	
	@Test
	public void getFromUniformResourceIdentifier_1_2(){
		VariableHelper.writeClassUniformResourceIdentifier(Person.class,"1", getClass().getResource("person_1.json"));
		VariableHelper.writeClassUniformResourceIdentifier(Person.class,"2", getClass().getResource("person_2.json"));
		Collection<Person> persons = InstanceGetter.getInstance().getFromUniformResourceIdentifiers(Person.class, List.of("1","2"), "identifier","name","identifier");
		assertThat(persons).isNotNull();
		assertThat(persons.stream().map(Person::getIdentifier)).containsExactly("1","2","A","B");
	}
	
	/**/
	
	/* Persistence */
	
	@Getter @Setter @Accessors(chain=true)
	public static class Klass {		
		private String identifier,code;
	}
	
	@Getter @Setter @Accessors(chain=true) 
	@EqualsAndHashCode(of = {"identifier"}) @ToString
	@NoArgsConstructor @AllArgsConstructor
	public static class Person {
		private String identifier;
		private String name;
		private Integer age;
	}
}
